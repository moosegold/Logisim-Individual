package logisim.state.states;

import android.graphics.Canvas;

import logisim.sidebar.ComponentSidebar;
import logisim.sidebar.SidebarButton;
import logisim.util.ScreenPoint;
import logisim.util.TouchAction;

public class SidebarButtonTouchState extends AbstractStateHolder {

    private ComponentSidebar sidebar;

    private SidebarButton button;
    private ScreenPoint touchPoint;

    public SidebarButtonTouchState(ComponentSidebar sidebar, SidebarButton button, ScreenPoint screenPoint) {
        this.sidebar = sidebar;
        this.button = button;
        this.touchPoint = screenPoint;
    }

    @Override
    public void update(ScreenPoint screenPoint, TouchAction action) {
        this.touchPoint = screenPoint;

        if (action == TouchAction.UP) {
            this.isValid = false;
            if (button == sidebar.getButtonPress(sidebar.convertToLocalPoint(screenPoint)))
                button.handleTap();
        } else if (action == TouchAction.MOVE) {
            button.handleDragStart(screenPoint);
        }
    }

    public SidebarButton getButton() {
        return button;
    }

    @Override
    public void drawState(Canvas mainCanvas) {
        stateManager.debugText.addText("Button: " + button);
    }

    @Override
    public void initState() {

    }

    @Override
    public void finalizeState() {

    }
}
