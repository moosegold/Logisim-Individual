package logisim.sidebar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import logisim.AbstractScreenPartition;
import logisim.util.DebugTextDrawer;
import logisim.util.LocalPoint;
import logisim.util.Paints;
import logisim.util.ScreenPoint;
import logisim.util.Size;

public abstract class SidebarButton {

    public final String label;

    protected final AbstractScreenPartition partition;
    private Bitmap image;
    protected Canvas canvas;

    public final LocalPoint point;
    public final Size size;

    protected final DebugTextDrawer debugText;

    public SidebarButton(LocalPoint point, Size size, String label, AbstractScreenPartition partition) {
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

    /**
     * Called when a drag starts on a button.
     *
     */
    public abstract void handleDragStart(ScreenPoint screenPoint);

    public abstract void handleTap();

    public abstract void drawLabel();

    private void drawBounds() {
        Rect bounds = new Rect(0, 0, size.width - 1, size.height - 1);
        canvas.drawRect(bounds, Paints.BUTTON_BORDER_COLOR);
    }

    public LocalPoint getCenterForPartition() {
        return new LocalPoint(point.x + size.width / 2, point.y + size.height / 2);
    }

    public LocalPoint getLocalCenter() {
        return new LocalPoint(size.width / 2, size.height / 2);
    }

    public Bitmap getImage() {
        return image;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + label + ")";
    }

}
