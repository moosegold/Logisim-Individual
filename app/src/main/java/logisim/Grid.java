package logisim;


import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

import logisim.tiles.components.concrete.ANDGate;
import logisim.tiles.components.concrete.NOTGate;
import logisim.tiles.components.concrete.ORGate;
import logisim.tiles.Tile;
import logisim.tiles.EmptyTile;
import logisim.util.GridPoint;
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

    public ScreenManager screenManager;

    private List<Tile> tiles;

    /**
     * The tile touched when a touch or drag began.
     */
    private GridPoint tileBeingTouched;
    private boolean touchInProgress;

    public Grid(int width, int height, int tileLength, ScreenManager screenManager, ScreenPoint origin, Size size) {
        super(origin, size, screenManager);
        this.gridSize = new Size(width, height);
        this.tileLength = tileLength;
        this.screenManager = screenManager;
        this.tiles = new LinkedList<>();
        resetGrid();

        //Test
        setTile(new GridPoint(0, 0), new ANDGate(getTile(new GridPoint(0, 0))));
        setTile(new GridPoint(1, 1), new ORGate(getTile(new GridPoint(1, 1))));
        setTile(new GridPoint(2, 3), new NOTGate(getTile(new GridPoint(2, 3))));
    }

    public void resetGrid() {
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

    public ScreenPoint convertToScreenPoint(GridPoint gridPoint) {
        return new ScreenPoint(gridPoint.x * this.tileLength, gridPoint.y * this.tileLength);
    }

    private int getTileIndex(GridPoint gridPoint) {
        return gridPoint.y * (gridSize.width + 1) + gridPoint.x;
    }

    public void processTouchUp(ScreenPoint localPoint) {
        GridPoint gridPoint = convertToGridPoint(localPoint);
        if (tileBeingTouched != null && tileBeingTouched.equals(gridPoint)) {
            System.out.println("[" + getName() + "] Touched tile: " + gridPoint);
            Tile tileTouched = getTileTouched(localPoint);
            if (tileTouched != null) {
                tileTouched.handleTouch();
            }
        }
        if (screenManager.dragSourceButton != null)
            screenManager.dragSourceButton.createNewComponent(gridPoint, this);
        tileBeingTouched = null;
        touchInProgress = false;
    }

    public void processTouchDown(ScreenPoint localPoint) {
        GridPoint gridTouch = convertToGridPoint(localPoint);
        if (!touchInProgress) {
            tileBeingTouched = gridTouch;
        }
        touchInProgress = true;
    }

    public void processTouchDrag(ScreenPoint localPoint) {

    }

    private Tile getTileTouched(ScreenPoint localPoint) {
        GridPoint gridPoint = convertToGridPoint(localPoint);
        Tile tile = getTile(gridPoint);
        return tile;
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
            canvas.drawBitmap(tile.getImage(), drawPoint.x, drawPoint.y, null);
        }
        drawNameOfDraggedComponent();
    }

    private void drawNameOfDraggedComponent() {
        Paint paint = Paints.DRAGGED_COMPONENT_TEXT;
        String name = screenManager.getNameOfDraggedComponent();
        canvas.drawText(name, getSize().width / 2 - TextDrawUtil.getTextWidthPx(name, paint), screenManager.getDisplaySize().height - TextDrawUtil.getTextHeightPx(paint) - 10, paint);
    }

    @Override
    public String getName() {
        return "Grid";
    }

    private void fillBackground() {
        this.canvas.drawRect(new Rect(0, 0, getSize().width, getSize().height), Paints.GRID_BACKGROUND_COLOR);
    }

    private GridPoint convertToGridPoint(ScreenPoint localPoint) {
        int gridPointX = localPoint.x / tileLength;
        int gridPointY = localPoint.y / tileLength;
        return new GridPoint(gridPointX, gridPointY);
    }

}