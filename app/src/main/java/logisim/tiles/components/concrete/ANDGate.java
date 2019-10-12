package logisim.tiles.components.concrete;


import logisim.R;
import logisim.tiles.Tile;
import logisim.tiles.components.CommutativeComponent;

public class ANDGate extends CommutativeComponent {

    public ANDGate(Tile tile) {
        super(tile);
    }

    @Override
    public String getName() {
        return "AND Gate";
    }

    public boolean eval() {
        return this.getInput(0) && this.getInput(1);
    }

    @Override
    public int getRresource() {
        return R.drawable.and_gate;
    }

}
