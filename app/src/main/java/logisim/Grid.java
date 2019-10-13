package logisim;


import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;

import logisim.state.states.GridComponentTouchState;
import logisim.state.StateManager;
import logisim.tiles.components.Component;
import logisim.tiles.components.concrete.ANDGate;
import logisim.tiles.components.concrete.NOTGate;
import logisim.tiles.components.concrete.ORGate;
import logisim.tiles.Tile;
import logisim.tiles.EmptyTile;
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

//    private Component dragSource;
//    private GridPoint dragPoint;

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

        //Test
        setTile(new GridPoint(0, 0), new ANDGate(getTile(new GridPoint(0, 0))));
        setTile(new GridPoint(1, 1), new ORGate(getTile(new GridPoint(1, 1))));
        setTile(new GridPoint(2, 3), new NOTGate(getTile(new GridPoint(2, 3))));
    }

    public void resetGrid() {
        tiles.clear();
        fillGrid();
    }

    public void setTile(GridPoint point, Tile tile) {
        tiles.set(getTileIndex(point), tile);
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
    public Object getTouchedObject(LocalPoint localPoint) {
        return getTile(convertToGridPoint(localPoint));
    }

//    public void processTouchUp(ScreenPoint localPoint) {
//        GridPoint gridPoint = convertToGridPoint(localPoint);
//        if (tileBeingTouched != null && tileBeingTouched.equals(gridPoint)) {
//            System.out.println("[" + getName() + "] Touched tile: " + gridPoint);
//            Tile tileTouched = getTileTouched(localPoint);
//            if (tileTouched != null) {
//                tileTouched.handleTouch();
//            }
//        }
//        if (screenManager.dragSourceButton != null) {
//            if (!tileIsComponent(gridPoint))
//                screenManager.dragSourceButton.createNewComponent(gridPoint, this);
//            screenManager.setStatusBarText("");
//        }
//        if (dragSource != null) {
//            Tile dest = getTile(gridPoint);
//            if (!containsTouch(localPoint)) {
//                // Reset the drag source if the component is dragged of the grid.
//                // This is either the sidebar, or the right and bottom edges of the screen.
//                clearTile(dragSource);
//            } else if (dest != null && !tileIsComponent(gridPoint)) {
//                moveTile(dragSource, dest);
//            }
//            dragSource = null;
//            screenManager.setStatusBarText("");
//        }
//        tileBeingTouched = null;
//        touchInProgress = false;
//        pressHoldTimer.cancel();
//    }
//
//    public void processTouchDown(ScreenPoint localPoint) {
//        GridPoint gridTouch = convertToGridPoint(localPoint);
//        if (!touchInProgress) {
//            tileBeingTouched = gridTouch;
//            pressHoldTimer = new Timer();
//            pressHoldTimer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    beginDrag();
//                }
//            }, 500);
//        }
//        touchInProgress = true;
//    }
//
//    public void processTouchDrag(ScreenPoint localPoint) {
//        GridPoint gridTouch = convertToGridPoint(localPoint);
//        dragPoint = gridTouch;
//        if (tileBeingTouched != null && !tileBeingTouched.equals(gridTouch)) {
//            pressHoldTimer.cancel();
//            beginWireCreation();
//        }
//    }

//    private void beginDrag() {
//        if (getTile(tileBeingTouched) instanceof Component) {
//            Component touchedComponent = (Component) getTile(tileBeingTouched);
//            dragSource = touchedComponent;
//            screenManager.setDraggedObject(touchedComponent.getComponentImage());
//            screenManager.setStatusBarText(touchedComponent.getName());
//            screenManager.draw();
//        }
//    }

//    private void beginWireCreation() {
//
//    }

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
            ScreenPoint drawPoint = convertToScreenPoint(tile.getPoint());
            canvas.drawBitmap(tile.getImage(), drawPoint.x, drawPoint.y, Paints.IMAGE_OPAQUE);
        }
//        if (touchInProgress && dragPoint != null && getTile(dragPoint) != null) {
//            drawTileOutline(getTile(dragPoint), tileIsComponent(dragPoint) ? Paints.TILE_OUTLINE_DENY_PLACE : Paints.TILE_OUTLINE_ALLOW_PLACE);
//            if (dragSource != null) {
//                drawTileOutline(dragSource, Paints.TILE_OUTLINE_SOURCE);
//            }
//        }
        drawStatusBar();
    }

//    public void drawTileOutline(Tile tile, Paint paint) {
//        if (tile != null) {
//            ScreenPoint pos = convertToScreenPoint(tile.getPoint());
//            Size size = new Size(tile.getRect().width(), tile.getRect().height());
//            int lineWidth = tile.getRect().width() / 8;
//            int lineHeight = tile.getRect().height() / 8;
//            // Top left corner
//            canvas.drawLine(pos.x, pos.y, pos.x + lineWidth, pos.y, paint);
//            canvas.drawLine(pos.x, pos.y, pos.x, pos.y + lineHeight, paint);
//            // Top right corner
//            canvas.drawLine(pos.x + size.width, pos.y, pos.x + size.width - lineWidth, pos.y, paint);
//            canvas.drawLine(pos.x + size.width, pos.y, pos.x + size.width, pos.y + lineHeight, paint);
//            // Bottom left corner
//            canvas.drawLine(pos.x, pos.y + size.height, pos.x + lineWidth, pos.y + size.height, paint);
//            canvas.drawLine(pos.x, pos.y + size.height, pos.x, pos.y + size.height - lineHeight, paint);
//            // Bottom right corner
//            canvas.drawLine(pos.x + size.width, pos.y + size.height, pos.x + size.width - lineWidth, pos.y + size.height, paint);
//            canvas.drawLine(pos.x + size.width, pos.y + size.height, pos.x + size.width, pos.y + size.height - lineHeight, paint);
//        }
//    }

    private void drawStatusBar() {
        Paint paint = Paints.STATUS_BAR_TEXT;
        String name = stateManager.getStatusBarText();
        canvas.drawText(name, getSize().width / 2 - TextDrawUtil.getTextWidthPx(name, paint), screenManager.getDisplaySize().height - TextDrawUtil.getTextHeightPx(name, paint) - 10, paint);
    }

    public boolean loadGrid(String slot) {
        try {
            File inputFile = getSaveFile(slot);
            if (inputFile.exists()) {
                FileInputStream inStream = new FileInputStream(inputFile);
                // READ GRID
                inStream.close();
                return true;
            }
            return false;
        } catch (Exception ex) {
            System.out.println("Caught exception loading layout for slot " + slot + ": " + ex.getLocalizedMessage());
            resetGrid();
            return false;
        }
    }

    public boolean saveGrid(String slot) {
        try {
            File outputFile = getSaveFile(slot);
            FileOutputStream outStream = new FileOutputStream(outputFile);
            // WRITE GRID
            outStream.close();
            return true;
        } catch (Exception ex) {
            System.out.println("Caught exception saving layout to slot " + slot + ": " + ex.getLocalizedMessage());
            return false;
        }
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

    public ScreenPoint convertToScreenPoint(GridPoint gridPoint) {
        return new ScreenPoint(gridPoint.x * this.tileLength, gridPoint.y * this.tileLength);
    }

}
