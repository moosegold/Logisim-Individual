package logisim;

import android.graphics.Bitmap;

import logisim.util.ScreenPoint;
import logisim.util.Size;

public interface IScreenPartition {

    void processTouchDown(ScreenPoint localPoint);

    void processTouchUp(ScreenPoint localPoint);

    void processTouchDrag(ScreenPoint localPoint);

    boolean touchInBounds(ScreenPoint localPoint);

    void draw();

    Bitmap getPartitionBitmap();

    String getName();

    ScreenPoint getOrigin();

    Size getSize();

}
