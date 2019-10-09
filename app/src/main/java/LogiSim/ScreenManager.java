package LogiSim;

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

public class ScreenManager {

    private LinkedList<IScreenPartition> partitions;
    private Display display;
    private Canvas mainCanvas;
    private Bitmap mainImage;
    private ImageView imageView;
    DebugTextDrawer debugText;

    final Context appContext;

    private ScreenPoint lastTouch = null;
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
        IScreenPartition partition =
        if (action == MotionEvent.ACTION_DOWN) {
            handleTouchDown(screenPoint);
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            if (action == MotionEvent.ACTION_CANCEL)
                System.out.println("The motion event was ACTION_CANCEL");
            handleTouchUp(screenPoint);
        } else if (action == MotionEvent.ACTION_MOVE) {
            handleTouchDrag(screenPoint);
        }
//        this.lastTouch = screenPoint.copy();
//        boolean foundPartition = false;
//        for (IScreenPartition partition : partitions) {
//            if (touchIsInside(partition, screenPoint)) {
//                foundPartition = true;
//                this.lastPartition = partition.getName();
//                screenPoint.offset(-partition.getOrigin().x, -partition.getOrigin().y);
//                this.lastLocalPoint = screenPoint.copy();
//                partition.processTouch(screenPoint);
//            }
//        }
//        if (!foundPartition) {
//            this.lastPartition = null;
//            this.lastLocalPoint = null;
//        }
//
//        this.draw();
    }

    public void handleTouchDown(ScreenPoint localPoint) {

    }

    public void handleTouchUp(ScreenPoint localPoint) {

    }

    public void handleTouchDrag(ScreenPoint localPoint) {

    }

    private IScreenPartition getPartition() {
        
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
        debugText.addText("Global Touch: " + lastTouch);
        debugText.addText("Partition: " + lastPartition);
        debugText.addText("Local Touch: " + lastLocalPoint);
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
