package logisim.state.states;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import logisim.Grid;
import logisim.tiles.EmptyTile;
import logisim.tiles.IDraggable;
import logisim.tiles.Tile;
import logisim.util.Paints;
import logisim.util.ScreenPoint;
import logisim.util.TouchAction;
import logisim.util.Util;

public class TileMoveDragState extends AbstractStateHolder {

    private Tile dragSource;
    private ScreenPoint dragPoint;
    private Grid grid;

    public TileMoveDragState(Tile dragSource, Grid grid, ScreenPoint touchPoint) {
        this.dragSource = dragSource;
        this.grid = grid;
        this.dragPoint = touchPoint;
    }

    @Override
    public void update(ScreenPoint screenPoint, TouchAction action) {
        dragPoint = screenPoint;
        Tile tileOver = grid.getTile(grid.convertToGridPoint(screenPoint));
        if (action == TouchAction.UP) {
            isValid = false;
            if (grid.containsTouch(screenPoint)) {
                if (tileOver != null && tileOver.isReplaceable())
                    grid.moveTile(dragSource, tileOver);
            } else {
                grid.setTile(dragSource.getPoint(), new EmptyTile(dragSource));
            }
        }
    }

    @Override
    public void drawState(Canvas mainCanvas) {
        Util.drawDraggedObject(mainCanvas, ((IDraggable) dragSource).getComponentImage(), dragPoint);
        drawTileOutlines(mainCanvas);
    }

    private void drawTileOutlines(Canvas mainCanvas) {
        Util.drawTileOutline(dragSource, grid, mainCanvas, Paints.TILE_OUTLINE_SOURCE);
        Tile tileOver = grid.getTile(grid.convertToGridPoint(dragPoint));
        if (grid.containsTouch(dragPoint) && tileOver != null && tileOver != dragSource) {
            Util.drawTileOutline(tileOver, grid, mainCanvas,
                    tileOver.isReplaceable() ? Paints.TILE_OUTLINE_ALLOW_PLACE : Paints.TILE_OUTLINE_DENY_PLACE);
        }
    }

    @Override
    public void initState() {
        stateManager.setStatusBarText(((IDraggable) dragSource).getName());
        screenManager.draw();
    }

    @Override
    public void finalizeState() {
        stateManager.setStatusBarText("");
    }

}
