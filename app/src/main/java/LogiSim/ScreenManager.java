package LogiSim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.LinkedList;

public class ScreenManager {

    private LinkedList<IScreenPartition> partitions;
    private Display display;
    private Canvas mainCanvas;
    private Bitmap mainImage;
    private ImageView imageView;
    DebugTextDrawer debugText;

    final Context appContext;

    private String lastPartition = null;
    private ScreenPoint lastLocalPoint = null;

    ScreenManager(Display display, ImageView imageView, Context appContext) {
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
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            // Fire the release touch event on all partitions so they can reset their state.
            for (IScreenPartition partition : partitions)
                partition.processTouchUp(getLocalPoint(partition, screenPoint));
        } else {
            IScreenPartition partition = getTouchedPartition(screenPoint);
            if (partition != null) {
                ScreenPoint localPoint = getLocalPoint(partition, screenPoint);
                if (action == MotionEvent.ACTION_DOWN) {
                    //handleTouchDown(screenPoint);
                    partition.processTouchDown(localPoint);
                } else if (action == MotionEvent.ACTION_MOVE) {
                    //handleTouchDrag(screenPoint);
                    partition.processTouchDrag(localPoint);
                }
            }
        }

        this.draw();
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
        debugText.addText("");
//        debugText.addText("Global Touch: " + lastTouch);
//        debugText.addText("Partition: " + lastPartition);
//        debugText.addText("Local Touch: " + lastLocalPoint);
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
