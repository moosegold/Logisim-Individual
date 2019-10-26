package logisim.tiles.components.concrete;


import logisim.Grid;
import logisim.R;
import logisim.tiles.components.CommutativeComponent;

public class ANDGate extends CommutativeComponent {

    public ANDGate(Grid grid) {
        super(grid);
    }

    @Override
    public String getName() {
        return "AND Gate";
    }

    public boolean eval() {
        return this.getInput(0) && this.getInput(1);
    }

    @Override
    public String getStorageID() {
        return "and";
    }

    @Override
    public int getRresource() {
        return R.drawable.and_gate;
    }

}
