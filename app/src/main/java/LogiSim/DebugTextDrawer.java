package LogiSim;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Deque;
import java.util.LinkedList;

public class DebugTextDrawer {

    private static final int TEXT_SIZE = 12;
    private static final int BACKGROUND_OPACITY = 150;

    private ScreenPoint startPos;
    private Canvas canvas;
    private boolean active;

    private Deque<String> textToDraw = new LinkedList<>();

    DebugTextDrawer(ScreenPoint pos, Canvas canvas, boolean active) {
        this.startPos = pos;
        this.canvas = canvas;
        this.active = active;
    }

    public void addText(String string) {
        textToDraw.addLast(string);
    }

    public void draw(Canvas canvas) {
        if (active) {
            int yCoord = (int) (startPos.y + TEXT_SIZE);
            for (String text : textToDraw) {
                drawString(text, yCoord);
                yCoord += TEXT_SIZE + 1;
            }
        }
        textToDraw.clear();
    }

    private void drawString(String text, int yCoord) {
        drawBackground(text, yCoord);
        canvas.drawText(text, startPos.x, yCoord, createTextPaint());
    }

    private void drawBackground(String text, int yCoord) {
        float[] widths = new float[text.length()];
        createBackgroundPaint().getTextWidths(text, widths);
        float width = 0.0f;
        for (float cwidth : widths) {
            width += cwidth;
        }
        canvas.drawRect(startPos.x, yCoord - TEXT_SIZE, startPos.x + (int) width, yCoord + 2, createBackgroundPaint());
    }

    private Paint createTextPaint() {
        Paint newPaint = new Paint();
        newPaint.setTextSize(TEXT_SIZE);
        newPaint.setColor(Color.BLACK);
        return newPaint;
    }

    private Paint createBackgroundPaint() {
        Paint newPaint = new Paint();
        newPaint.setTextSize(TEXT_SIZE);
        newPaint.setColor(Color.WHITE);
        newPaint.setAlpha(BACKGROUND_OPACITY);
        return newPaint;
    }

}
