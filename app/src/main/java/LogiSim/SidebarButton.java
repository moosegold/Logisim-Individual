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

    final DebugTextDrawer debugText;

    public SidebarButton(ScreenPoint point, int length, String action, int Rresource, AbstractScreenPartition partition) {
        this.point = point;
        this.length = length;
        this.action = action;
        this.Rresource = Rresource;

        this.partition = partition;
        this.debugText = new DebugTextDrawer(point, partition.canvas, true);
    }

    public void draw() {
        debugText.addText("Button Loc: " + point);
        debugText.addText("Button Size: " + length);
        drawImage();
        drawBounds();

        debugText.draw(partition.canvas);
    }

    void drawImage() {
        Rect orgImgRect = new Rect(point.x, point.y, getImage().getWidth(), getImage().getWidth());
        Rect transformImgRect = new Rect(point.x, point.y, point.x + length, point.y + length);
        transformImgRect.offsetTo(point.x, point.y + (length / 4));
        debugText.addText("Img size: " + new Size(transformImgRect.width(), transformImgRect.height()));
        debugText.addText("Img at: " + new ScreenPoint(transformImgRect.left, transformImgRect.top));
        partition.canvas.drawBitmap(getImage(), orgImgRect, transformImgRect, null);
    }

    void drawBounds() {
        Rect bounds = Util.getRect(point, new Size(length, length));
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.BLUE);
        borderPaint.setStyle(Paint.Style.STROKE);

        partition.canvas.drawRect(bounds, borderPaint);
    }

    private Bitmap getImage() {
        return BitmapFactory.decodeResource(partition.screenManager.appContext.getResources(), Rresource);

    }

}
