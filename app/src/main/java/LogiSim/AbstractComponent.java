package LogiSim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public abstract class AbstractComponent extends AbstractTile implements ILogicComponent {

    AbstractComponent(AbstractTile tile) {
        super(tile);
    }

    /**
     * A wire is being dragged to the component. It is up to the component to decide what
     * to do.
     */
    public abstract void processConnection(ILogicComponent source);

    public abstract int getRresource();

    public final Bitmap getComponentImage() {
        return BitmapFactory.decodeResource(grid.screenManager.appContext.getResources(), getRresource());
    }

    @Override
    void draw() {
        super.draw();
        debugText.addText("pos: " + grid.convertToScreenPoint(gridPoint));
        debugText.addText("size: " + grid.tileLength);
        drawComponentImage();
    }

    private void drawComponentImage() {
        Bitmap componentImage = getComponentImage();
        Rect orgRect = new Rect(0, 0, componentImage.getWidth(), componentImage.getWidth());
        Rect transformRect = new Rect(0, 0, grid.tileLength, grid.tileLength);
        transformRect.offsetTo(0, grid.tileLength / 4);
        debugText.addText("ipos:" + new ScreenPoint(transformRect.left, transformRect.top));
        debugText.addText("isize:" + new ScreenPoint(transformRect.width(), transformRect.height()));
        canvas.drawBitmap(componentImage, orgRect, transformRect, null);
    }
}
