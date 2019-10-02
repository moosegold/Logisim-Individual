package LogiSim;

import androidx.annotation.Nullable;

public class GridScreen extends AbstractScreenPartition {

    Grid grid;

    @Nullable
    AbstractTile touchedTile;

    GridScreen(ScreenPoint origin, Size size, Grid grid) {
        super(origin, size);
        this.grid = grid;
    }

    @Override
    public void processTouch(ScreenPoint localPoint) {
        GridPoint gridPoint = convertToGridPoint(localPoint);
        grid.getTile(gridPoint);
    }

    @Override
    public void draw() {
        for (int x = 0; x < grid.gridSize.width ; x++) {
            for (int y = 0; y < grid.gridSize.height ; y++) {
                grid.getTile(new GridPoint(x, y)).draw(this.canvas);
            }
        }
    }

    private GridPoint convertToGridPoint(ScreenPoint localPoint) {
        int gridPointX = localPoint.x / grid.tileSize;
        int gridPointY = localPoint.y / grid.tileSize;
        return new GridPoint(gridPointX, gridPointY);
    }

}
