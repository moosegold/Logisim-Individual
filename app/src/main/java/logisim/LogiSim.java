package logisim;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.FragmentActivity;

import java.util.Timer;
import java.util.TimerTask;

import logisim.sidebar.ComponentSidebar;
import logisim.state.StateManager;
import logisim.tiles.components.Component;
import logisim.tiles.components.concrete.ANDGate;
import logisim.tiles.components.concrete.ComponentLED;
import logisim.tiles.components.concrete.ComponentSwitch;
import logisim.util.GridPoint;
import logisim.util.ScreenPoint;
import logisim.util.Size;


public class LogiSim extends FragmentActivity {

    private static final double SIDEBAR_WIDTH_RATIO = 1.0/12;
    private int sidebar_width;

    private static final int GRID_WIDTH_TILES = 12;

    public static final boolean DEBUG_TEXT_ENABLED = false;

    private ScreenManager screenManager;
    private StateManager stateManager;

    Grid grid;

    /**
     * Base android setup.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        setupScreenManager();
    }

    public void setupScreenManager() {
        ImageView imageView = new ImageView(this);
        this.screenManager = new ScreenManager(getWindowManager().getDefaultDisplay(), this, imageView, this.getApplicationContext());
        this.stateManager = new StateManager(screenManager);
        screenManager.setStateManager(this.stateManager);

        calculateSidebarWidth();

        grid = setupGrid(GRID_WIDTH_TILES);

//        final View sidebar = findViewById(R.id.sidebar);

//        (new Timer()).schedule(new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(() -> {
//                    Guideline guideline = findViewById(R.id.guideline);
//                    ConstraintLayout.LayoutParams guidelineParams = (ConstraintLayout.LayoutParams) guideline.getLayoutParams();
//                    guidelineParams.guideBegin = 400;
//                    guideline.setLayoutParams(guidelineParams);
//                });
//            }
//        }, 5000);


//        addSidebarPartition(grid);
//        screenManager.draw();

//        setContentView(R.layout.main_layout);

    }

    private void calculateSidebarWidth() {
        sidebar_width = (int) (screenManager.getDisplaySize().width * SIDEBAR_WIDTH_RATIO);
    }

    private Grid setupGrid(int widthTiles) {
        Size displaySize = screenManager.getDisplaySize();

        int gridWidth = displaySize.width - sidebar_width;
        int startX = sidebar_width;
        ScreenPoint origin = new ScreenPoint(startX, 0);
        Size size = new Size(gridWidth, displaySize.height);

        // Get the length of the tiles to fit the specified number of tiles.
        int tileLengthPx = gridWidth / (widthTiles + 1);
        // How many tiles will fit vertically given the width of the tile. Tiles are squares.
        int heightTiles = size.height / tileLengthPx - 1;

        ImageView contentView = findViewById(R.id.contentView);
        contentView.addOnLayoutChangeListener((v, left, top, right, bottom,
                                               oldLeft, oldTop, oldRight, oldBottom) -> grid.draw());
//        contentView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.landscape_image));
        Grid grid = new Grid(widthTiles, heightTiles, tileLengthPx, contentView, screenManager, stateManager);

        ComponentSwitch compSwitch = new ComponentSwitch(grid);
        compSwitch.setState(true);
        Component led = new ComponentLED(grid);

        grid.setTile(new GridPoint(0, 0), compSwitch);
        grid.setTile(new GridPoint(1, 1), led);

        led.setInput(0, compSwitch);

        grid.draw();
//        screenManager.addPartition(grid);
        return grid;
    }

    private void addSidebarPartition(Grid grid) {
        ScreenPoint origin = new ScreenPoint(0, 0);
        Size size = new Size(sidebar_width, screenManager.getDisplaySize().height);
        screenManager.addPartition(new ComponentSidebar(origin, size, screenManager, stateManager, grid));
    }

    /**
     * The touch event is part of the Activity class so it must be dispatched to the screen manager
     * from here.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        ScreenPoint point = new ScreenPoint((int) event.getX(), (int) event.getY());
        grid.draw();
        screenManager.handleTouch(point, action);
        return true;
    }

}
