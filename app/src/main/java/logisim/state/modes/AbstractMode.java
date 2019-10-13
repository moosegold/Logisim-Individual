package logisim.state.modes;


import logisim.state.StateManager;

public abstract class AbstractMode implements IMode {

    protected StateManager stateManager;

    public AbstractMode(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public void require(boolean test) {
        if (!test)
            stateManager.resetMode();
    }

}
