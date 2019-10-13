package logisim.state.modes;


import logisim.state.IStateHolder;

/**
 * A long-term mode for the app to be in, requiring multiple steps to complete.
 */
public interface IMode {

    /**
     * Update mode with the current state.
     */
    void processTouch(Object touchedObject);

    void processDrag(Object dest);

    /**
     * Clean up before switching mode.
     */
    void finalizeMode();

}
