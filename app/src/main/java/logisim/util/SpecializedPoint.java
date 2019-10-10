package logisim.util;

import android.graphics.Point;

public class SpecializedPoint extends Point {

    public SpecializedPoint(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
