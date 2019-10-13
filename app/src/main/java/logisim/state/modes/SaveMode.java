package logisim.state.modes;


import logisim.sidebar.SaveButton;
import logisim.sidebar.SaveSlotButton;
import logisim.state.IStateHolder;
import logisim.state.StateManager;
import logisim.state.states.GridComponentTouchState;
import logisim.state.states.SidebarButtonTouchState;
import logisim.state.states.WaitingState;
import logisim.util.ScreenPoint;

public class SaveMode extends AbstractMode {

    public SaveMode(StateManager stateManager) {
        super(stateManager);
        stateManager.setStatusBarText("Select A Save Slot");
    }

    @Override
    public void processTap(Object touchedObject) {
        if (!(touchedObject instanceof SaveSlotButton))
            stateManager.resetMode();
    }

    @Override
    public void updateDrag(ScreenPoint screenPoint) {

    }

    @Override
    public void processDrag(Object dest) {
        stateManager.resetMode();
    }

    @Override
    public void draw() {

    }

    @Override
    public void finalizeMode() {
        stateManager.setStatusBarText("");
    }

}
