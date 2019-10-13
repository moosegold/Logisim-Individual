package logisim.state.states;


public class ShowMessageState extends WaitingState {

    private String message;

    public ShowMessageState(String message) {
        this.message = message;
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
