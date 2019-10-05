package LogiSim;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Maintains the state of something on the grid, so that it can be rendered.
 */
abstract class AbstractTile {

    final DebugTextDrawer debugText;

    final Grid grid;
    final GridPoint gridPoint;

    private Bitmap image;
    Canvas canvas;

    final Paint defaultBackgroundColor = new Paint();

    AbstractTile(GridPoint gridPoint, Grid grid) {
        this.gridPoint = gridPoint;
        this.grid = grid;
        this.defaultBackgroundColor.setColor(Color.WHITE);
        this.debugText = new DebugTextDrawer(true);
    }

    AbstractTile(AbstractTile tile) {
        this(tile.getPoint(), tile.grid);
    }

    GridPoint getPoint() {
        return new GridPoint(gridPoint.x, gridPoint.y);
    }

    void draw() {
        createCanvas();
        debugText.addText("gpos: " + getPoint());
    }

    void drawDebugText() {
        debugText.draw(canvas);
    }

    //TODO Remove duplicated code between here and SidebarButton
    private void createCanvas() {
        image = Bitmap.createBitmap(grid.tileLength, grid.tileLength, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(image);
    }

    Rect getRect() {
        ScreenPoint screenPoint = grid.convertToScreenPoint(gridPoint);
        return new Rect(0, 0, grid.tileLength, grid.tileLength);
    }

    public Bitmap getImage() {
        return image;
    }

}
