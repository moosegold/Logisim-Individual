package logisim.state.modes;

import logisim.Grid;
import logisim.state.StateManager;
import logisim.tiles.Tile;
import logisim.tiles.components.Component;
import logisim.util.Paints;
import logisim.util.ScreenPoint;

public class WireMode extends AbstractMode {

    Grid grid;

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
    public void processDrag(Object dest) {
        if (dest instanceof Component) {
            Component component = (Component) dest;
            if (isLoopSafe((Component) stateManager.getDraggedObject(), (Tile) dest))
                component.processConnection((Component) stateManager.getDraggedObject());
        }
        stateManager.resetMode();
    }

    private boolean isLoopSafe(Tile source, Tile dest) {
        for (Tile input : source.getInputs()) {
            if (input.equals(dest))
                return false;
            else
                return isLoopSafe(input, dest);
        }
        return true;
    }

    @Override
    public void draw() {
        stateManager.debugText.addText("Source: " + stateManager.getTouchedObjectStart());
        Component source = (Component) stateManager.getTouchedObjectStart();
        ScreenPoint start = grid.convertToScreenPoint(source.getOutputPos());
        ScreenPoint end = stateManager.getDragPoint();
        stateManager.debugText.addText("Start: " + start);
        stateManager.debugText.addText("End: " + end);
        stateManager.canvas.drawLine(start.x, start.y, end.x, end.y, Paints.WIRE);
    }

    @Override
    public void finalizeMode() {

    }
}
