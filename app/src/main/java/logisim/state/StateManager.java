package logisim.state;

import android.graphics.Canvas;

import logisim.ScreenManager;
import logisim.sidebar.SaveButton;
import logisim.sidebar.SidebarButton;
import logisim.state.modes.IMode;
import logisim.state.modes.NormalMode;
import logisim.util.DebugTextDrawer;
import logisim.util.LocalPoint;
import logisim.util.ScreenPoint;
import logisim.util.TouchAction;

public class StateManager {

    private final Canvas canvas;
    public final ScreenManager screenManager;
    public final DebugTextDrawer debugText;

//    private IStateHolder currentState = new WaitingState();
    private IMode mode = new NormalMode();

    private boolean touchInProgress;
    private Object touchedObjectStart;

    private String statusBarText = "";

    public StateManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
        this.canvas = this.screenManager.getCanvas();
        debugText = new DebugTextDrawer(new LocalPoint(screenManager.getDisplaySize().width - 4, 4), true);
        debugText.alignRight = true;
    }

    public void update(ScreenPoint screenPoint, TouchAction action) {

        if (action == TouchAction.UP) {
            touchInProgress = false;
            Object touchedObject = screenManager.getTouchedObject(screenPoint);
            if (touchedObjectStart == touchedObject) {
                IMode tempMode = mode;
                if (touchedObjectStart instanceof SidebarButton)
                    ((SidebarButton) touchedObjectStart).handleTap();
                tempMode.processTouch(touchedObjectStart);
            } else {
                mode.processDrag(touchedObject);
            }
        } else if (action == TouchAction.DOWN) {
            if (!touchInProgress) {
                touchedObjectStart = screenManager.getTouchedObject(screenPoint);
            }
            touchInProgress = true;
        } else if (action == TouchAction.MOVE) {

        }

//        currentState.update(screenPoint, action);
//        if (!currentState.isValid() && !(currentState instanceof WaitingState)) {
//            currentState.finalizeState();
//            setState(new WaitingState());
//        }
    }

    public void draw() {
        debugText.addText("Mode: " + mode.getClass().getSimpleName());
//        debugText.addText("State: " + currentState.getClass().getSimpleName());
//        if (currentState.isValid()) {
//            currentState.drawState(canvas);
//        }
        debugText.draw(canvas);
    }

    /**
     * Only sets the state to newState if the current state is invalid.
     * The current state is the first thing to be updated after a touch so a state
     * can become invalidated and updated on the same press.
     */
    public void trySetState(IStateHolder newState) {
//        if (!currentState.isValid()) {
//            setState(newState);
//        }
    }

    /**
     * Sets the state without checking if the current state is valid or not.
     * Used for states that have multiple steps, like touching a grid tile -> drag.
     */
    public void setState(IStateHolder newState) {
//        currentState.finalizeState();
//        newState.setManagers(this, screenManager);
//        currentState = newState;
//        newState.initState();
//        mode.processTouch(newState);
    }

    public void resetMode() {
        setMode(new NormalMode());
    }

    public void setMode(IMode mode) {
        this.mode.finalizeMode();
        this.mode = mode;
    }

    public IMode getMode() {
        return mode;
    }

//    public IStateHolder getCurrentState() {
//        return currentState;
//    }

    public void setStatusBarText(String text) {
        if (text == null)
            statusBarText = "";
        else
            statusBarText = text;
    }

    public String getStatusBarText() {
        return statusBarText;
    }

}
