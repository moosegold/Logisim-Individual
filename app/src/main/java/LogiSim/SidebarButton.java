package LogiSim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class SidebarButton {

    final AbstractScreenPartition partition;

    final ScreenPoint point;
    final int length;
    final String action;
    final int Rresource;

    public SidebarButton(ScreenPoint point, int length, String action, int Rresource, AbstractScreenPartition partition) {
        this.point = point;
        this.length = length;
        this.action = action;
        this.Rresource = Rresource;

        this.partition = partition;
    }

    public void draw() {
        drawImage();
        drawBounds();
        drawDebugText();
    }

    void drawImage() {
        Rect orgImgRect = new Rect(point.x, point.y, getImage().getWidth(), getImage().getWidth());
        Rect transformImgRect = new Rect(point.x, point.y, point.x + length, point.y + length);
        transformImgRect.offsetTo(point.x, point.y + (length / 2) - transformImgRect.centerY() / 2);
        partition.canvas.drawBitmap(getImage(), orgImgRect, transformImgRect, null);
    }

    void drawBounds() {
        Rect bounds = Util.getRect(point, new Size(length, length));
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.BLUE);
        borderPaint.setStyle(Paint.Style.STROKE);

        partition.canvas.drawRect(bounds, borderPaint);
    }

    void drawDebugText() {

    }

    private Bitmap getImage() {
        return BitmapFactory.decodeResource(partition.screenManager.appContext.getResources(), Rresource);
    }

}
