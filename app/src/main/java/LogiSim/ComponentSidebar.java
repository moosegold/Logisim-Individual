package LogiSim;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Deque;
import java.util.LinkedList;

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

    @Override
    public String getName() {
        return "Sidebar";
    }

    private void addButtons() {
        addButton("AND", R.drawable.and_gate);
        addButton("OR", R.drawable.or_gate);
        addButton("NOT", R.drawable.not_gate);
        addButton("SWITCH", R.drawable.switch_off);
        addButton("LED", R.drawable.led_on);
    }

    private void addButton(String action, int Rresource) {
        // Add button below last
        int yPos = insetPx;
        if (buttons.size() > 0) {
            yPos += buttons.getLast().point.y + buttons.getLast().length;
        }
        int xPos = insetPx;
        // Buttons are squares.
        int length = getSize().width - (insetPx * 2);
        System.out.println("Adding " + action + " button at: " + new ScreenPoint(xPos, yPos));
        buttons.addLast(new SidebarButton(new ScreenPoint(xPos, yPos), length, action, Rresource, this));
    }

    @Override
    public void draw() {
        fillBackground();

        for (SidebarButton button : buttons) {
            button.draw();
            canvas.drawBitmap(button.getImage(), button.point.x, button.point.y, null);
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
