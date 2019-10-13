package logisim.sidebar;

import android.graphics.Paint;

import logisim.state.modes.SaveMode;
import logisim.state.states.ShowMessageState;
import logisim.util.LocalPoint;
import logisim.util.Paints;
import logisim.util.ScreenPoint;
import logisim.util.Size;
import logisim.util.TextDrawUtil;

public class SaveButton extends SidebarButton {

    public SaveButton(LocalPoint point, Size size, String label, ComponentSidebar sidebar) {
        super(point, size, label, sidebar);
    }

    @Override
    public void handleDragStart(ScreenPoint screenPoint) {
        // Do nothing
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void drawBackground() {
        super.drawBackground();
        if (sidebar.stateManager.getMode() instanceof SaveMode)
            canvas.drawRect(0, 0, size.width, size.height, Paints.SAVE_MODE_SAVE_BUTTON_BACKGROUND_COLOR);
    }

    @Override
    public void drawLabel() {
        boolean saveMode = sidebar.stateManager.getMode() instanceof SaveMode;
        Paint paint = saveMode ? Paints.SAVE_MODE_SAVE_BUTTON_TEXT : Paints.SAVE_BUTTON_TEXT;
        int xPos = getLocalCenter().x - TextDrawUtil.getTextWidthPx(label, paint) / 2;
        int yPos = getLocalCenter().y + TextDrawUtil.getTextHeightPx(label, paint) / 2;
        debugText.addText("labelPos: " + new ScreenPoint(xPos, yPos));
        canvas.drawText(label, xPos, yPos, paint);
    }

    @Override
    public void handleTap() {
        if (!(sidebar.stateManager.getMode() instanceof SaveMode)) {
//            sidebar.stateManager.setState(new ShowMessageState("Select A Save Slot"));
            sidebar.stateManager.setMode(new SaveMode(sidebar.stateManager));
        } else {
            sidebar.stateManager.resetMode();
        }
    }
}
