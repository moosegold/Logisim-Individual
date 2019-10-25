package logisim;

import android.graphics.Bitmap;

import logisim.util.LocalPoint;
import logisim.util.ScreenPoint;
import logisim.util.Size;

public interface IScreenPartition {

    boolean containsTouch(ScreenPoint screenPoint);

    IInteractable getTouchedObject(LocalPoint localPoint);

    LocalPoint convertToLocalPoint(ScreenPoint globalPoint);

    void draw();

    Bitmap getPartitionBitmap();

    String getName();

    ScreenPoint getOrigin();

    Size getSize();

}
