package logisim.tiles.components.concrete;

import logisim.R;
import logisim.tiles.Tile;
import logisim.tiles.components.UnaryComponent;

public class NOTGate extends UnaryComponent {

    public NOTGate(Tile tile) {
        super(tile);
    }

    @Override
    public String getComponentName() {
        return "NOT Gate";
    }

    @Override
    public boolean eval() {
        return !this.getInput();
    }

    @Override
    public int getRresource() {
        return R.drawable.not_gate;
    }
}
