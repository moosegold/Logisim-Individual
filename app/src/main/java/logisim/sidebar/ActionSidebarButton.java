package logisim.sidebar;

import android.graphics.Paint;

import java.util.List;

import logisim.AbstractScreenPartition;
import logisim.util.Paints;
import logisim.util.ScreenPoint;
import logisim.util.Size;
import logisim.util.TextDrawUtil;

public class ActionSidebarButton extends SidebarButton {

    private final ITapProcedure action;

    public ActionSidebarButton(ScreenPoint point, Size size, String label, AbstractScreenPartition partition, ITapProcedure action) {
        super(point, size, label, partition);
        this.action = action;
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void handleDragStart() {
        // Do Nothing
    }

    @Override
    public void handleTap() {
        action.onTap();
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
