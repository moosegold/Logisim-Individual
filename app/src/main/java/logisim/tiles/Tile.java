package logisim.tiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import logisim.IInteractable;
import logisim.util.DebugTextDrawer;
import logisim.Grid;
import logisim.util.GridPoint;
import logisim.util.LocalPoint;

/**
 * Maintains the state of something on the grid, so that it can be rendered.
 */
public abstract class Tile implements IInteractable {

    protected final DebugTextDrawer debugText;

    protected final Grid grid;
    protected GridPoint gridPoint;

    private Bitmap image;
    protected Canvas canvas;

    private boolean onGrid = true;

    Tile(GridPoint gridPoint, Grid grid) {
        this.gridPoint = gridPoint;
        this.grid = grid;
        this.debugText = new DebugTextDrawer(false);
    }

    public Tile(Tile tile) {
        this(tile.getPoint(), tile.grid);
    }

    public GridPoint getPoint() {
        return gridPoint.copy();
    }

    public void setPoint(GridPoint gridPoint) {
        this.gridPoint = gridPoint;
    }

    public void draw() {
        createCanvas();
        debugText.addText("gpos: " + getPoint());
    }

    public void drawDebugText() {
        debugText.draw(canvas);
    }

    public final boolean isDraggable() {
        return this instanceof IDraggable;
    }

    public abstract boolean isReplaceable();

    private void createCanvas() {
        image = Bitmap.createBitmap(grid.tileLength, grid.tileLength, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(image);
    }

    public Rect getRect() {
        LocalPoint localPoint = grid.convertToLocalPoint(gridPoint);
        return new Rect(0, 0, grid.tileLength, grid.tileLength);
    }

    public Bitmap getImage() {
        return image;
    }

    public boolean onGrid() {
        return onGrid;
    }

    public void removeFromGrid() {
        this.onGrid = false;
    }

    /**
     * Used to differentiate components when reading from storage.
     * returns null if the tile is not written to disk
     */
    public String getStorageID() {
        return null;
    }

    public String additionalStorageData() {
        return "";
    }

    @NonNull
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + getPoint();
    }
}
