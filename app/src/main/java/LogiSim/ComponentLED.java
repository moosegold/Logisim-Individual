package LogiSim;

public class ComponentLED extends AbstractComponent {

    ComponentLED(AbstractTile tile) {
        super(tile);
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
