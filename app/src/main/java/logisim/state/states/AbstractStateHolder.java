package logisim.state.states;

import logisim.ScreenManager;
import logisim.state.IStateHolder;
import logisim.state.StateManager;

public abstract class AbstractStateHolder implements IStateHolder {

    protected StateManager stateManager;
    protected ScreenManager screenManager;
    protected boolean isValid = true;

    @Override
    public final void setManagers(StateManager stateManager, ScreenManager screenManager) {
        this.stateManager = stateManager;
        this.screenManager = screenManager;
    }

    @Override
    public boolean isValid() {
        return isValid;
    }

}
