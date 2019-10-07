package LogiSim;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DebugTextDrawer {

    private static final int TEXT_SIZE = 14;
    private static final int VERTICAL_SPACING = 1;
    private static final int BACKGROUND_OPACITY = 150;

    private ScreenPoint startPos;
    private boolean active;

    boolean drawDownwards = true;
    private List<String> textToDraw = new LinkedList<>();

    DebugTextDrawer(ScreenPoint pos, boolean active) {
        this.startPos = pos;
        this.active = active;
    }

    DebugTextDrawer(boolean active) {
        this(new ScreenPoint(1, 1), active);
    }

    public void addText(String string) {
        textToDraw.add(string);
    }

    public void draw(Canvas canvas) {
        if (active && LogiSim.DEBUG_TEXT_ENABLED) {
            if (this.drawDownwards)
                drawDownwards(canvas);
            else
                drawUpwards(canvas);
        }
        textToDraw.clear();
    }

    private void drawDownwards(Canvas canvas) {
        int yCoord = startPos.y + TEXT_SIZE;
        for (String text : textToDraw) {
            drawString(canvas, text, yCoord);
            yCoord += TEXT_SIZE + VERTICAL_SPACING;
        }
    }

    private void drawUpwards(Canvas canvas) {
        Collections.reverse(textToDraw);
        int yCoord = startPos.y;
        for (String text : textToDraw) {
            drawString(canvas, text, yCoord);
            yCoord -= TEXT_SIZE + VERTICAL_SPACING;
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return active;
    }

    private void drawString(Canvas canvas, String text, int yCoord) {
        drawBackground(canvas, text, yCoord);
        canvas.drawText(text, startPos.x, yCoord, createTextPaint());
    }

    private void drawBackground(Canvas canvas, String text, int yCoord) {
        float width = TextDrawUtil.getTextWidth(text, createBackgroundPaint());
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
