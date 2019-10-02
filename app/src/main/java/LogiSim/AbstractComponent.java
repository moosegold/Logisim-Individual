package LogiSim;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class AbstractComponent extends AbstractTile {

    AbstractComponent(AbstractTile tile) {
        super(tile);
    }

    /**
     * A wire is being dragged to the component. It is up to the component to decide what
     * to do.
     */
    public abstract void processConnection(ILogicComponent source);

    public abstract Bitmap getImage();

    @Override
    void draw(Canvas canvas) {
        Bitmap image = this.getImage();
        ScreenPoint screenPoint = grid.convertToScreenPoint(this.gridPoint);
        canvas.drawBitmap(image, screenPoint.x, screenPoint.y, null);
    }

}
