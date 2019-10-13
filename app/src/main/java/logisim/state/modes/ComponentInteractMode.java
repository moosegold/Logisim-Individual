package logisim.state.modes;

import logisim.Grid;
import logisim.state.StateManager;
import logisim.tiles.components.Component;

public class ComponentInteractMode extends AbstractMode {

    private final static int DRAG_START_DELAY_MS = 500;

    private Grid grid;
    private Component component;

    public ComponentInteractMode(StateManager stateManager, Component component, Grid grid) {
        super(stateManager);
        this.component = component;
        this.grid = grid;
    }

    @Override
    public void processTouch(Object touchedObject) {

    }

    @Override
    public void processDrag(Object dest) {

    }

    @Override
    public void finalizeMode() {

    }
}
