package LogiSim;

import android.graphics.Canvas;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DebugTextDrawer {

    private static final int VERTICAL_SPACING = 1;

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
        int yCoord = startPos.y + Paints.DEBUG_TEXT_SIZE;
        for (String text : textToDraw) {
            drawString(canvas, text, yCoord);
            yCoord += Paints.DEBUG_TEXT_SIZE + VERTICAL_SPACING;
        }
    }

    private void drawUpwards(Canvas canvas) {
        Collections.reverse(textToDraw);
        int yCoord = startPos.y;
        for (String text : textToDraw) {
            drawString(canvas, text, yCoord);
            yCoord -= Paints.DEBUG_TEXT_SIZE + VERTICAL_SPACING;
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
        canvas.drawText(text, startPos.x, yCoord, Paints.DEBUG_TEXT);
    }

    private void drawBackground(Canvas canvas, String text, int yCoord) {
        float width = TextDrawUtil.getTextWidthPx(text, Paints.DEBUG_BACKGROUND_COLOR);
        canvas.drawRect(startPos.x, yCoord - Paints.DEBUG_TEXT_SIZE, startPos.x + (int) width, yCoord + 2, Paints.DEBUG_BACKGROUND_COLOR);
    }

}
