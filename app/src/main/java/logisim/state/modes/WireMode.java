package logisim.state.modes;

import logisim.Grid;
import logisim.state.StateManager;
import logisim.tiles.components.Component;
import logisim.tiles.components.ILogicComponent;
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
            component.processConnection((ILogicComponent) stateManager.getDraggedObject());
        }
        stateManager.resetMode();
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
