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
    public void process(IStateHolder state) {
        require(state instanceof SidebarButtonTouchState || state instanceof WaitingState);

        if (state instanceof SidebarButtonTouchState) {
            SidebarButtonTouchState touchState = (SidebarButtonTouchState) state;
            require(touchState.getButton() instanceof SaveSlotButton ||
                    touchState.getButton() instanceof SaveButton);
        }
    }

    @Override
    public void finalizeMode() {

    }

}
