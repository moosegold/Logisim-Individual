package LogiSim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class SidebarButton {

    final String action;

    private final AbstractScreenPartition partition;
    Bitmap image;
    private Canvas canvas;

    final ScreenPoint point;
    final int length;
    private final int Rresource;

    private final DebugTextDrawer debugText;

    public SidebarButton(ScreenPoint point, int length, String action, int Rresource, AbstractScreenPartition partition) {
        this.point = point;
        this.length = length;
        this.action = action;
        this.Rresource = Rresource;
        createCanvas();
        this.partition = partition;
        this.debugText = new DebugTextDrawer(point, partition.canvas, true);
    }

    private void createCanvas() {
        image = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(image);
    }

    public void draw() {
        createCanvas();
        debugText.addText("Button Loc: " + point);
        debugText.addText("Button Size: " + length);
        drawComponentImage();
        drawBounds();

        debugText.draw(canvas);
    }

    private void drawComponentImage() {
        Rect orgImgRect = new Rect(0, 0, getImage().getWidth(), getImage().getWidth());
        Rect transformImgRect = new Rect(0, 0, length, length);
        transformImgRect.offsetTo(0, length / 4);
        debugText.addText("Img size: " + new Size(transformImgRect.width(), transformImgRect.height()));
        debugText.addText("Img at: " + new ScreenPoint(transformImgRect.left, transformImgRect.top));
        canvas.drawBitmap(getImage(), orgImgRect, transformImgRect, null);
    }

    private void drawBounds() {
        Rect bounds = new Rect(0, 0, length - 1, length - 1);
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.BLUE);
        borderPaint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(bounds, borderPaint);
    }

    private Bitmap getImage() {
        return BitmapFactory.decodeResource(partition.screenManager.appContext.getResources(), Rresource);

    }

}
