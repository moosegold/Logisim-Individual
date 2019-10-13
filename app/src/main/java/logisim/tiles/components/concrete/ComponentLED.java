package logisim.tiles.components.concrete;

import android.graphics.Canvas;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import logisim.R;
import logisim.tiles.Tile;
import logisim.tiles.components.Component;
import logisim.util.GridPoint;
import logisim.util.LocalPoint;
import logisim.util.PaintBuilder;

public class ComponentLED extends Component {

    private Component input;

    public ComponentLED(Tile tile) {
        super(tile);
    }

    @Override
    public String getName() {
        return "LED";
    }

    @Override
    public void processConnection(Component source) {
        if (this.input == null || this.input != source)
            attachWire(source);
        else
            detachWire();
    }

    private void attachWire(Component source) {
        input = source;
    }

    private void detachWire() {
        input = null;
    }

    @Override
    public boolean eval() {
        return getInput();
    }

    private boolean getInput() {
        if (input != null && !input.onGrid())
            detachWire();
        return input != null && input.eval();
    }

    @Override
    public void drawWires(Canvas canvas) {
        debugText.addText("i: " + input);
        drawWire(canvas, input, this);
    }

    @Override
    public String getStorageID() {
        return "led";
    }

    @Override
    public String getAdditionalStorageData() {
        if (input != null) {
            return " " + input.getPoint().x + " " + input.getPoint().y;
        }
        return " ";
    }

    @Override
    public void loadAdditionalStorageData(Scanner scanner) {
        if (scanner.hasNextInt()) {
            Tile tile = grid.getTile(new GridPoint(scanner.nextInt(), scanner.nextInt()));
            if (tile instanceof Component)
                attachWire((Component) tile);
        }

    }

    public List<Tile> getInputs() {
        if (input != null)
            return Collections.singletonList(input);
        else
            return Collections.emptyList();
    }

    @Override
    public int getRresource() {
        return this.eval() ? R.drawable.led_on : R.drawable.led_off;
    }

    @Override
    public boolean hasInput() {
        return true;
    }

    @Override
    public boolean hasOutput() {
        return false;
    }

    /**
     * Returns the point local to grid partition of the input that the wire should be
     * routed to.
     */
    public LocalPoint getInputPosFor(Component component) {
        return convertToGridSpace(new LocalPoint((int) ((35.0 / 150) * grid.tileLength), getRect().centerY()));
    }
}
