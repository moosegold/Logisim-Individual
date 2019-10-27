package logisim.state.modes;

import android.graphics.Paint;

import java.util.LinkedList;
import java.util.List;

import logisim.Grid;
import logisim.history.UndoProcedure;
import logisim.state.StateManager;
import logisim.tiles.components.Component;
import logisim.util.Paints;
import logisim.util.ScreenPoint;
import logisim.util.Util;

public class WireMode extends AbstractMode {

    private final Grid grid;

    public WireMode(StateManager stateManager, Grid grid) {
        super(stateManager);
        this.grid = grid;
    }

    @Override
    public void updateDrag(ScreenPoint screenPoint) {

    }

    @Override
    public void processTap(Object touchedObject) {
        stateManager.resetMode();
    }

    @Override
    public void processDrag(ScreenPoint screenPoint, Object dest) {
        if (dest instanceof Component) {
            Component component = (Component) dest;
            if (isLoopSafe((Component) stateManager.getDraggedObject(), (Component) dest)) {
                List<Component> oldComponents = new LinkedList<Component>(component.getInputs());
                component.processConnection((Component) stateManager.getDraggedObject());
                addWireingToHistory(oldComponents, component.getInputs(), component);
            }
        }
        stateManager.resetMode();
    }


    private void addWireingToHistory(List<Component> oldInputs, List<Component> newInputs, Component dest) {
        stateManager.history.pushAction("Updating inputs for " + dest, new UndoProcedure() {
            @Override
            public void performUndo() {
                setInputsTo(oldInputs);
            }

            @Override
            public void performRedo() {
                setInputsTo(newInputs);
            }

            private void setInputsTo(List<Component> state) {
                // Clear existing inputs
                for (int i = 0; i < dest.getInputs().size(); i++) {
                    dest.setInput(i, null);
                }
                for (int i = 0; i < state.size(); i++) {
                    dest.setInput(i, state.get(i));
                }
            }
        });
    }

    private boolean isLoopSafe(Component source, Component dest) {
        boolean isSafe = true;
        for (Component input : source.getInputs()) {
            if (input.equals(dest))
                isSafe = false;
            else
                isSafe = isLoopSafe(input, dest);
        }
        return isSafe;
    }

    @Override
    public void draw() {
        drawLine();
        drawSelection();
    }

    private void drawLine() {
        stateManager.debugText.addText("Source: " + stateManager.getTouchedObjectStart());
        Component source = (Component) stateManager.getTouchedObjectStart();
        ScreenPoint start = grid.convertToScreenPoint(source.getOutputPos());
        ScreenPoint end = stateManager.getDragPoint();
        stateManager.debugText.addText("Start: " + start);
        stateManager.debugText.addText("End: " + end);
        stateManager.canvas.drawLine(start.x, start.y, end.x, end.y, Paints.WIRE);
    }

    private void drawSelection() {
        Util.drawTileOutline((Component) stateManager.getTouchedObjectStart(), grid, stateManager.canvas, Paints.TILE_OUTLINE_SOURCE);
        ScreenPoint screenPoint = stateManager.getDragPoint();
        if (grid.containsTouch(screenPoint)) {
            Component hoverTile = grid.getTile(grid.convertToGridPoint(screenPoint));
            if (hoverTile != null && hoverTile != stateManager.getDraggedObject()) {
                Paint paint = isLoopSafe((Component) stateManager.getTouchedObjectStart(), hoverTile) && hoverTile.canAcceptWire() ? Paints.TILE_OUTLINE_ALLOW_PLACE : Paints.TILE_OUTLINE_DENY_PLACE;
                Util.drawTileOutline(hoverTile, grid, stateManager.canvas, paint);
            }
        }
    }

    @Override
    public void finalizeMode() {

    }
}
