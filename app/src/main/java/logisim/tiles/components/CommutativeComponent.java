package logisim.tiles.components;


import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import logisim.Grid;
import logisim.tiles.components.concrete.ANDGate;
import logisim.tiles.components.concrete.ORGate;
import logisim.util.GridPoint;
import logisim.util.LocalPoint;

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

    private final List<Component> inputs = new ArrayList<>(MAX_INPUTS);

    public CommutativeComponent(Grid grid) {
        super(grid);
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
            return inputs.get(input).eval();
        } else {
            return false;
        }
    }

//    public void validate() {
//        LinkedList<Component> toRemove = new LinkedList<>();
//        for (Component input : inputs) {
//            if (input.notOnGrid())
//                toRemove.add(input);
//        }
//        inputs.removeAll(toRemove);
//    }

    @Override
    public void setInput(int input, Component component) {
        inputs.set(input, component);
    }

    @Override
    public boolean hasInputPin() {
        return true;
    }

    @Override
    public boolean hasOutputPin() {
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
            Component component = grid.getTile(new GridPoint(scanner.nextInt(), scanner.nextInt()));
            if (component != null)
                connectInput(component);
        }
    }

    public List<Component> getInputs() {
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
            return grid.convertToLocalPoint(new GridPoint(0, 0));
        }
    }

}
