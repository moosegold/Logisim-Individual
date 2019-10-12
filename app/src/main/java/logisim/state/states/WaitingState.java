package logisim.state.states;

import android.graphics.Canvas;

import logisim.ScreenManager;
import logisim.state.IStateHolder;
import logisim.state.StateManager;
import logisim.util.ScreenPoint;
import logisim.util.TouchAction;

public class WaitingState implements IStateHolder {

    @Override
    public void update(ScreenPoint screenPoint, TouchAction action) {

    }

    @Override
    public void drawState(Canvas mainCanvas) {

    }

    @Override
    public void initState() {

    }

    @Override
    public void finalizeState() {

    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void setManagers(StateManager stateManager, ScreenManager screenManager) {
        
    }
}
