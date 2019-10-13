package logisim.tiles.components;


import java.util.ArrayList;
import java.util.List;

import logisim.tiles.Tile;
import logisim.tiles.components.concrete.ANDGate;
import logisim.tiles.components.concrete.ORGate;
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

    private List<ILogicComponent> inputs = new ArrayList<>(MAX_INPUTS);

    public CommutativeComponent(Tile tile) {
        super(tile);
    }

    @Override
    public void processConnection(ILogicComponent source) {
        if (inputs.contains(source))
            // Disconnect the wire if drawn from a component already connected.
            removeInput(source);
        else
            connectInput(source);
    }

    private void connectInput(ILogicComponent component) {
        if (inputs.size() >= MAX_INPUTS) {
            throw new RuntimeException("Gate full - Remove an existing wire to add a new one\n" +
                    " Replace this with a notification that appears  on the grid in the same spot" +
                    " as the component identifier.");
        } else {
            inputs.add(component);
        }
    }

    private void removeInput(ILogicComponent component) {
        inputs.remove(component);
    }

    /**
     * @return true if the input is on, and false if it is off.
     */
    protected boolean getInput(int input) {
        if (inputs.size() < input)
            return inputs.get(input).eval();
        else
            return false;
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
        return grid.convertToLocalPoint(gridPoint);
    }

}
