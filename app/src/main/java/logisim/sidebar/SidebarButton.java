package logisim.sidebar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import logisim.AbstractScreenPartition;
import logisim.util.DebugTextDrawer;
import logisim.util.Paints;
import logisim.util.ScreenPoint;
import logisim.util.Size;
import logisim.util.TextDrawUtil;

public abstract class SidebarButton {

    public final String label;

    protected final AbstractScreenPartition partition;
    private Bitmap image;
    protected Canvas canvas;

    public final ScreenPoint point;
    public final Size size;

    protected final DebugTextDrawer debugText;

    public SidebarButton(ScreenPoint point, Size size, String label, AbstractScreenPartition partition) {
        this.point = point;
        this.size = size;
        this.label = label;
        createCanvas();
        this.partition = partition;
        this.debugText = new DebugTextDrawer(false);
    }

    private void createCanvas() {
        image = Bitmap.createBitmap(size.width, size.height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(image);
    }

    public void draw() {
        createCanvas();
        debugText.addText("Button Loc: " + point);
        debugText.addText("Button Size: " + size);

        drawLabel();
        drawBounds();

        debugText.draw(canvas);
    }

    public abstract void drawLabel();

    private void drawBounds() {
        Rect bounds = new Rect(0, 0, size.width - 1, size.height - 1);
        canvas.drawRect(bounds, Paints.BUTTON_BORDER_COLOR);
    }

    public ScreenPoint getCenterForPartition() {
        return new ScreenPoint(point.x + size.width / 2, point.y + size.height / 2);
    }

    public ScreenPoint getLocalCenter() {
        return new ScreenPoint(size.width / 2, size.height / 2);
    }

    public Bitmap getImage() {
        return image;
    }

    public Bitmap getDragImage() {
        return null;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + label + ")";
    }

}
