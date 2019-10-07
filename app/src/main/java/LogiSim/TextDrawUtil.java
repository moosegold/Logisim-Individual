package LogiSim;

import android.graphics.Paint;

public class TextDrawUtil {

    public static float getTextWidth(String text, Paint paint) {
        float[] widths = new float[text.length()];
        paint.getTextWidths(text, widths);
        float width = 0.0f;
        for (float cwidth : widths) {
            width += cwidth;
        }
        return width;
    }

}