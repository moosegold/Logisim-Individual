package logisim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.LinkedList;

import logisim.sidebar.ComponentSidebarButton;
import logisim.util.DebugTextDrawer;
import logisim.util.ScreenPoint;
import logisim.util.Size;
import logisim.util.Util;

public class ScreenManager {

    private LinkedList<IScreenPartition> partitions;
    private Display display;
    private Canvas mainCanvas;
    private Bitmap mainImage;
    private ImageView imageView;
    protected DebugTextDrawer debugText;

    public final Context appContext;

    private String lastPartition = null;
    private ScreenPoint lastLocalPoint = null;

    private Bitmap draggedObject;
    private ScreenPoint dragPoint;
    public ComponentSidebarButton dragSourceButton;

    private String statusBarText = "";

    public ScreenManager(Display display, ImageView imageView, Context appContext) {
        this.partitions = new LinkedList<>();
        this.display = display;
        this.appContext = appContext;
        this.imageView = imageView;
        debugText = new DebugTextDrawer(new ScreenPoint(1, getDisplaySize().height - 4), true);
        debugText.drawDownwards = false;
        createCanvas();
    }

    public Size getDisplaySize() {
        Point sizePoint = new Point();
        display.getSize(sizePoint);
        return new Size(sizePoint.x, sizePoint.y);
    }

    public void addPartition(IScreenPartition partition) {
        partitions.add(partition);
    }

    public void handleTouch(ScreenPoint screenPoint, int action) {
        this.dragPoint = screenPoint;
        IScreenPartition touchedPartiton = getTouchedPartition(screenPoint);
        if (touchedPartiton != null) {
            ScreenPoint localPoint = getLocalPoint(touchedPartiton, screenPoint);
            if (action == MotionEvent.ACTION_UP) {
                // Fire the release touch event on the partition released on before
                // firing on other partitions.
                touchedPartiton.processTouchUp(localPoint);
            } else if (action == MotionEvent.ACTION_DOWN) {
                touchedPartiton.processTouchDown(localPoint);
            } else if (action == MotionEvent.ACTION_MOVE) {
                touchedPartiton.processTouchDrag(localPoint);
            }
        }

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            // Fire the release touch event on all partitions so they can reset their state.
            for (IScreenPartition part : partitions)
                if (part != touchedPartiton)
                    part.processTouchUp(getLocalPoint(part, screenPoint));
            this.draggedObject = null;
            setStatusBarText("");
        }

        this.draw();
    }

    private void drawDraggedObject() {
        Bitmap image = this.draggedObject;
        if (image != null) {
            Rect orgRect = new Rect(0, 0, image.getWidth(), image.getWidth());
            Rect transformRect = new Rect(
                    dragPoint.x - image.getWidth() / 4,
                    dragPoint.y - image.getWidth() / 4,
                    dragPoint.x + image.getWidth() / 4,
                    dragPoint.y + image.getWidth() / 4);
            mainCanvas.drawBitmap(this.draggedObject, orgRect, transformRect, null);
        }
    }

    public void setDraggedObject(Bitmap image) {
        this.draggedObject = image;
    }

    public void setStatusBarText(String text) {
        if (text == null)
            statusBarText = "";
        else
            statusBarText = text;
    }

    public String getStatusBarText() {
        return statusBarText;
    }

    private IScreenPartition getTouchedPartition(ScreenPoint screenPoint) {
        for (IScreenPartition partition : partitions) {
            if (touchIsInside(partition, screenPoint)) {
                return partition;
            }
        }
        return null;
    }

    private ScreenPoint getLocalPoint(IScreenPartition partition, ScreenPoint screenPoint) {
        ScreenPoint newPoint = screenPoint.copy();
        newPoint.offset(-partition.getOrigin().x, -partition.getOrigin().y);
        return newPoint;
    }

    public void draw() {
        debugText.addText("Display Size: " + getDisplaySize());
        for (IScreenPartition partition : partitions) {
            partition.draw();
            ScreenPoint origin = partition.getOrigin();
            debugText.addText("");
            debugText.addText(partition.getName() + " origin: " + partition.getOrigin());
            debugText.addText(partition.getName() + " size: " + partition.getSize());
            mainCanvas.drawBitmap(partition.getPartitionBitmap(), origin.x, origin.y, new Paint());
        }
        drawDraggedObject();
        debugText.draw(mainCanvas);
        this.imageView.setImageBitmap(this.mainImage);
    }

    private void createCanvas() {
        this.mainImage = Bitmap.createBitmap(getDisplaySize().width,
                getDisplaySize().height,
                Bitmap.Config.ARGB_8888);

        this.mainCanvas = new Canvas(this.mainImage);
    }

    private boolean touchIsInside(IScreenPartition partition, ScreenPoint screenPoint) {
        Rect partitionBounds = Util.getRect(partition.getOrigin(), partition.getSize());

        return partitionBounds.contains(screenPoint.x, screenPoint.y);
    }

}
