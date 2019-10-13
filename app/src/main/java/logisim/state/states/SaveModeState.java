package logisim.state.states;

import android.graphics.Canvas;

import logisim.sidebar.ComponentSidebar;
import logisim.util.ScreenPoint;
import logisim.util.TouchAction;

public class SaveModeState extends AbstractStateHolder {

    ComponentSidebar sidebar;

    public SaveModeState(ComponentSidebar sidebar) {
        this.sidebar = sidebar;
    }

    @Override
    public void update(ScreenPoint screenPoint, TouchAction action) {

    }

    @Override
    public void drawState(Canvas mainCanvas) {

    }

    @Override
    public void initState() {
        sidebar.saveMode = true;
    }

    @Override
    public void finalizeState() {

    }

}
