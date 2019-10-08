package LogiSim;

import android.graphics.Paint;

public class TextDrawUtil {

    public static int getTextWidthPx(String text, Paint paint) {
        float[] widths = new float[text.length()];
        paint.getTextWidths(text, widths);
        float width = 0.0f;
        for (float cwidth : widths) {
            width += cwidth;
        }
        return (int) width;
    }

    public static int getTextHeightPx(Paint paint) {
        return (int) paint.getTextSize();
    }

}
