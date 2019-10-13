package logisim.tiles.components;

import android.graphics.Canvas;

import androidx.annotation.Nullable;

import logisim.tiles.Tile;
import logisim.util.LocalPoint;

public abstract class UnaryComponent extends Component {

    @Nullable
    private Component input;

    public UnaryComponent(Tile tile) {
        super(tile);
    }

    @Override
    public void processConnection(Component source) {
        if (input == source)
            detachWire();
        else
            attachWire(source);
    }

    private void attachWire(Component source) {
        input = source;
    }

    private void detachWire() {
        input = null;
    }

    @Override
    public void drawWires(Canvas canvas) {
        debugText.addText("i: " + input);
        drawWire(canvas, input, this);
    }

    protected boolean getInput() {
        if (input != null && !input.onGrid())
            detachWire();
        return input != null && input.eval();
    }

    @Override
    public boolean hasInput() {
        return true;
    }

    @Override
    public boolean hasOutput() {
        return true;
    }

    /**
     * Returns the point local to grid partition of the input that the wire should be
     * routed to.
     */
    public LocalPoint getInputPosFor(Component component) {
        return convertToGridSpace(new LocalPoint((int) ((35.0 / 150) * grid.tileLength), getRect().centerY()));
    }
}
