package LogiSim;


public class ANDGate extends CommutativeComponent {

    ANDGate(AbstractTile tile) {
        super(tile);
    }

    public boolean eval() {
        return this.getInput(0) && this.getInput(1);
    }

    @Override
    public int getRresource() {
        return R.drawable.and_gate;
    }

}
