package LogiSim;

import android.graphics.Canvas;

public class EmptyTile extends AbstractTile {

    public EmptyTile(GridPoint gridPoint, Grid grid, ScreenManager screenManager, Canvas canvas) {
        super(gridPoint, grid, screenManager, canvas);
    }

    EmptyTile(AbstractTile tile) {
        super(tile);
    }

    @Override
    void draw(Canvas canvas) {
        fillTile();
    }

    void fillTile() {
        canvas.drawRect(getRect(), defaultBackgroundColor);
    }

}
