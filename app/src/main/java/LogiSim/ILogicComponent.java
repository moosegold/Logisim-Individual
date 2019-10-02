package LogiSim;

public interface ILogicComponent {

    /**
     * Evaluates the state of the component by recursively evaluating all components that
     * are connected to it, as shown in Daryl Posnett's example classes.
     * @return whether the gate output is on or off.
     */
    boolean eval();

}
