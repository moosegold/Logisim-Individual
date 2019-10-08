package LogiSim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SidebarButton {

    final String action;

    final AbstractScreenPartition partition;
    private Bitmap image;
    Canvas canvas;

    final ScreenPoint point;
    final Size size;

    final DebugTextDrawer debugText;

    public SidebarButton(ScreenPoint point, Size size, String action, AbstractScreenPartition partition) {
        this.point = point;
        this.size = size;
        this.action = action;
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

        drawBounds();

        debugText.draw(canvas);
    }

    private void drawBounds() {
        Rect bounds = new Rect(0, 0, size.width - 1, size.height - 1);
        canvas.drawRect(bounds, Paints.BUTTON_BORDER_COLOR);
    }

    ScreenPoint getCenterForPartition() {
        return new ScreenPoint(point.x + size.width / 2, point.y + size.height / 2);
    }

    ScreenPoint getLocalCenter() {
        return new ScreenPoint(size.width / 2, size.height / 2);
    }

    public Bitmap getImage() {
        return image;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + getToStringData();
    }

    List<String> getToStringData() {
        return new LinkedList<>(Collections.singletonList(action));
    }
}
