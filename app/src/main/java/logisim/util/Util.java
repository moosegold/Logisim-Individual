package logisim.util;

import android.graphics.Point;
import android.graphics.Rect;

public class Util {

    public static Rect getRect(Point point, Size size) {
        return new Rect(point.x, point.y, point.x + size.width, point.y + size.height);
    }

}
