package LogiSim;

import android.graphics.Bitmap;
import android.graphics.Canvas;

abstract class AbstractScreenPartition implements IScreenPartition {

    final ScreenManager screenManager;

    private ScreenPoint origin;
    private Size size;

    Canvas canvas;
    private Bitmap image;

    AbstractScreenPartition(ScreenPoint origin, Size size, ScreenManager screenManager) {
        this.origin = origin;
        this.size = size;
        image = Bitmap.createBitmap(size.width, size.height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(image);
        this.screenManager = screenManager;
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
