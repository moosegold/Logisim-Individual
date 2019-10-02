package LogiSim;

import android.graphics.Bitmap;

public interface IScreenPartition {

    void processTouch(ScreenPoint localPoint);

    void draw();

    Bitmap getPartitionBitmap();

    ScreenPoint getOrigin();

    Size getSize();

}
