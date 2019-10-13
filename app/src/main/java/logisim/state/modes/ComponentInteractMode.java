package logisim.state.modes;

import java.util.Timer;
import java.util.TimerTask;

import logisim.Grid;
import logisim.state.StateManager;
import logisim.state.states.GridComponentTouchState;
import logisim.tiles.components.Component;
import logisim.util.ScreenPoint;

public class ComponentInteractMode extends AbstractMode {

    private final static int DRAG_START_DELAY_MS = 500;

    private Grid grid;
    private Component component;

    private Timer pressHoldTimer = new Timer();

    public ComponentInteractMode(StateManager stateManager, Component component, Grid grid) {
        super(stateManager);
        this.component = component;
        this.grid = grid;
        pressHoldTimer.schedule(new DragCountdownTimerTask(), DRAG_START_DELAY_MS);
    }

    @Override
    public void updateDrag(ScreenPoint screenPoint) {
        if (component != grid.getTile(grid.convertToGridPoint(screenPoint))) {
            stateManager.setMode(new WireMode(stateManager));
            pressHoldTimer.cancel();
        }
    }

    @Override
    public void processTap(Object touchedObject) {
        stateManager.resetMode();
    }

    @Override
    public void processDrag(Object dest) {

    }

    @Override
    public void draw() {

    }

    @Override
    public void finalizeMode() {
        pressHoldTimer.cancel();
    }

    private class DragCountdownTimerTask extends TimerTask {

        @Override
        public void run() {
            stateManager.setMode(new MoveMode(stateManager, component, grid));
        }
    }
}
