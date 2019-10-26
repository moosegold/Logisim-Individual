package logisim.tiles;

import android.graphics.Rect;

import logisim.Grid;
import logisim.util.GridPoint;
import logisim.util.Paints;

public class EmptyTile extends Tile {

    public EmptyTile(GridPoint gridPoint, Grid grid) {
        super(gridPoint, grid);
    }

    public EmptyTile(Tile tile) {
        super(tile);
    }

    @Override
    public void draw() {
        super.draw();
        fillTile();
        //drawBounds();
    }

    @Override
    public boolean isReplaceable() {
        return true;
    }

    @Override
    public void onTouch() {
        // Do Nothing
    }

    @Override
    public void onTap() {
        // Do Nothing
    }

    @Override
    public IDraggable onDrag() {
        return null;
    }

    @SuppressWarnings("unused")
    public void drawBounds() {
        canvas.drawRect(new Rect(0, 0, grid.tileLength - 1, grid.tileLength - 1), Paints.TILE_BORDER_COLOR_DEBUG);
    }

    private void fillTile() {
        canvas.drawRect(getRect(), Paints.GRID_BACKGROUND_COLOR);
    }

}
