package logisim.state.modes;


import logisim.state.IStateHolder;
import logisim.state.StateManager;
import logisim.util.ScreenPoint;

/**
 * Default state of the app.
 */
public class NormalMode implements IMode {

    @Override
    public void updateDrag(ScreenPoint screenPoint) {
//        stateManager.setStatusBarText("");
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
