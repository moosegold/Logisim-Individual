package logisim.sidebar;

import android.graphics.Paint;

import logisim.AbstractScreenPartition;
import logisim.util.LocalPoint;
import logisim.util.Paints;
import logisim.util.ScreenPoint;
import logisim.util.Size;
import logisim.util.TextDrawUtil;

public abstract class LabelSidebarButton extends SidebarButton {

    public LabelSidebarButton(LocalPoint point, Size size, String label, ComponentSidebar sidebar) {
        super(point, size, label, sidebar);
    }

    @Override
    public void draw() {
        super.draw();
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

    interface ITapProcedure {
        public void onTap();
    }

}
