package logisim.state.states;

import android.graphics.Canvas;

import java.lang.reflect.Constructor;

import logisim.Grid;
import logisim.sidebar.ComponentSidebar;
import logisim.sidebar.ComponentSidebarButton;
import logisim.tiles.EmptyTile;
import logisim.tiles.IDraggable;
import logisim.tiles.Tile;
import logisim.tiles.components.Component;
import logisim.util.Paints;
import logisim.util.ScreenPoint;
import logisim.util.TouchAction;
import logisim.util.Util;

public class ComponentAddDragState extends AbstractStateHolder {

    private Grid grid;

    private ComponentSidebarButton button;
    private ScreenPoint dragPoint;

    public ComponentAddDragState(ComponentSidebarButton button, Grid grid, ScreenPoint touchPoint) {
        this.button = button;
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
                if (tileOver != null && tileOver.isReplaceable()) {
                    button.createNewComponent(tileOver.getPoint(), grid);
                }
            }
        }
    }

    @Override
    public void drawState(Canvas mainCanvas) {
        Util.drawDraggedObject(mainCanvas, button.getComponentImage(), dragPoint);
        drawTileOutlines(mainCanvas);
    }

    private void drawTileOutlines(Canvas mainCanvas) {
        Tile tileOver = grid.getTile(grid.convertToGridPoint(dragPoint));
        if (grid.containsTouch(dragPoint) && tileOver != null) {
            Util.drawTileOutline(tileOver, grid, mainCanvas,
                    tileOver.isReplaceable() ? Paints.TILE_OUTLINE_ALLOW_PLACE : Paints.TILE_OUTLINE_DENY_PLACE);
        }
    }

    @Override
    public void initState() {
        stateManager.setStatusBarText(button.label);
        screenManager.draw();
    }

    @Override
    public void finalizeState() {
        stateManager.setStatusBarText("");
    }


}
