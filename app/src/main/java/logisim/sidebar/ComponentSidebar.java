package logisim.sidebar;

import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Deque;
import java.util.LinkedList;

import logisim.AbstractScreenPartition;
import logisim.Grid;
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

    private final double BUTTON_INSET_RATIO = 1.0 / 20;
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

    private boolean saveMode = false;

    private final Grid grid;

    public ComponentSidebar(ScreenPoint origin, Size size, ScreenManager screenManager, Grid grid) {
        super(origin, size, screenManager);
        calculateInsets();
        addButtons();
        this.grid = grid;
    }

    @Override
    public String getName() {
        return "Sidebar";
    }

    private void addButtons() {
        addComponentButton("AND Gate", R.drawable.and_gate, ANDGate.class);
        addComponentButton("OR Gate", R.drawable.or_gate, ORGate.class);
        addComponentButton("NOT Gate", R.drawable.not_gate, NOTGate.class);
        addComponentButton("Switch", R.drawable.switch_off, ComponentSwitch.class);
        addComponentButton("LED", R.drawable.led_on, ComponentLED.class);

        addSaveSlotButton("C");
        addSaveSlotButton("B");
        addSaveSlotButton("A");

        addSaveButton();
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
        lastComponentButtonAdded = new ComponentSidebarButton(new ScreenPoint(xPos, yPos), length, componentName, Rresource, this, representation);
        buttons.addLast(lastComponentButtonAdded);
    }

    /**
     * Adds a save button above the previous button starting from the bottom of the sidebar.
     */
    private void addSaveSlotButton(String label) {
        int width = getButtonLength();
        int height = width / 2;
        width = width / 2 - insetPx;
        int xPos = insetPx;
        int yPos;
        if (lastSaveButtonAdded == null) {
            yPos = getSize().height - insetPx - height;
        } else {
            yPos = lastSaveButtonAdded.point.y - insetPx - height;
        }
        lastSaveButtonAdded = new ActionSidebarButton(new ScreenPoint(xPos, yPos), new Size(width, height), label, this,
                () -> {
                    String feedback = "";
                    if (saveMode) {
                        boolean result = grid.saveGrid(label);
                        feedback = result ? "Layout saved to " + label : "Failed to save layout";
                    } else {
                        boolean result = grid.loadGrid(label);
                        feedback = result ? "Loaded layout " + label : "Failed to load layout " + label;
                    }
                    screenManager.setStatusBarText(feedback);
                    System.out.println(feedback);
                });
        buttons.addLast(lastSaveButtonAdded);
    }

    private void addSaveButton() {
        int width = getButtonLength() / 2 - insetPx;
        int xPos = getButtonLength() / 2 + insetPx * 2;
        int yPos = lastSaveButtonAdded.point.y;
        int height = screenManager.getDisplaySize().height - yPos - insetPx;
        buttons.addLast(new ActionSidebarButton(new ScreenPoint(xPos, yPos), new Size(width, height), "Save", this,
                () -> {
            saveMode = !saveMode;
            screenManager.setStatusBarText("Select save slot");
            System.out.println("SaveMode: " + saveMode);
                }));
    }

    private void drawLoadLabel() {
        String text = "Load";
        Paint paint = Paints.LABEL_TEXT;
        int xPos = lastSaveButtonAdded.getCenterForPartition().x - TextDrawUtil.getTextWidthPx(text, paint) / 2;
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
            canvas.drawBitmap(button.getImage(), button.point.x, button.point.y, Paints.IMAGE_OPAQUE);
        }

        drawLoadLabel();
    }

    public void processTouchUp(ScreenPoint localPoint) {
        screenManager.setStatusBarText("");
        SidebarButton touchedButton = getButtonPress(localPoint);
        screenManager.dragSourceButton = null;
        if (buttonBeingTouched != null && touchedButton == buttonBeingTouched) {
            buttonBeingTouched.handleTap();
        }
        touchInProgress = false;
        if (touchedButton != null && !touchedButton.label.equals("Save")) {
            saveMode = false;
        }
    }

    public void processTouchDown(ScreenPoint localPoint) {
        SidebarButton touchedButton = getButtonPress(localPoint);
        if (!touchInProgress) {
            this.buttonBeingTouched = touchedButton;
        }
        touchInProgress = true;
    }

    public void processTouchDrag(ScreenPoint localPoint) {
        if (touchInProgress && buttonBeingTouched != null) {
            buttonBeingTouched.handleDragStart();
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
