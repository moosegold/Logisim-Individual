package LogiSim;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

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
        drawBounds();
    }

    private void drawBounds() {
        Paint boundsPaint = new Paint();
        boundsPaint.setColor(Color.BLACK);
        boundsPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(getRect(), boundsPaint);
    }

    private void fillTile() {
        canvas.drawRect(getRect(), defaultBackgroundColor);
    }

}
