package logisim;


import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import logisim.history.UndoProcedure;
import logisim.state.StateManager;
import logisim.tiles.components.Component;
import logisim.tiles.components.concrete.ANDGate;
import logisim.tiles.components.concrete.ComponentLED;
import logisim.tiles.components.concrete.ComponentSwitch;
import logisim.tiles.components.concrete.NOTGate;
import logisim.tiles.components.concrete.ORGate;
import logisim.util.GridPoint;
import logisim.util.LocalPoint;
import logisim.util.Paints;
import logisim.util.ScreenPoint;
import logisim.util.Size;
import logisim.util.TextDrawUtil;
import logisim.util.Util;

/**
 * Maintains the grid and components.
 */
public class Grid extends AbstractScreenPartition {
    // The length of the grid in tiles (not pixels).
    public final Size gridSize;
    // the length in pixels of a tile. The tiles are square so only 1 value is needed.
    public final int tileLength;

    private final Map<GridPoint, Component> components;

    public Grid(int width, int height, int tileLength, ScreenManager screenManager, StateManager stateManager, ScreenPoint origin, Size size) {
        super(origin, size, screenManager, stateManager);
        this.gridSize = new Size(width, height);
        this.tileLength = tileLength;
        this.components = new HashMap<>();
        resetGrid();
    }

    public void resetGrid() {
        components.clear();
        stateManager.history.clear();
    }

    public void setTile(GridPoint point, Component component) {
        if (component != null) {
            Component existingComp = components.get(point);
            if (existingComp != null)
                existingComp.setOnGrid(false);
            components.put(point, component);
            component.setPoint(point);
            component.setOnGrid(true);
        }
    }

    public void removeTile(GridPoint point) {
        Component component = components.get(point);
        if (component != null)
            component.setOnGrid(false);
        components.remove(point);
    }

    @Nullable
    public Component getTile(GridPoint point) {
        return components.get(point);
    }

    @Override
    public IInteractable getTouchedObject(LocalPoint localPoint) {
        return getTile(convertToGridPoint(localPoint));
    }

    public void moveTile(GridPoint src, GridPoint dest) {
        Component component = components.get(src);
        if (component != null) {
            components.remove(src);
            components.put(dest, component);
            component.setPoint(dest);
        }
    }

    @Override
    public void draw() {
        fillBackground();
        screenManager.debugText.addText("");
        screenManager.debugText.addText("Grid Size: " + gridSize);
        screenManager.debugText.addText("Tile Length: " + tileLength);
        for (Component component : components.values()) {
            component.draw();
            component.drawDebugText();
            LocalPoint drawPoint = convertToLocalPoint(component.getPoint());
            canvas.drawBitmap(component.getRenderImage(), drawPoint.x, drawPoint.y, Paints.IMAGE_OPAQUE);
        }
        for (Component component : components.values()) {
            component.drawWires(canvas);
        }
        drawStatusBar();
    }

    private void drawStatusBar() {
        Paint paint = Paints.STATUS_BAR_TEXT;
        String name = stateManager.getStatusBarText();
        canvas.drawText(name, getSize().width / 2 - TextDrawUtil.getTextWidthPx(name, paint), screenManager.getDisplaySize().height - TextDrawUtil.getTextHeightPx(name, paint) - 10, paint);
    }

    /**
     * Storage format
     * --------------
     * 1 component per line
     * | = whitespace
     * storageID | posX | posY | [additional whitespace delineated data for component]
     */
    public boolean saveGrid(String slot) {
        try {
            File outputFile = getSaveFile(slot);
            FileWriter writer = new FileWriter(outputFile);
            List<Component> savedTiles = new LinkedList<>();
            for (Component component : components.values()) {
                writeRecursively(savedTiles, writer, component);
            }
            writer.flush();
            writer.close();
            return true;
        } catch (Exception ex) {
            System.out.println("Caught exception saving layout to slot " + slot + ": " + ex.getLocalizedMessage());
            ex.printStackTrace();
            return false;
        }
    }

    private void writeRecursively(List<Component> savedTiles, FileWriter writer, Component component) throws IOException {
        for (Component input : component.getInputs()) {
            writeRecursively(savedTiles, writer, input);
        }
        writeTile(savedTiles, writer, component);
    }

    private void writeTile(List<Component> savedTiles, FileWriter writer, Component component) throws IOException {
        if (component.getStorageID() == null || savedTiles.contains(component))
            return;
        savedTiles.add(component);
        String line = component.getStorageID() + " " + component.getPoint().x + " " + component.getPoint().y + " " + component.getAdditionalStorageData();
        writer.write(line + "\n");
    }

    public boolean loadGrid(String slot) {
        resetGrid();
        try {
            File inputFile = getSaveFile(slot);
            if (inputFile.exists()) {
                Scanner fileScanner = new Scanner(inputFile);
                while (fileScanner.hasNextLine()) {
                    String entry = fileScanner.nextLine();
                    Scanner entryScanner = new Scanner(entry);
                    String id = entryScanner.next();
                    int xPos = entryScanner.nextInt();
                    int yPos = entryScanner.nextInt();
                    Component component = loadTileForID(id, xPos, yPos);
                    if (component != null)
                        component.loadAdditionalStorageData(entryScanner);
                    entryScanner.close();
                }
                fileScanner.close();
                return true;
            }
            return false;
        } catch (Exception ex) {
            System.out.println("Caught exception loading layout for slot " + slot + ": " + ex.getLocalizedMessage());
            ex.printStackTrace();
            resetGrid();
            return false;
        }
    }

    private Component loadTileForID(String id, int xPos, int yPos) {
        GridPoint gridPoint = new GridPoint(xPos, yPos);
        if (id.equals("and"))
            setTile(gridPoint, new ANDGate(this));
        else if (id.equals("or"))
            setTile(gridPoint, new ORGate(this));
        else if (id.equals("not"))
            setTile(gridPoint, new NOTGate(this));
        else if (id.equals("switch"))
            setTile(gridPoint, new ComponentSwitch(this));
        else if (id.equals("led"))
            setTile(gridPoint, new ComponentLED(this));
        else {
            System.out.println("id not recognized for component trying to be added at " + gridPoint);
            return null;
        }

        return getTile(gridPoint);
    }

    private File getSaveFile(String slot) {
        return new File(screenManager.appContext.getFilesDir(), slot + ".logisim");
    }

    @Override
    public String getName() {
        return "Grid";
    }

    private void fillBackground() {
        this.canvas.drawRect(new Rect(0, 0, getSize().width, getSize().height), Paints.GRID_BACKGROUND_COLOR);
    }

    public GridPoint convertToGridPoint(LocalPoint localPoint) {
        int gridPointX = localPoint.x / tileLength;
        int gridPointY = localPoint.y / tileLength;
        return new GridPoint(gridPointX, gridPointY);
    }

    public GridPoint convertToGridPoint(ScreenPoint screenPoint) {
        return convertToGridPoint(convertToLocalPoint(screenPoint));
    }

    public LocalPoint convertToLocalPoint(GridPoint gridPoint) {
        return new LocalPoint(gridPoint.x * this.tileLength, gridPoint.y * this.tileLength);
    }

}
