package LogiSim;

class NOTGate extends UnaryComponent {

    NOTGate(AbstractTile tile) {
        super(tile);
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
