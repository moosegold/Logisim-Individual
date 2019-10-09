package LogiSim;

import android.graphics.Bitmap;

public interface IScreenPartition {

    void processTouchDown(ScreenPoint localPoint);

    void processTouchUp(ScreenPoint localPoint);

    void processTouchDrag(ScreenPoint localPoint);

    void draw();

    Bitmap getPartitionBitmap();

    String getName();

    ScreenPoint getOrigin();

    Size getSize();

}
