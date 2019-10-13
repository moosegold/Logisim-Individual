package logisim.state.modes;


import logisim.state.IStateHolder;
import logisim.state.StateManager;
import logisim.util.ScreenPoint;

/**
 * Default state of the app.
 */
public class NormalMode extends AbstractMode {

    public NormalMode(StateManager stateManager) {
        super(stateManager);
    }

    @Override
    public void updateDrag(ScreenPoint screenPoint) {

    }

    @Override
    public void processTap(Object touchedObject) {

    }

    @Override
    public void processDrag(Object dest) {

    }

    @Override
    public void draw() {

    }

    @Override
    public void finalizeMode() {

    }

}
