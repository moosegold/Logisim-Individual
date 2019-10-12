package logisim.state;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.Timer;
import java.util.TimerTask;

import logisim.Grid;
import logisim.tiles.Tile;
import logisim.tiles.components.Component;
import logisim.util.GridPoint;
import logisim.util.LocalPoint;
import logisim.util.ScreenPoint;
import logisim.util.TouchAction;

/**
 * Starts a sequence of states for when the user interacts with a component on the grid.
 */
public class GridComponentTouchState implements IStateHolder {

    private final static int DRAG_START_DELAY_MS = 500;

    private StateManager stateManager;
    private boolean isValid;

    private Tile startGridPoint;
    private Timer pressHoldTimer = new Timer();

    private Grid grid;

    public GridComponentTouchState(Grid grid, Tile tile) {
        this.grid = grid;
        this.startGridPoint = tile;
        isValid = startGridPoint instanceof Component;
        pressHoldTimer.schedule(new DragCountdownTimerTask(this), DRAG_START_DELAY_MS);
    }

    @Override
    public void update(ScreenPoint screenPoint, TouchAction action) {
        LocalPoint localPoint = grid.localizePoint(screenPoint);
        GridPoint touchedGridPoint = grid.convertToGridPoint(localPoint);

        // The user is no longer touching the screen, invalidating this state.
        if (action == TouchAction.UP) {
            isValid = false;
            return;
        }

        // The user moved their finger off the
        if (!touchedGridPoint.equals(this.startGridPoint.getPoint())) {
            setToWireMode();
        }
    }

    private void setToWireMode() {
        // setState to a WireDrawState
        this.isValid = false;
        System.out.println("Changed to wire mode!");
    }

    private void setToDragMode() {
        // setState to GridMoveState
        this.isValid = false;
        System.out.println("Changed to drag mode!");
    }

    @Override
    public void drawState(Canvas mainCanvas) {

    }

    @Override
    public void finalizeState() {
        pressHoldTimer.cancel();
    }

    @Override
    public boolean isValid() {
        return isValid;
    }

    @Override
    public void setStateManager(StateManager stateManager) {
        this.stateManager = stateManager;
    }



    private class DragCountdownTimerTask extends TimerTask {

        private GridComponentTouchState state;

        public DragCountdownTimerTask(GridComponentTouchState state) {
            this.state = state;
        }

        @Override
        public void run() {
            state.setToDragMode();
        }
    }
}
