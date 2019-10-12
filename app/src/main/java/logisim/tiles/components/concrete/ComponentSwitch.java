package logisim.tiles.components.concrete;

import logisim.R;
import logisim.tiles.Tile;
import logisim.tiles.components.Component;
import logisim.tiles.components.ILogicComponent;

public class ComponentSwitch extends Component {

    private boolean state;

    public ComponentSwitch(Tile tile) {
        super(tile);
    }

    @Override
    public String getName() {
        return "Switch";
    }

    @Override
    public void processConnection(ILogicComponent source) {

    }

    @Override
    public void handleTouch() {
        super.handleTouch();
        this.state = !this.state;
    }

    @Override
    public boolean eval() {
        return state;
    }

    @Override
    public int getRresource() {
        return state ? R.drawable.switch_on : R.drawable.switch_off;
    }

}
