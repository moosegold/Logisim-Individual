package LogiSim;

import android.graphics.Rect;

public class Util {

    public static Rect getRect(ScreenPoint screenPoint, Size size) {
        return new Rect(screenPoint.x, screenPoint.y, screenPoint.x + size.width, screenPoint.y + size.height);
    }

}
