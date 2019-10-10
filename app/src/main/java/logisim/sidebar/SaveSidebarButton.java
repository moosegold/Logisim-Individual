package logisim.sidebar;

import android.graphics.Paint;

import java.util.List;

import logisim.AbstractScreenPartition;
import logisim.util.Paints;
import logisim.util.ScreenPoint;
import logisim.util.Size;
import logisim.util.TextDrawUtil;

public class SaveSidebarButton extends SidebarButton {

    public SaveSidebarButton(ScreenPoint point, Size size, String label, AbstractScreenPartition partition) {
        super(point, size, label, partition);
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void drawLabel() {
        Paint paint = Paints.SAVE_BUTTON_TEXT;
        int xPos = getLocalCenter().x - TextDrawUtil.getTextWidthPx(label, paint) / 2;
        int yPos = getLocalCenter().y + TextDrawUtil.getTextHeightPx(paint) / 2;
        debugText.addText("labelPos: " + new ScreenPoint(xPos, yPos));
        canvas.drawText(label, xPos, yPos, paint);
    }

}
