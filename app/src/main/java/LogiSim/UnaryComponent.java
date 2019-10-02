package LogiSim;

import androidx.annotation.Nullable;

abstract class UnaryComponent extends AbstractComponent implements ILogicComponent {

    @Nullable
    private ILogicComponent input;

    UnaryComponent(AbstractTile tile) {
        super(tile);
    }

    @Override
    public void processConnection(ILogicComponent source) {
        if (input == source)
            detachWire(source);
        else
            attachWire(source);
    }

    private void attachWire(ILogicComponent source) {
        input = source;
    }

    private void detachWire(ILogicComponent source) {
        input = null;
    }

    boolean getInput() {
        return input != null && input.eval();
    }
}
