package logisim.state.modes;


import logisim.sidebar.SaveButton;
import logisim.sidebar.SaveSlotButton;
import logisim.state.IStateHolder;
import logisim.state.StateManager;
import logisim.state.states.GridComponentTouchState;
import logisim.state.states.SidebarButtonTouchState;
import logisim.state.states.WaitingState;

public class SaveMode extends AbstractMode {

    public SaveMode(StateManager stateManager) {
        super(stateManager);
    }

    @Override
    public void processTouch(Object touchedObject) {
        if (!(touchedObject instanceof SaveSlotButton))
            stateManager.resetMode();
    }

    @Override
    public void processDrag(Object dest) {
        stateManager.resetMode();
    }

    @Override
    public void finalizeMode() {

    }

}
