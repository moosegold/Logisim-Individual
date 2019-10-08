package LogiSim;

import android.graphics.Color;
import android.graphics.Paint;

public class Paints {


    //----------------------------------------------------------------------------------------------
    // TEXT PAINT
    //----------------------------------------------------------------------------------------------

    static final int STANDARD_TEXT_SIZE = 18;
    static final int DEBUG_TEXT_SIZE = 14;

    /**
     * Labels like the save label, or the name of the component being dragged.
     */
    static final Paint LABEL_TEXT = PaintBuilder.start()
            .setColor(Color.BLACK)
            .setTextSize(STANDARD_TEXT_SIZE).makePaint();

    /**
     * For text drawn by DebugTextDrawer
     */
    static final Paint DEBUG_TEXT = PaintBuilder.start()
            .setColor(Color.BLACK)
            .setTextSize(DEBUG_TEXT_SIZE).makePaint();

    /**
     * For text drawn in the save buttons
     */
    static final Paint SAVE_BUTTON_TEXT = PaintBuilder.start()
            .setColor(Color.BLUE)
            .setTextSize(STANDARD_TEXT_SIZE)
            .setBold(true).makePaint();


    //----------------------------------------------------------------------------------------------
    // BACKGROUND COLORS
    //----------------------------------------------------------------------------------------------

    /**
     * The transparent background behind debug text.
     */
    static final Paint DEBUG_BACKGROUND_COLOR = PaintBuilder.start()
            .setTextSize(DEBUG_TEXT_SIZE)
            .setColor(Color.WHITE)
            .setAlpha(150).makePaint();

    static final Paint GRID_BACKGROUND_COLOR = PaintBuilder.start()
            .setColor(Color.WHITE).makePaint();

    static final Paint SIDEBAR_BACKGROUND_COLOR = PaintBuilder.start()
            .setColor(Color.GRAY).makePaint();


    //----------------------------------------------------------------------------------------------
    // BORDER COLORS
    //----------------------------------------------------------------------------------------------

    /**
     * Color of the border surrounding buttons on the sidebar.
     */
    static final Paint BUTTON_BORDER_COLOR = PaintBuilder.start()
            .setColor(Color.BLUE)
            .setStyle(Paint.Style.STROKE).makePaint();

    /**
     * Color of the borders of tiles forming the grid lines.
     */
    static final Paint TILE_BORDER_COLOR = PaintBuilder.start()
            .setColor(Color.BLACK)
            .setStyle(Paint.Style.STROKE).makePaint();

}