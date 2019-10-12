package logisim.state;

import android.graphics.Canvas;

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
    public void finalizeState() {

    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void setStateManager(StateManager stateManager) {
        
    }
}
