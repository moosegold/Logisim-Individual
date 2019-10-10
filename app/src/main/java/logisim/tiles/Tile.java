package logisim.tiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import logisim.util.DebugTextDrawer;
import logisim.Grid;
import logisim.util.GridPoint;
import logisim.util.ScreenPoint;

/**
 * Maintains the state of something on the grid, so that it can be rendered.
 */
public abstract class Tile {

    protected final DebugTextDrawer debugText;

    protected final Grid grid;
    protected final GridPoint gridPoint;

    private Bitmap image;
    protected Canvas canvas;

    final Paint defaultBackgroundColor = new Paint();

    Tile(GridPoint gridPoint, Grid grid) {
        this.gridPoint = gridPoint;
        this.grid = grid;
        this.defaultBackgroundColor.setColor(Color.WHITE);
        this.debugText = new DebugTextDrawer(false);
    }

    public Tile(Tile tile) {
        this(tile.getPoint(), tile.grid);
    }

    public void handleTouch() {};

    public GridPoint getPoint() {
        return new GridPoint(gridPoint.x, gridPoint.y);
    }

    public void draw() {
        createCanvas();
        debugText.addText("gpos: " + getPoint());
    }

    public void drawDebugText() {
        debugText.draw(canvas);
    }

    private void createCanvas() {
        image = Bitmap.createBitmap(grid.tileLength, grid.tileLength, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(image);
    }

    public Rect getRect() {
        ScreenPoint screenPoint = grid.convertToScreenPoint(gridPoint);
        return new Rect(0, 0, grid.tileLength, grid.tileLength);
    }

    public Bitmap getImage() {
        return image;
    }

}