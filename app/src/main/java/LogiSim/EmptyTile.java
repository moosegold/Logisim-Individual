package LogiSim;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class EmptyTile extends AbstractTile {

    public EmptyTile(GridPoint gridPoint, Grid grid) {
        super(gridPoint, grid);
    }

    EmptyTile(AbstractTile tile) {
        super(tile);
    }

    @Override
    void draw() {
        super.draw();
        fillTile();
        drawBounds();
    }

    private void drawBounds() {
        canvas.drawRect(new Rect(0, 0, grid.tileLength - 1, grid.tileLength - 1), Paints.TILE_BORDER_COLOR);
    }

    private void fillTile() {
        canvas.drawRect(getRect(), defaultBackgroundColor);
    }

}
