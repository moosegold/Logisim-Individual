package logisim.tiles.components.concrete;

import logisim.R;
import logisim.tiles.Tile;
import logisim.tiles.components.Component;
import logisim.tiles.components.ILogicComponent;
import logisim.util.LocalPoint;

public class ComponentLED extends Component {

    public ComponentLED(Tile tile) {
        super(tile);
    }

    @Override
    public String getName() {
        return "LED";
    }

    @Override
    public void processConnection(ILogicComponent source) {

    }

    @Override
    public boolean eval() {
        return false;
    }

    @Override
    public int getRresource() {
        return this.eval() ? R.drawable.led_on : R.drawable.led_off;
    }

    @Override
    public boolean hasInput() {
        return true;
    }

    @Override
    public boolean hasOutput() {
        return false;
    }

    /**
     * Returns the point local to grid partition of the input that the wire should be
     * routed to.
     */
    public LocalPoint getInputPosFor(Component component) {
        return grid.convertToLocalPoint(gridPoint);
    }
}
