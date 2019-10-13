package logisim.tiles.components.concrete;


import logisim.R;
import logisim.tiles.Tile;
import logisim.tiles.components.CommutativeComponent;
import logisim.tiles.components.Component;

public class ORGate extends CommutativeComponent {

    public ORGate(Tile tile) {
        super(tile);
    }

    @Override
    public String getName() {
        return "OR Gate";
    }

    public boolean eval() {
        return this.getInput(0) || this.getInput(1);
    }

    @Override
    public String getStorageID() {
        return "or";
    }

    @Override
    public int getRresource() {
        return R.drawable.or_gate;
    }
}
