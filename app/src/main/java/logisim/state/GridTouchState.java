package logisim.state;

import android.graphics.Canvas;

import java.util.Timer;
import java.util.TimerTask;

import logisim.Grid;
import logisim.tiles.Tile;
import logisim.tiles.components.Component;
import logisim.util.GridPoint;
import logisim.util.ScreenPoint;

public class GridTouchState implements IStateHolder {

    private final static int DRAG_START_DELAY_MS = 500;

    private StateManager stateManager;
    private boolean isValid;

    private Tile touchedGridPoint;
    private Timer pressHoldTimer = new Timer();

    private Grid grid;

    public GridTouchState(Grid grid, Tile tile) {
        this.grid = grid;
        this.touchedGridPoint = tile;
        isValid = touchedGridPoint instanceof Component;
        pressHoldTimer.schedule(new DragCountdownTimerTask(this), DRAG_START_DELAY_MS);
    }

    @Override
    public void update(ScreenPoint globalPoint) {

    }

    @Override
    public void drawState(Canvas mainCanvas) {

    }

    @Override
    public void finalizeState() {

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

        private GridTouchState state;

        public DragCountdownTimerTask(GridTouchState state) {
            this.state = state;
        }

        @Override
        public void run() {

        }
    }
}
