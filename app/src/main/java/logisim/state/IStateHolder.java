package logisim.state;

import android.graphics.Canvas;

import logisim.util.ScreenPoint;
import logisim.util.TouchAction;

public interface IStateHolder {

    /**
     * Receives touch events that update the state, or may create a new one.
     */
    void update(ScreenPoint screenPoint, TouchAction action);

    /**
     * Allows the state to render things relevant to it. For dragging, this renders
     * the dragged object and draws outlines around tiles.
     */
    void drawState(Canvas mainCanvas);

    /**
     * Perform cleanup tasks when switching states.
     */
    void finalizeState();

    /**
     * Can this state be replaced at this point?
     */
    boolean isValid();

    void setStateManager(StateManager stateManager);

}
