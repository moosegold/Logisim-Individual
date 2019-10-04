package LogiSim;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Deque;
import java.util.LinkedList;

public class DebugTextDrawer {

    private final int TEXT_SIZE = 12;

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
            Paint paint = createPaint();
            int yCoord = (int) (startPos.y + paint.getTextSize());
            for (String text : textToDraw) {
                drawString(text, yCoord, paint);
                yCoord += paint.getTextSize() + 1;
            }
        }
        textToDraw.clear();
    }

    private void drawString(String text, int yCoord, Paint paint) {
        canvas.drawText(text, startPos.x, yCoord, paint);
    }

    private Paint createPaint() {
        Paint newPaint = new Paint();
        newPaint.setTextSize(12);
        newPaint.setColor(Color.BLACK);
        return newPaint;
    }

}
