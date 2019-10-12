package logisim;

import android.graphics.Bitmap;

import logisim.util.LocalPoint;
import logisim.util.ScreenPoint;
import logisim.util.Size;

public interface IScreenPartition {

    void processTouchDown(LocalPoint localPoint);

    void processTouchUp(LocalPoint localPoint);

    void processTouchDrag(LocalPoint localPoint);

    boolean touchInBounds(LocalPoint localPoint);

    LocalPoint localizePoint(ScreenPoint globalPoint);

    void draw();

    Bitmap getPartitionBitmap();

    String getName();

    ScreenPoint getOrigin();

    Size getSize();

}
