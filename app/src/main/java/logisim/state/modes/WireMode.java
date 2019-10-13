package logisim.state.modes;

import logisim.state.StateManager;
import logisim.tiles.components.Component;
import logisim.tiles.components.ILogicComponent;
import logisim.util.ScreenPoint;

public class WireMode extends AbstractMode {

    public WireMode(StateManager stateManager) {
        super(stateManager);
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
    }

    @Override
    public void draw() {

    }

    @Override
    public void finalizeMode() {

    }
}
