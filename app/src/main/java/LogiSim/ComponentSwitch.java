package LogiSim;

public class ComponentSwitch extends AbstractComponent {

    private boolean state;

    ComponentSwitch(AbstractTile tile) {
        super(tile);
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
    public int getRresource() {
        return state ? R.drawable.switch_on : R.drawable.switch_off;
    }

    @Override
    public boolean eval() {
        return state;
    }

}
