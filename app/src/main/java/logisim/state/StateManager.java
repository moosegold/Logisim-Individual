package logisim.state;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import logisim.IInteractable;
import logisim.ScreenManager;
import logisim.state.modes.IMode;
import logisim.state.modes.NormalMode;
import logisim.tiles.IDraggable;
import logisim.util.DebugTextDrawer;
import logisim.util.LocalPoint;
import logisim.util.Paints;
import logisim.util.ScreenPoint;
import logisim.util.TouchAction;

public class StateManager {

    public final Canvas canvas;
    public final ScreenManager screenManager;
    public final DebugTextDrawer debugText;

//    private IStateHolder currentState = new WaitingState();
    private IMode mode = new NormalMode();

    private boolean touchInProgress;
    private boolean dragInProgress;
    private IInteractable touchedObjectStart;
    private IDraggable draggedObject;
    private ScreenPoint dragPoint;

    private String statusBarText = "";

    public StateManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
        this.canvas = this.screenManager.getCanvas();
        debugText = new DebugTextDrawer(new LocalPoint(screenManager.getDisplaySize().width - 4, 4), true);
        debugText.alignRight = true;
    }

    public void update(ScreenPoint screenPoint, TouchAction action) {

        dragPoint = screenPoint;
        if (action == TouchAction.UP) {
            touchInProgress = false;
            dragInProgress = false;
            IInteractable touchedObject = screenManager.getTouchedObject(screenPoint);
            if (touchedObject != null && touchedObjectStart == touchedObject) {
                IMode tempMode = mode;
                touchedObject.onTap();
                tempMode.processTap(touchedObjectStart);
            } else {
                mode.processDrag(touchedObject);
            }
        } else if (action == TouchAction.DOWN) {
            if (!touchInProgress) {
                touchedObjectStart = screenManager.getTouchedObject(screenPoint);
                if (touchedObjectStart != null)
                    touchedObjectStart.onTouch();
            }
            touchInProgress = true;
        } else if (action == TouchAction.MOVE) {
            mode.updateDrag(screenPoint);
            if (!dragInProgress && touchedObjectStart != null)
                draggedObject = touchedObjectStart.onDrag();
            dragInProgress = true;
        }

//        currentState.updateDrag(screenPoint, action);
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
        mode.draw();
        debugText.draw(canvas);
    }

    private void drawDraggedObject() {
        Bitmap image = this.draggedObject.getComponentImage();
        if (image != null) {
            Rect orgRect = new Rect(0, 0, image.getWidth(), image.getHeight());
            Rect transformRect = new Rect(
                    dragPoint.x - image.getWidth() / 3,
                    dragPoint.y - image.getHeight() / 3,
                    dragPoint.x + image.getWidth() / 3,
                    dragPoint.y + image.getHeight() / 3);
            canvas.drawBitmap(image, orgRect, transformRect, Paints.IMAGE_TRANSLUCENT);
        }
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
//        mode.processTap(newState);
    }

    public void resetMode() {
        touchedObjectStart = null;
        draggedObject = null;
        dragPoint = null;
        setMode(new NormalMode());
    }

    public void setMode(IMode mode) {
        this.mode.finalizeMode();
        this.mode = mode;
        screenManager.draw();
    }

    public IMode getMode() {
        return mode;
    }

    public IInteractable getTouchedObjectStart() {
        return touchedObjectStart;
    }

    public IDraggable getDraggedObject() {
        return draggedObject;
    }

    public ScreenPoint getDragPoint() {
        return dragPoint;
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
