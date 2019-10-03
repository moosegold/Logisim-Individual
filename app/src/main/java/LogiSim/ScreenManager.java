package LogiSim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.ImageWriter;
import android.view.Display;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;

public class ScreenManager {

    private LinkedList<IScreenPartition> partitions;
    private Display display;
    private Canvas mainCanvas;

    final Context appContext;

    ScreenManager(Display display, ImageView imageView, Context appContext) {
        this.partitions = new LinkedList<>();
        this.display = display;
        this.appContext = appContext;
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

    public void handleTouch(ScreenPoint screenPoint) {
        for (IScreenPartition partition : partitions) {
            if (touchIsInside(partition, screenPoint)) {
                screenPoint.offset(partition.getOrigin().x, partition.getOrigin().y);
                partition.processTouch(screenPoint);
            }
        }

        this.draw();
    }

    public void draw() {
        for (IScreenPartition partition : partitions) {
            partition.draw();
            ScreenPoint origin = partition.getOrigin();

            try {
                File directory = new File(System.getProperty("java.io.tmpdir"));


                File file = new File(directory, "img");
                System.out.println((file.createNewFile() ? "Created file: " : "Failed to create file: ") + file.getAbsolutePath());
                partition.getPartitionBitmap().compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
                System.exit(0);
            } catch (Exception ex) {
                System.out.println("Caught exception: " + ex.getMessage());
                ex.printStackTrace();
            }
            mainCanvas.drawBitmap(partition.getPartitionBitmap(), origin.x, origin.y, new Paint());
        }
    }

    private void createCanvas() {
        Bitmap image = Bitmap.createBitmap(getDisplaySize().width,
                getDisplaySize().height,
                Bitmap.Config.ARGB_8888);

        this.mainCanvas = new Canvas(image);
    }

    private boolean touchIsInside(IScreenPartition partition, ScreenPoint screenPoint) {
        Rect partitionBounds = new Rect(
                partition.getOrigin().x,
                partition.getOrigin().y,
                partition.getSize().width,
                partition.getSize().height
        );

        return partitionBounds.contains(screenPoint.x, screenPoint.y);
    }

}
