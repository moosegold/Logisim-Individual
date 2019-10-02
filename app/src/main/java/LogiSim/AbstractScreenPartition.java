package LogiSim;

import android.graphics.Bitmap;
import android.graphics.Canvas;

abstract class AbstractScreenPartition implements IScreenPartition {

    private ScreenPoint origin;
    private Size size;

    Canvas canvas;
    private Bitmap image;

    AbstractScreenPartition(ScreenPoint origin, Size size) {
        this.origin = origin;
        this.size = size;
        image = Bitmap.createBitmap(size.width, size.height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(image);
        //canvas = new Canvas(Bitmap.createBitmap(size.width, size.height, Bitmap.Config.ARGB_8888));
    }

    @Override
    public Bitmap getPartitionBitmap() {
        return image;
    }

    public ScreenPoint getOrigin() {
        return origin;
    }

    public Size getSize() {
        return size;
    }
}
