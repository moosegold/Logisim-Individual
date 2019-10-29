package logisim.layout;

import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.FragmentActivity;

import logisim.Grid;
import logisim.R;
import logisim.util.Size;

public class MainLayout extends FragmentActivity {

    Guideline guideline;

    Grid grid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);

        ImageView contentView = findViewById(R.id.contentView);
//        contentView.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.landscape_image));

        Point displaySize = new Point();
        getWindowManager().getDefaultDisplay().getSize(displaySize);

        int sidbarWidth = 200;
        int widthTiles = 12;
        // Get the length of the tiles to fit the specified number of tiles.
        int tileLengthPx = (displaySize.x - sidbarWidth) / (widthTiles + 1);
        // How many tiles will fit vertically given the width of the tile. Tiles are squares.
        int heightTiles = displaySize.y / tileLengthPx - 1;
//        grid = new Grid(widthTiles, heightTiles, tileLengthPx, screenManager, stateManager, origin, new Size(displaySize.x - sidbarWidth, displaySize.y));

        guideline = findViewById(R.id.guideline);
        setSidebarWidth(sidbarWidth);
    }

    public void setSidebarWidth(int width) {
        guideline.setGuidelineBegin(width);
    }
}
