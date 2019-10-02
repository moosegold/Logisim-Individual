package LogiSim;


import java.util.ArrayList;
import java.util.List;

/**
 * Refers to a component, where the order of inputs is unimportant.
 * Named as this instead of binary component, because future implementations may allow
 * for components with an arbitrary amount of inputs.
 *
 * @see ANDGate
 * @see ORGate
 */
abstract class CommutativeComponent extends AbstractComponent {

    private static final int MAX_INPUTS = 2;

    List<ILogicComponent> inputs = new ArrayList<>(MAX_INPUTS);

    CommutativeComponent(AbstractTile tile) {
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
    boolean getInput(int input) {
        if (inputs.size() < input)
            return inputs.get(input).eval();
        else
            return false;
    }

}
