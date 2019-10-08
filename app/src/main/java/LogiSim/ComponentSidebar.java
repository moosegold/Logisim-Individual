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

    private SidebarButton lastComponentButton;
    private SidebarButton lastSaveButton;

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
        if (lastComponentButton != null) {
            yPos += lastComponentButton.point.y + buttons.getLast().size.height;
        }
        int xPos = insetPx;
        // Buttons are squares.
        int length = getButtonLength();
        System.out.println("Adding " + action + " button at: " + new ScreenPoint(xPos, yPos));
        lastComponentButton = new ComponentSidebarButton(new ScreenPoint(xPos, yPos), length, action, Rresource, this);;
        buttons.addLast(lastComponentButton);
    }

    /**
     * Adds a save button above the previous button starting from the bottom of the sidebar.
     */
    private void addSaveButton(String label) {
        int width = getButtonLength();
        int height = width / 2;
        int xPos = insetPx;
        int yPos;
        if (lastSaveButton == null) {
            yPos = getSize().height - insetPx - height;
        } else {
            yPos = lastSaveButton.point.y - insetPx - height;
        }
        System.out.println("Adding " + label + " save button at: " + new ScreenPoint(xPos, yPos));
        lastSaveButton = new SaveSidebarButton(new ScreenPoint(xPos, yPos), new Size(width, height), label, "SAVE", this);
        buttons.addLast(lastSaveButton);
    }

    private void drawSaveLabel() {
        String text = "Save";
        Paint paint = PaintBuilder.start().setColor(Color.BLACK).setTextSize(18f).makePaint();
        int width = getButtonLength();
        int height = TextDrawUtil.getTextHeightPx(paint);
        int xPos = getSize().width / 2 - TextDrawUtil.getTextWidthPx(text, paint) / 2;
        int yPos = lastSaveButton.point.y - insetPx;
        canvas.drawText(text, xPos, yPos, paint);
    }

    private int getButtonLength() {
        return getSize().width - (insetPx * 2);
    }

    @Override
    public void draw() {
        fillBackground();

        for (SidebarButton button : buttons) {
            button.draw();
            canvas.drawBitmap(button.getImage(), button.point.x, button.point.y, null);
        }

        drawSaveLabel();
    }

    public void processTouch(ScreenPoint localPoint) {
        SidebarButton touchedButton = getButtonPress(localPoint);
        System.out.println("Touched button: " + touchedButton);
    }

    private SidebarButton getButtonPress(ScreenPoint localPoint) {
        for (SidebarButton button : buttons) {
            Rect buttonBounds = Util.getRect(button.point, button.size);

            if (buttonBounds.contains(localPoint.x, localPoint.y))
                return button;
        }
        return null;
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
