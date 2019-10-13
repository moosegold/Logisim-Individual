package logisim;


import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import logisim.state.states.GridComponentTouchState;
import logisim.state.StateManager;
import logisim.tiles.components.Component;
import logisim.tiles.Tile;
import logisim.tiles.EmptyTile;
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

/**
 * Maintains the grid and components.
 */
public class Grid extends AbstractScreenPartition {
    // The length of the grid in tiles (not pixels).
    public final Size gridSize;
    // the length in pixels of a tile. The tiles are square so only 1 value is needed.
    public final int tileLength;

    private List<Tile> tiles;

    /**
     * The tile touched when a touch or drag began.
     */
    private GridPoint tileBeingTouched;
    private boolean touchInProgress;

    public Grid(int width, int height, int tileLength, ScreenManager screenManager, StateManager stateManager, ScreenPoint origin, Size size) {
        super(origin, size, screenManager, stateManager);
        this.gridSize = new Size(width, height);
        this.tileLength = tileLength;
        this.tiles = new LinkedList<>();
        resetGrid();
    }

    public void resetGrid() {
        tiles.clear();
        fillGrid();
    }

    public void setTile(GridPoint point, Tile tile) {
        tiles.set(getTileIndex(point), tile);
    }

    public void setTile(Tile tile) {
        tiles.set(getTileIndex(tile.getPoint()), tile);
    }

    private void fillGrid() {
        int i = 0;
        for (int y = 0; y <= gridSize.height; y++) {
            for (int x = 0; x <= gridSize.width; x++) {
                GridPoint point = new GridPoint(x, y);
                tiles.add(new EmptyTile(point, this));
            }
        }
    }

    @Nullable
    public Tile getTile(GridPoint point) {
        if (
                point.x > gridSize.width || point.x < 0 ||
                        point.y > gridSize.height || point.y < 0) {
            return null;
        }
        return tiles.get(getTileIndex(point));
    }

    private int getTileIndex(GridPoint gridPoint) {
        return gridPoint.y * (gridSize.width + 1) + gridPoint.x;
    }

    public void processTouchUp(LocalPoint localPoint) {

    }

    public void processTouchDown(LocalPoint localPoint) {
        Tile touchedTile = getTileTouched(localPoint);
        if (touchedTile != null && touchedTile.isDraggable())
            stateManager.trySetState(new GridComponentTouchState(this, touchedTile, convertToScreenPoint(localPoint)));
    }

    public void processTouchDrag(LocalPoint localPoint) {

    }

    @Override
    public IInteractable getTouchedObject(LocalPoint localPoint) {
        return getTile(convertToGridPoint(localPoint));
    }

    private Tile getTileTouched(LocalPoint localPoint) {
        GridPoint gridPoint = convertToGridPoint(localPoint);
        Tile tile = getTile(gridPoint);
        return tile;
    }

    private boolean tileIsComponent(GridPoint gridPoint) {
        Tile tile = getTile(gridPoint);
        return tile instanceof Component;
    }

    public void moveTile(Tile src, Tile dest) {
        clearTile(src);
        src.setPoint(dest.getPoint());
        setTile(src.getPoint(), src);
    }

    private void clearTile(Tile tile) {
        setTile(tile.getPoint(), new EmptyTile(tile));
    }

    @Override
    public void draw() {
        fillBackground();
        screenManager.debugText.addText("");
        screenManager.debugText.addText("Grid Size: " + gridSize);
        screenManager.debugText.addText("Tile Length: " + tileLength);
        //screenManager.debugText.addText("Last Tile: " + (lastTouched == null ? null : lastTouched.getPoint()));
        for (Tile tile : tiles) {
            tile.draw();
            tile.drawDebugText();
            LocalPoint drawPoint = convertToLocalPoint(tile.getPoint());
            canvas.drawBitmap(tile.getImage(), drawPoint.x, drawPoint.y, Paints.IMAGE_OPAQUE);
        }
        for (Tile tile : tiles) {
            if (tile instanceof Component) {
                ((Component) tile).drawWires(canvas);
            }
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
     * | = space
     * storageID | posX | posY | [additional whitespace delineated data for component]
     */
    public boolean saveGrid(String slot) {
        try {
            File outputFile = getSaveFile(slot);
            FileWriter writer = new FileWriter(outputFile);
            List<Tile> savedTiles = new LinkedList<>();
            for (Tile tile : tiles) {
                writeRecursively(savedTiles, writer, tile);
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

    private void writeRecursively(List<Tile> savedTiles, FileWriter writer, Tile tile) throws IOException {
        for (Tile input : tile.getInputs()) {
            writeRecursively(savedTiles, writer, input);
        }
        writeTile(savedTiles, writer, tile);
    }

    private void writeTile(List<Tile> savedTiles, FileWriter writer, Tile tile) throws IOException {
        if (tile.getStorageID() == null || savedTiles.contains(tile))
            return;
        savedTiles.add(tile);
        String line = tile.getStorageID() + " " + tile.getPoint().x + " " + tile.getPoint().y + " " + tile.getAdditionalStorageData();
        System.out.println(line);
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
                    System.out.println(entry);
                    Scanner entryScanner = new Scanner(entry);
                    String id = entryScanner.next();
                    int xPos = entryScanner.nextInt();
                    int yPos = entryScanner.nextInt();
                    Tile tile = loadTileForID(id, xPos, yPos);
                    if (tile != null)
                        tile.loadAdditionalStorageData(entryScanner);
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

    private Tile loadTileForID(String id, int xPos, int yPos) {
        GridPoint gridPoint = new GridPoint(xPos, yPos);
        Tile existingTile = getTile(gridPoint);
        if (existingTile == null) {
            System.out.println("Not adding tile at " + gridPoint + " because there is no empty tile at that place");
            return null;
        }
        if (id.equals("and"))
            setTile(new ANDGate(existingTile));
        else if (id.equals("or"))
            setTile(new ORGate(existingTile));
        else if (id.equals("not"))
            setTile(new NOTGate(existingTile));
        else if (id.equals("switch"))
            setTile(new ComponentSwitch(existingTile));
        else if (id.equals("led"))
            setTile(new ComponentLED(existingTile));
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

    public ScreenPoint convertToScreenPoint(GridPoint gridPoint) {
        return convertToScreenPoint(convertToLocalPoint(gridPoint));
    }

}
