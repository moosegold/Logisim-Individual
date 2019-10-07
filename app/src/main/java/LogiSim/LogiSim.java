package LogiSim;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;


public class LogiSim extends Activity {

    private static final double SIDEBAR_WIDTH_RATIO = 1.0/12;
    private int sidebar_width;

    private static final int GRID_WIDTH_TILES = 16;

    public static final boolean DEBUG_TEXT_ENABLED = true;

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

        calculateSidebarWidth();

        addSidebarPartition();
        setupGrid(GRID_WIDTH_TILES);
        screenManager.draw();

        setContentView(imageView);

    }

    void calculateSidebarWidth() {
        sidebar_width = (int) (screenManager.getDisplaySize().width * SIDEBAR_WIDTH_RATIO);
    }

    void setupGrid(int widthTiles) {
        Size displaySize = screenManager.getDisplaySize();

        int gridWidth = displaySize.width - sidebar_width;
        int startX = sidebar_width;
        ScreenPoint origin = new ScreenPoint(startX, 0);
        Size size = new Size(gridWidth, displaySize.height);

        // Get the length of the tiles to fit the specified number of tiles.
        int tileLengthPx = gridWidth / (widthTiles + 1);
        // How many tiles will fit vertically given the width of the tile. Tiles are squares.
        int heightTiles = size.height / tileLengthPx - 1;

        Grid grid = new Grid(widthTiles, heightTiles, tileLengthPx, screenManager, origin, size);
        screenManager.addPartition(grid);
    }

    private void addSidebarPartition() {
        ScreenPoint origin = new ScreenPoint(0, 0);
        Size size = new Size(sidebar_width, screenManager.getDisplaySize().height);
        screenManager.addPartition(new ComponentSidebar(origin, size, screenManager));
    }

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
