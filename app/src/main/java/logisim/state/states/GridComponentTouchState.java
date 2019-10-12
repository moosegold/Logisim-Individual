package logisim.state.states;

import android.graphics.Canvas;

import java.util.Timer;
import java.util.TimerTask;

import logisim.Grid;
import logisim.tiles.Tile;
import logisim.util.GridPoint;
import logisim.util.LocalPoint;
import logisim.util.ScreenPoint;
import logisim.util.TouchAction;

/**
 * Starts a sequence of states for when the user interacts with a component on the grid.
 */
public class GridComponentTouchState extends AbstractStateHolder {

    private final static int DRAG_START_DELAY_MS = 500;

    private Tile startGridPoint;
    private ScreenPoint touchPoint;
    private Timer pressHoldTimer = new Timer();

    private Grid grid;

    public GridComponentTouchState(Grid grid, Tile tile, ScreenPoint screenPoint) {
        this.grid = grid;
        this.startGridPoint = tile;
        touchPoint = screenPoint;
        pressHoldTimer.schedule(new DragCountdownTimerTask(this), DRAG_START_DELAY_MS);
    }

    @Override
    public void update(ScreenPoint screenPoint, TouchAction action) {
        LocalPoint localPoint = grid.convertToLocalPoint(screenPoint);
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
    }

    private void setToDragMode() {
        stateManager.setState(new TileMoveDragState(startGridPoint, grid, touchPoint));
    }

    @Override
    public void drawState(Canvas mainCanvas) {

    }

    @Override
    public void initState() {

    }

    @Override
    public void finalizeState() {
        pressHoldTimer.cancel();
    }

    @Override
    public boolean isValid() {
        return isValid;
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
