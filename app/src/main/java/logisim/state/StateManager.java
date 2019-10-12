package logisim.state;

import android.graphics.Canvas;

import logisim.util.ScreenPoint;
import logisim.util.TouchAction;

public class StateManager {

    private Canvas canvas;

    private IStateHolder currentState = new WaitingState();

    public StateManager(Canvas canvas) {
        this.canvas = canvas;
    }

    public void update(ScreenPoint screenPoint, TouchAction action) {
        if (currentState.isValid())
            currentState.update(screenPoint, action);
        if (!currentState.isValid()) {
            currentState.finalizeState();
            setState(new WaitingState());
        }
    }

    public void draw() {
        if (currentState.isValid())
            currentState.drawState(canvas);
    }

    /**
     * Only sets the state to newState if the current state is invalid.
     * The current state is the first thing to be updated after a touch so a state
     * can become invalidated and updated on the same press.
     */
    public void setStateIfNecessary(IStateHolder newState) {
        if (!currentState.isValid()) {
            setState(newState);
        }
    }

    /**
     * Sets the state without checking if the current state is valid or not.
     * Used for states that have multiple steps, like touching a grid tile -> drag.
     */
    public void setState(IStateHolder newState) {
        currentState.finalizeState();
        newState.setStateManager(this);
        currentState = newState;
    }

}
