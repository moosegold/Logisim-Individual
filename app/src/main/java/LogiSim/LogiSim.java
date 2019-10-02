package LogiSim;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;


public class LogiSim extends Activity {

    private static final int SIDEBAR_WIDTH_PX = 100;

    private static final int GRID_WIDTH = 20;

    private ScreenManager screenManager;

    /**
     * Base android setup.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupScreenManager();
    }

    void setupScreenManager() {
        ImageView imageView = new ImageView(this);
        this.screenManager = new ScreenManager(getWindowManager().getDefaultDisplay(), imageView, this.getApplicationContext());
        setContentView(imageView);

        addSidebarPartition();
//        addGridPartition(setupGrid(GRID_WIDTH));
        setupGrid(GRID_WIDTH);
        screenManager.draw();
    }

//    Grid setupGrid(int width) {
//        Size displaySize = screenManager.getDisplaySize();
//        int tileWidthPx = displaySize.width / width;
//        // How many tiles will fit vertically given the width of the tile. Tiles are squares.
//        int height = (displaySize.height - displaySize.height % tileWidthPx) / tileWidthPx;
//        return new Grid(width, height, tileWidthPx, screenManager, );
//    }

    Grid setupGrid(int width) {
        Size displaySize = screenManager.getDisplaySize();
        int tileWidthPx = displaySize.width / width;
        // How many tiles will fit vertically given the width of the tile. Tiles are squares.
        int height = (displaySize.height - displaySize.height % tileWidthPx) / tileWidthPx;

        int gridWidth = SIDEBAR_WIDTH_PX - displaySize.width;
        int startX = SIDEBAR_WIDTH_PX;
        ScreenPoint origin = new ScreenPoint(startX, 0);
        Size size = new Size(displaySize.width - origin.x, displaySize.height);
//        screenManager.addPartition(new GridScreen(origin, size, grid));

        Grid grid = new Grid(width, height, tileWidthPx, screenManager, origin, size);
        screenManager.addPartition(grid);
        return grid;
    }

    private void addSidebarPartition() {
        ScreenPoint origin = new ScreenPoint(0, 0);
        Size size = new Size(SIDEBAR_WIDTH_PX, screenManager.getDisplaySize().height);
        screenManager.addPartition(new ComponentSidebar(origin, size));
    }

//    private void addGridPartition(Grid grid) {
//        Size screenSize = screenManager.getDisplaySize();
//        int gridWidth = SIDEBAR_WIDTH_PX - screenSize.width;
//        int startX = SIDEBAR_WIDTH_PX;
//        ScreenPoint origin = new ScreenPoint(startX, 0);
//        Size size = new Size(screenSize.width - origin.x, screenSize.height);
//        screenManager.addPartition(new GridScreen(origin, size, grid));
//    }

    /**
     * The touch event is part of the Activity class so it must be dispatched to the screen manager
     * from here.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Dispatch the touch to the screen manager when the finger is lifted.
        if((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
            screenManager.handleTouch(new ScreenPoint((int) event.getX(), (int) event.getY()));
        }

        return true;
    }

}
