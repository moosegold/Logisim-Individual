package logisim.state.modes;

import logisim.Grid;
import logisim.sidebar.ComponentSidebarButton;
import logisim.sidebar.SidebarButton;
import logisim.state.StateManager;
import logisim.tiles.Tile;
import logisim.tiles.components.Component;

public class AddMode extends AbstractMode {

    private ComponentSidebarButton button;
    private Grid grid;

    public AddMode(StateManager stateManager, ComponentSidebarButton button, Grid grid) {
        super(stateManager);
        this.button = button;
        this.grid = grid;
    }

    @Override
    public void processTouch(Object touchedObject) {

    }

    @Override
    public void processDrag(Object dest) {
        if (dest instanceof Tile) {
            Tile tile = (Tile) dest;
            if (tile.isReplaceable())
                button.createNewComponent(tile.getPoint(), grid);
        }
        stateManager.resetMode();
    }

    @Override
    public void finalizeMode() {

    }
}
