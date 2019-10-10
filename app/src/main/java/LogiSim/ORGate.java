package LogiSim;


public class ORGate extends CommutativeComponent {

    ORGate(AbstractTile tile) {
        super(tile);
    }

    public boolean eval() {
        return this.getInput(0) || this.getInput(1);
    }

    @Override
    public int getRresource() {
        return R.drawable.or_gate;
    }

}
