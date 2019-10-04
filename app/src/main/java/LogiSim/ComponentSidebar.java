package LogiSim;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class ComponentSidebar extends AbstractScreenPartition {

    final Paint backgroundColor = new Paint();

    final double BUTTON_INSET_RATIO = 1.0/20;
    int insetPx;

    private Deque<SidebarButton> buttons = new LinkedList<>();

    ComponentSidebar(ScreenPoint origin, Size size, ScreenManager screenManager) {
        super(origin, size, screenManager);
        setupPaints();
        calculateInsets();
        addButtons();
    }

    private void addButtons() {
        addButton("AND", R.drawable.and_gate);
        addButton("OR", R.drawable.or_gate);
        addButton("NOT", R.drawable.not_gate);
    }

    private void addButton(String action, int Rresource) {
        // Add button below last
        int yPos = insetPx;
        if (buttons.size() > 0) {
            yPos = buttons.getLast().size;
        }
        int xPos = insetPx;
        // Buttons are squares.
        int length = getSize().width - (insetPx * 2);
        buttons.addLast(new SidebarButton(new ScreenPoint(xPos, yPos), length, action, Rresource, this));
    }

    @Override
    public void draw() {
        fillBackground();

        for (SidebarButton button : buttons) {
            button.draw();
        }
    }

    public void processTouch(ScreenPoint localPoint) {

    }

    private void setupPaints() {
        backgroundColor.setColor(Color.GRAY);
    }

    private void calculateInsets() {
        insetPx = (int) (getSize().width * BUTTON_INSET_RATIO);
    }

    public void fillBackground() {
        canvas.drawRect(new Rect(0, 0, getSize().width, getSize().height), backgroundColor);
    }

}
