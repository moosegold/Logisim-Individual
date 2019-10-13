package logisim.state.modes;

import android.graphics.Canvas;

import logisim.Grid;
import logisim.state.StateManager;
import logisim.tiles.EmptyTile;
import logisim.tiles.IDraggable;
import logisim.tiles.Tile;
import logisim.tiles.components.Component;
import logisim.util.Paints;
import logisim.util.ScreenPoint;
import logisim.util.Util;

public class MoveMode extends AbstractMode {

    private Grid grid;
    private Component start;

    public MoveMode(StateManager stateManager, Component start, Grid grid) {
        super(stateManager);
        this.start = start;
        this.grid = grid;
        stateManager.setStatusBarText(start.getName());
    }

    @Override
    public void updateDrag(ScreenPoint screenPoint) {

    }

    @Override
    public void processTap(Object touchedObject) {
        stateManager.resetMode();
    }

    @Override
    public void processDrag(Object dest) {
        if (dest instanceof Tile) {
            Tile tile = (Tile) dest;
            if (tile.isReplaceable())
                grid.moveTile(start, tile);
        } else {
            start.removeFromGrid();
            grid.setTile(start.getPoint(), new EmptyTile(start));
        }
        stateManager.resetMode();
    }

    @Override
    public void draw() {
        ScreenPoint dragPoint = stateManager.getDragPoint();
        Util.drawDraggedObject(stateManager.canvas, start.getComponentImage(), dragPoint);
        drawTileOutlines(stateManager.canvas);
    }

    private void drawTileOutlines(Canvas mainCanvas) {
        ScreenPoint dragPoint = stateManager.getDragPoint();
        IDraggable dragSource = stateManager.getDraggedObject();
        Util.drawTileOutline((Tile) dragSource, grid, mainCanvas, Paints.TILE_OUTLINE_SOURCE);
        Tile tileOver = grid.getTile(grid.convertToGridPoint(dragPoint));
        if (grid.containsTouch(dragPoint) && tileOver != null && tileOver != dragSource) {
            Util.drawTileOutline(tileOver, grid, mainCanvas,
                    tileOver.isReplaceable() ? Paints.TILE_OUTLINE_ALLOW_PLACE : Paints.TILE_OUTLINE_DENY_PLACE);
        }
    }

    @Override
    public void finalizeMode() {
        stateManager.setStatusBarText("");
    }
}
