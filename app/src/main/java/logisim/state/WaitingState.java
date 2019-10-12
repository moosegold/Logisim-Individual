package logisim.state;

import android.graphics.Canvas;

import logisim.util.ScreenPoint;

public class WaitingState implements IStateHolder {

    @Override
    public void update(ScreenPoint screenPoint) {

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
}
