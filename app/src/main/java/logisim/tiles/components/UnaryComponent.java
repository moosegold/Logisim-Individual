package logisim.tiles.components;

import androidx.annotation.Nullable;

import logisim.tiles.Tile;
import logisim.util.GridPoint;

public abstract class UnaryComponent extends Component implements ILogicComponent {

    @Nullable
    private ILogicComponent input;

    public UnaryComponent(Tile tile) {
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

    protected boolean getInput() {
        return input != null && input.eval();
    }
}
