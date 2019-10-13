package logisim.tiles.components;


import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import logisim.tiles.Tile;
import logisim.tiles.components.concrete.ANDGate;
import logisim.tiles.components.concrete.ORGate;
import logisim.util.GridPoint;
import logisim.util.LocalPoint;
import logisim.util.PaintBuilder;

/**
 * Refers to a component, where the order of inputs is unimportant.
 * Named as this instead of binary component, because future implementations may allow
 * for components with an arbitrary amount of inputs.
 *
 * @see ANDGate
 * @see ORGate
 */
public abstract class CommutativeComponent extends Component {

    private static final int MAX_INPUTS = 2;

    private List<Component> inputs = new ArrayList<>(MAX_INPUTS);

    public CommutativeComponent(Tile tile) {
        super(tile);
    }

    @Override
    public void processConnection(Component source) {
        if (inputs.contains(source))
            // Disconnect the wire if drawn from a component already connected.
            removeInput(source);
        else
            connectInput(source);
    }

    private void connectInput(Component component) {
        if (inputs.size() >= MAX_INPUTS) {
            System.out.println("Not adding connection; Gate full");
        } else {
            inputs.add(component);
        }
    }

    private void removeInput(Component component) {
        inputs.remove(component);
    }

    @Override
    public void drawWires(Canvas canvas) {
        for (int i = 0; i < inputs.size(); i++) {
            debugText.addText("i" + i + ": " + inputs.get(i));
            drawWire(canvas, inputs.get(i), this);
        }
    }

    /**
     * @return true if the input is on, and false if it is off.
     */
    protected boolean getInput(int input) {
        if (input < inputs.size()) {
            Component source = inputs.get(input);
            if (!source.onGrid()) {
                removeInput(source);
                return false;
            } else {
                return inputs.get(input).eval();
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean hasInput() {
        return true;
    }

    @Override
    public boolean hasOutput() {
        return true;
    }

    @Override
    public boolean canAcceptWire() {
        return inputs.size() < MAX_INPUTS;
    }

    @Override
    public String getAdditionalStorageData() {
        String ret = " ";
        for (Component input : inputs) {
            ret += input.getPoint().x + " " + input.getPoint().y + " ";
        }
        return ret;
    }

    @Override
    public void loadAdditionalStorageData(Scanner scanner) {
        while (scanner.hasNextInt()) {
            Tile tile = grid.getTile(new GridPoint(scanner.nextInt(), scanner.nextInt()));
            if (tile instanceof Component)
                connectInput((Component) tile);
        }
    }

    public List<Tile> getInputs() {
        return new LinkedList<>(inputs);
    }


    /**
     * Returns the point local to grid partition of the input that the wire should be
     * routed to.
     */
    public LocalPoint getInputPosFor(Component component) {
        int input = inputs.indexOf(component);
        if (input != -1) {
            if (input == 0) {
                return convertToGridSpace(new LocalPoint((int) ((35.0 / 150) * grid.tileLength), getRect().centerY() - (int) ((15.0 / 150) * grid.tileLength)));
            } else {
                return convertToGridSpace(new LocalPoint((int) ((35.0 / 150) * grid.tileLength), getRect().centerY() + (int) ((15.0 / 150) * grid.tileLength)));
            }
        } else {
            return grid.convertToLocalPoint(gridPoint);
        }
    }

}
