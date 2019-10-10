package logisim.sidebar;

import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Deque;
import java.util.LinkedList;

import logisim.AbstractScreenPartition;
import logisim.util.Paints;
import logisim.R;
import logisim.ScreenManager;
import logisim.util.ScreenPoint;
import logisim.util.Size;
import logisim.util.TextDrawUtil;
import logisim.util.Util;
import logisim.tiles.components.concrete.ANDGate;
import logisim.tiles.components.Component;
import logisim.tiles.components.concrete.ComponentLED;
import logisim.tiles.components.concrete.ComponentSwitch;
import logisim.tiles.components.concrete.NOTGate;
import logisim.tiles.components.concrete.ORGate;

public class ComponentSidebar extends AbstractScreenPartition {

    private final double BUTTON_INSET_RATIO = 1.0/20;
    private int insetPx;

    private Deque<SidebarButton> buttons = new LinkedList<>();

    private SidebarButton lastComponentButtonAdded;
    private SidebarButton lastSaveButtonAdded;

    /**
     * Set to the button a touch began on.
     * Becomes null when the drag ends.
     */
    private SidebarButton buttonBeingTouched;
    private boolean touchInProgress;

    public ComponentSidebar(ScreenPoint origin, Size size, ScreenManager screenManager) {
        super(origin, size, screenManager);
        calculateInsets();
        addButtons();
    }

    @Override
    public String getName() {
        return "Sidebar";
    }

    private void addButtons() {
        addComponentButton("AND Gate", R.drawable.and_gate, ANDGate.class);
        addComponentButton("OR Gate", R.drawable.or_gate, ORGate.class);
        addComponentButton("NOT Gate", R.drawable.not_gate, NOTGate.class);
        addComponentButton("SWITCH", R.drawable.switch_off, ComponentSwitch.class);
        addComponentButton("LED", R.drawable.led_on, ComponentLED.class);

        addSaveButton("C");
        addSaveButton("B");
        addSaveButton("A");
    }

    /**
     * Adds a new button below the last button added starting from the top of the sidebar.
     */
    private void addComponentButton(String componentName, int Rresource, Class<? extends Component> representation) {
        // Add button below last
        int yPos = insetPx;
        if (lastComponentButtonAdded != null) {
            yPos += lastComponentButtonAdded.point.y + buttons.getLast().size.height;
        }
        int xPos = insetPx;
        // Buttons are squares.
        int length = getButtonLength();
        System.out.println("Adding " + componentName + " button at: " + new ScreenPoint(xPos, yPos));
        lastComponentButtonAdded = new ComponentSidebarButton(new ScreenPoint(xPos, yPos), length, componentName, Rresource, this, representation);
        buttons.addLast(lastComponentButtonAdded);
    }

    /**
     * Adds a save button above the previous button starting from the bottom of the sidebar.
     */
    private void addSaveButton(String label) {
        int width = getButtonLength();
        int height = width / 2;
        int xPos = insetPx;
        int yPos;
        if (lastSaveButtonAdded == null) {
            yPos = getSize().height - insetPx - height;
        } else {
            yPos = lastSaveButtonAdded.point.y - insetPx - height;
        }
        System.out.println("Adding " + label + " save button at: " + new ScreenPoint(xPos, yPos));
        lastSaveButtonAdded = new SaveSidebarButton(new ScreenPoint(xPos, yPos), new Size(width, height), label, this);
        buttons.addLast(lastSaveButtonAdded);
    }

    private void drawSaveLabel() {
        String text = "Save";
        Paint paint = Paints.LABEL_TEXT;
        int xPos = getSize().width / 2 - TextDrawUtil.getTextWidthPx(text, paint) / 2;
        int yPos = lastSaveButtonAdded.point.y - insetPx;
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

    public void processTouchUp(ScreenPoint localPoint) {
        SidebarButton touchedButton = getButtonPress(localPoint);
        screenManager.dragSourceButton = null;
        if (buttonBeingTouched != null && touchedButton == buttonBeingTouched)
            System.out.println("[" + getName() + "] Touched button: " + touchedButton);
        touchInProgress = false;
    }

    public void processTouchDown(ScreenPoint localPoint) {
        SidebarButton touchedButton = getButtonPress(localPoint);
        if (!touchInProgress) {
            this.buttonBeingTouched = touchedButton;
        }
        touchInProgress = true;
    }

    public void processTouchDrag(ScreenPoint localPoint) {
        if (touchInProgress) {
            screenManager.setDraggedObject(buttonBeingTouched.getDragImage());
            if (buttonBeingTouched instanceof ComponentSidebarButton) {
                screenManager.dragSourceButton = (ComponentSidebarButton) buttonBeingTouched;
                screenManager.setNameOfDraggedComponent(buttonBeingTouched.label);
            }
        }
    }

    private SidebarButton getButtonPress(ScreenPoint localPoint) {
        for (SidebarButton button : buttons) {
            Rect buttonBounds = Util.getRect(button.point, button.size);

            if (buttonBounds.contains(localPoint.x, localPoint.y))
                return button;
        }
        return null;
    }

    private void calculateInsets() {
        insetPx = (int) (getSize().width * BUTTON_INSET_RATIO);
    }

    public void fillBackground() {
        canvas.drawRect(new Rect(0, 0, getSize().width, getSize().height), Paints.SIDEBAR_BACKGROUND_COLOR);
    }

}
