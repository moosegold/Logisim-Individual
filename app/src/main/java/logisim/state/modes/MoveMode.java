package logisim.state.modes;

import logisim.Grid;
import logisim.state.StateManager;
import logisim.tiles.Tile;
import logisim.tiles.components.Component;
import logisim.util.ScreenPoint;

public class MoveMode extends AbstractMode {

    private Grid grid;
    private Component start;

    public MoveMode(StateManager stateManager, Component start, Grid grid) {
        super(stateManager);
        this.start = start;
        this.grid = grid;
    }

    @Override
    public void updateDrag(ScreenPoint screenPoint) {

    }

    @Override
    public void processTouch(Object touchedObject) {

    }

    @Override
    public void processDrag(Object dest) {
        if (dest instanceof Tile) {
            Tile tile = (Tile) dest;
            if (tile.isReplaceable())
                grid.moveTile(start, tile);
        }
        stateManager.resetMode();
    }

    @Override
    public void finalizeMode() {

    }
}
