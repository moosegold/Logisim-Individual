package logisim.sidebar;

import android.graphics.Paint;

import logisim.state.modes.SaveMode;
import logisim.state.states.ShowMessageState;
import logisim.util.LocalPoint;
import logisim.util.Paints;
import logisim.util.ScreenPoint;
import logisim.util.Size;
import logisim.util.TextDrawUtil;

public class SaveSlotButton extends SidebarButton {

    public SaveSlotButton(LocalPoint point, Size size, String label, ComponentSidebar sidebar) {
        super(point, size, label, sidebar);
    }

    @Override
    public void handleDragStart(ScreenPoint screenPoint) {
        // Do nothing
    }

    @Override
    public void drawLabel() {
        Paint paint = Paints.SAVE_BUTTON_TEXT;
        int xPos = getLocalCenter().x - TextDrawUtil.getTextWidthPx(label, paint) / 2;
        int yPos = getLocalCenter().y + TextDrawUtil.getTextHeightPx(label, paint) / 2;
        debugText.addText("labelPos: " + new ScreenPoint(xPos, yPos));
        canvas.drawText(label, xPos, yPos, paint);
    }

    @Override
    public void handleTap() {
        String feedback = "";
        if (sidebar.stateManager.getMode() instanceof SaveMode) {
            boolean result = sidebar.grid.saveGrid(label);
            feedback = result ? "Layout saved to " + label : "Failed to save layout";
        } else {
            boolean result = sidebar.grid.loadGrid(label);
            feedback = result ? "Loaded layout " + label : "Failed to load layout " + label;
        }
        sidebar.stateManager.setState(new ShowMessageState(feedback));
        sidebar.stateManager.resetMode();
    }
}
