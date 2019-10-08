package LogiSim;

import android.graphics.Color;
import android.graphics.Paint;

public class SaveSidebarButton extends SidebarButton {

    String label;

    public SaveSidebarButton(ScreenPoint point, Size size, String label, String action, AbstractScreenPartition partition) {
        super(point, size, action, partition);
        this.label = label;
    }

    @Override
    public void draw() {
        super.draw();
        drawText();
    }

    private void drawText() {
        Paint paint = createPaint();
        int xPos = getLocalCenter().x - TextDrawUtil.getTextWidthPx(label, paint) / 2;
        int yPos = getLocalCenter().y + TextDrawUtil.getTextHeightPx(paint) / 2;
        debugText.addText("labelPos: " + new ScreenPoint(xPos, yPos));
        canvas.drawText(label, xPos, yPos, paint);
    }

    private Paint createPaint() {
        Paint newPaint = new Paint();
        newPaint.setTextSize(14f);
        newPaint.setFakeBoldText(true);
        newPaint.setColor(Color.BLUE);
        return newPaint;
    }
}
