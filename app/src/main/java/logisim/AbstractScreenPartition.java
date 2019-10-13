package logisim;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import logisim.state.StateManager;
import logisim.util.LocalPoint;
import logisim.util.ScreenPoint;
import logisim.util.Size;

public abstract class AbstractScreenPartition implements IScreenPartition {

    public final ScreenManager screenManager;
    public final StateManager stateManager;

    private ScreenPoint origin;
    private Size size;

    protected Canvas canvas;
    private Bitmap image;

    public AbstractScreenPartition(ScreenPoint origin, Size size, ScreenManager screenManager, StateManager stateManager) {
        this.origin = origin;
        this.size = size;
        image = Bitmap.createBitmap(size.width, size.height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(image);
        this.screenManager = screenManager;
        this.stateManager = stateManager;
    }

    @Override
    public final boolean containsTouch(ScreenPoint screenPoint) {
        Rect bounds = new Rect(0, 0, size.width, size.height);
        LocalPoint localPoint = convertToLocalPoint(screenPoint);
        return bounds.contains(localPoint.x, localPoint.y);
    }

    public final LocalPoint convertToLocalPoint(ScreenPoint globalPoint) {
        LocalPoint newPoint = new LocalPoint(globalPoint.x, globalPoint.y);
        newPoint.offset(-origin.x, -origin.y);
        return newPoint;
    }

    public final ScreenPoint convertToScreenPoint(LocalPoint localPoint) {
        ScreenPoint newPoint = new ScreenPoint(localPoint.x, localPoint.y);
        newPoint.offset(origin.x, origin.y);
        return newPoint;
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
