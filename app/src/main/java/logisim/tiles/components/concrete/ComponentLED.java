package logisim.tiles.components.concrete;

import android.graphics.Canvas;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import logisim.Grid;
import logisim.R;
import logisim.tiles.components.Component;
import logisim.util.GridPoint;
import logisim.util.LocalPoint;

public class ComponentLED extends Component {

    private Component input;

    public ComponentLED(Grid grid) {
        super(grid);
    }

    @Override
    public String getName() {
        return "LED";
    }

    @Override
    public void processConnection(Component source) {
        if (this.input != source)
            setInput(source);
        else
            setInput(null);
    }

    @Override
    public boolean eval() {
        return getInput();
    }

    private boolean getInput() {
        return input != null && input.eval();
    }

    @Override
    public void drawWires(Canvas canvas) {
        debugText.addText("i: " + input);
        drawWire(canvas, input, this);
    }

//    public void validate() {
//        if (input != null && input.notOnGrid())
//            setInput(null);
//    }

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
            Component component = grid.getTile(new GridPoint(scanner.nextInt(), scanner.nextInt()));
            if (component != null)
                setInput(component);
        }
    }

    public List<Component> getInputs() {
        if (input != null)
            return Collections.singletonList(input);
        else
            return Collections.emptyList();
    }

    @Override
    public void setInput(int input, Component component) {
        if (input == 0)
            this.input = component;
    }

    public void setInput(Component component) {
        setInput(0, component);
    }

    @Override
    public int getRresource() {
        return this.eval() ? R.drawable.led_on : R.drawable.led_off;
    }

    @Override
    public boolean hasInputPin() {
        return true;
    }

    @Override
    public boolean hasOutputPin() {
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
