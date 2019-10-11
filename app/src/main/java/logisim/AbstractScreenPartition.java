package logisim;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import logisim.util.ScreenPoint;
import logisim.util.Size;

public abstract class AbstractScreenPartition implements IScreenPartition {

    public final ScreenManager screenManager;

    private ScreenPoint origin;
    private Size size;

    protected Canvas canvas;
    private Bitmap image;

    public AbstractScreenPartition(ScreenPoint origin, Size size, ScreenManager screenManager) {
        this.origin = origin;
        this.size = size;
        image = Bitmap.createBitmap(size.width, size.height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(image);
        this.screenManager = screenManager;
    }

    @Override
    public final boolean touchInBounds(ScreenPoint localPoint) {
        Rect bounds = new Rect(0, 0, size.width, size.height);
        return bounds.contains(localPoint.x, localPoint.y);
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
