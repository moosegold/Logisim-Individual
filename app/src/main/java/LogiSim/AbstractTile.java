package LogiSim;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Maintains the state of something on the grid, so that it can be rendered.
 */
abstract class AbstractTile {

    final GridPoint gridPoint;
    final Grid grid;
    final ScreenManager screenManager;
    final Canvas canvas;

    final Paint defaultBackgroundColor = new Paint();

    AbstractTile(GridPoint gridPoint, Grid grid, ScreenManager screenManager, Canvas canvas) {
        this.gridPoint = gridPoint;
        this.grid = grid;
        this.screenManager = screenManager;
        this.canvas = canvas;

        this.defaultBackgroundColor.setColor(Color.WHITE);
    }

    AbstractTile(AbstractTile tile) {
        this(tile.getPoint(), tile.grid, tile.screenManager, tile.canvas);
    }

    GridPoint getPoint() {
        return new GridPoint(gridPoint.x, gridPoint.y);
    }

    abstract void draw(Canvas canvas);

    Rect getRect() {
        ScreenPoint screenPoint = grid.convertToScreenPoint(gridPoint);
        return new Rect(
                screenPoint.x, screenPoint.y, screenPoint.x + grid.tileSize, screenPoint.y + grid.tileSize);
    }

}
