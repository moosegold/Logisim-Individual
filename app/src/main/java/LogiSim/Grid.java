package LogiSim;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

/**
 * Maintains the grid and components.
 */
class Grid extends AbstractScreenPartition {
    // The length of the grid in tiles (not pixels).
    final Size gridSize;
    // the length in pixels of a tile. The tiles are square so only 1 value is needed.
    final int tileLength;

//    private List<UnaryComponent> components = new LinkedList<>();

    private List<AbstractTile> tiles;

    private AbstractTile lastTouched;

    Grid(int width, int height, int tileLength, ScreenManager screenManager, ScreenPoint origin, Size size) {
        super(origin, size, screenManager);
        this.gridSize = new Size(width, height);
        this.tileLength = tileLength;
        tiles = new LinkedList<>();
        resetGrid();

        //Test
        setTile(new GridPoint(0, 0), new ANDGate(getTile(new GridPoint(0, 0))));
        setTile(new GridPoint(1, 1), new ORGate(getTile(new GridPoint(1, 1))));
        setTile(new GridPoint(2, 3), new NOTGate(getTile(new GridPoint(2, 3))));
    }

    void resetGrid() {
        fillGrid();
    }

    private void setTile(GridPoint point, AbstractTile tile) {
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
    AbstractTile getTile(GridPoint point) {
        if (
                point.x > gridSize.width || point.x < 0 ||
                point.y > gridSize.height || point.y < 0) {
            return null;
        }
        return tiles.get(getTileIndex(point));
    }

    ScreenPoint convertToScreenPoint(GridPoint gridPoint) {
        return new ScreenPoint(gridPoint.x * this.tileLength, gridPoint.y * this.tileLength);
    }

    private int getTileIndex(GridPoint gridPoint) {
        return gridPoint.y * (gridSize.width + 1) + gridPoint.x;
    }

    @Override
    public void processTouch(ScreenPoint localPoint) {
        GridPoint gridPoint = convertToGridPoint(localPoint);
        AbstractTile tile = getTile(gridPoint);
        lastTouched = tile;
        if (tile != null) {
            tile.debugText.setActive(!tile.debugText.getActive());
        }
    }

    @Override
    public void draw() {
        fillBackground();
        screenManager.debugText.addText("");
        screenManager.debugText.addText("Grid Size: " + gridSize);
        screenManager.debugText.addText("Tile Length: " + tileLength);
        screenManager.debugText.addText("Last Tile: " + (lastTouched == null ? null : lastTouched.getPoint()));
        for (AbstractTile tile : tiles) {
            tile.draw();
            tile.drawDebugText();
            ScreenPoint drawPoint = convertToScreenPoint(tile.getPoint());
            canvas.drawBitmap(tile.getImage(), drawPoint.x, drawPoint.y, null);
        }
    }

    @Override
    public String getName() {
        return "Grid";
    }

    private void fillBackground() {
        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);
        this.canvas.drawRect(new Rect(0, 0, getSize().width, getSize().height), backgroundPaint);
    }

    private GridPoint convertToGridPoint(ScreenPoint localPoint) {
        int gridPointX = localPoint.x / tileLength;
        int gridPointY = localPoint.y / tileLength;
        return new GridPoint(gridPointX, gridPointY);
    }

}
