package logisim.state.states;


import logisim.ScreenManager;
import logisim.util.ScreenPoint;
import logisim.util.TouchAction;

/**
 * For setting the status bar after a action is performed.
 */
public class ShowMessageState extends WaitingState {

    private String message;

    public ShowMessageState(String message) {
        this.message = message;
    }

    @Override
    public void update(ScreenPoint screenPoint, TouchAction action) {
        super.update(screenPoint, action);
        stateManager.setState(new WaitingState());
    }

    @Override
    public void initState() {
        stateManager.setStatusBarText(message);
    }

    @Override
    public void finalizeState() {
        stateManager.setStatusBarText("");
    }


}
