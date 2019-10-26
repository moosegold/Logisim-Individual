package logisim.state.modes;

import android.graphics.Canvas;

import logisim.Grid;
import logisim.sidebar.ComponentSidebarButton;
import logisim.state.StateManager;
import logisim.tiles.Tile;
import logisim.util.Paints;
import logisim.util.ScreenPoint;
import logisim.util.Util;

public class AddMode extends AbstractMode {

    private final ComponentSidebarButton button;
    private final Grid grid;

    public AddMode(StateManager stateManager, ComponentSidebarButton button, Grid grid) {
        super(stateManager);
        this.button = button;
        this.grid = grid;
        stateManager.setStatusBarText(button.label);
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
                button.createNewComponent(tile.getPoint(), grid);
        }
        stateManager.resetMode();
    }

    @Override
    public void draw() {
        ScreenPoint dragPoint = stateManager.getDragPoint();
        Util.drawDraggedObject(stateManager.canvas, button.getComponentImage(), dragPoint);
        drawTileOutlines(stateManager.canvas);

    }

    private void drawTileOutlines(Canvas mainCanvas) {
        ScreenPoint dragPoint = stateManager.getDragPoint();
        Tile tileOver = grid.getTile(grid.convertToGridPoint(dragPoint));
        if (grid.containsTouch(dragPoint) && tileOver != null) {
            Util.drawTileOutline(tileOver, grid, mainCanvas,
                    tileOver.isReplaceable() ? Paints.TILE_OUTLINE_ALLOW_PLACE : Paints.TILE_OUTLINE_DENY_PLACE);
        }
    }

    @Override
    public void finalizeMode() {
        stateManager.setStatusBarText("");
    }
}
