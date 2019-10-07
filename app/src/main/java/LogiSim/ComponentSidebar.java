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

    private Deque<SidebarButton> componentButtons = new LinkedList<>();

    private Deque<SidebarButton> saveButtons = new LinkedList<>();

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
        addComponentButton("AND", R.drawable.and_gate);
        addComponentButton("OR", R.drawable.or_gate);
        addComponentButton("NOT", R.drawable.not_gate);
        addComponentButton("SWITCH", R.drawable.switch_off);
        addComponentButton("LED", R.drawable.led_on);

        addSaveButton("C");
        addSaveButton("B");
        addSaveButton("A");
    }

    /**
     * Adds a new button below the last button added starting from the top of the sidebar.
     */
    private void addComponentButton(String action, int Rresource) {
        // Add button below last
        int yPos = insetPx;
        if (componentButtons.size() > 0) {
            yPos += componentButtons.getLast().point.y + componentButtons.getLast().length;
        }
        int xPos = insetPx;
        // Buttons are squares.
        int length = getButtonLength();
        System.out.println("Adding " + action + " button at: " + new ScreenPoint(xPos, yPos));
        componentButtons.addLast(new SidebarButton(new ScreenPoint(xPos, yPos), length, action, Rresource, this));
    }

    /**
     * Adds a save button above the previous button starting from the bottom of the sidebar.
     */
    private void addSaveButton(String label) {
        int length = getButtonLength();
        int height = length / 2;
        int xPos = insetPx;
        int yPos = getSize().height - insetPx - height;
        //componentButtons.addLast(new SidebarButton(new ScreenPoint(xPos, yPos), length, label, ));

    }

    private int getButtonLength() {
        return getSize().width - (insetPx * 2);
    }

    @Override
    public void draw() {
        fillBackground();

        for (SidebarButton button : componentButtons) {
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
