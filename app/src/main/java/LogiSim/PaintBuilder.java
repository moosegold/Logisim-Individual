package LogiSim;

import android.graphics.Color;
import android.graphics.Paint;

public class PaintBuilder {

    private Paint paint;

    private PaintBuilder() {
        paint = new Paint();
    }

    public static PaintBuilder start() {
        return new PaintBuilder();
    }

    public Paint makePaint() {
        return paint;
    }

    public PaintBuilder setColor(int color) {
        paint.setColor(color);
        return this;
    }

    public PaintBuilder setTextSize(float size) {
        paint.setTextSize(size);
        return this;
    }

}
