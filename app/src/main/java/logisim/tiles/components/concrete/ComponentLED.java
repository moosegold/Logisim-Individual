package logisim.tiles.components.concrete;

import logisim.R;
import logisim.tiles.Tile;
import logisim.tiles.components.Component;
import logisim.tiles.components.ILogicComponent;

public class ComponentLED extends Component {

    public ComponentLED(Tile tile) {
        super(tile);
    }

    @Override
    public String getComponentName() {
        return "LED";
    }

    @Override
    public void processConnection(ILogicComponent source) {

    }

    @Override
    public int getRresource() {
        return this.eval() ? R.drawable.led_on : R.drawable.led_off;
    }

    @Override
    public boolean eval() {
        return false;
    }
}
