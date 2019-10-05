package LogiSim;

import android.graphics.Bitmap;
import android.graphics.Rect;

public abstract class AbstractComponent extends AbstractTile {

    AbstractComponent(AbstractTile tile) {
        super(tile);
    }

    /**
     * A wire is being dragged to the component. It is up to the component to decide what
     * to do.
     */
    public abstract void processConnection(ILogicComponent source);

    public abstract Bitmap getComponentImage();

    @Override
    void draw() {
        super.draw();
        debugText.addText("pos: " + grid.convertToScreenPoint(gridPoint));
        debugText.addText("size: " + grid.tileLength);
        drawComponentImage();
    }

    private void drawComponentImage() {
//        Rect orgImgRect = new Rect(0, 0, getImage().getWidth(), getImage().getWidth());
//        Rect transformImgRect = new Rect(0, 0, length, length);
//        transformImgRect.offsetTo(0, length / 4);
        Bitmap componentImage = getComponentImage();
        Rect orgRect = new Rect(0, 0, componentImage.getWidth(), componentImage.getWidth());
        Rect transformRect = new Rect(0, 0, grid.tileLength, grid.tileLength);
        transformRect.offsetTo(0, grid.tileLength / 4);
        debugText.addText("ipos:" + new ScreenPoint(transformRect.left, transformRect.top));
        debugText.addText("isize:" + new ScreenPoint(transformRect.width(), transformRect.height()));
        canvas.drawBitmap(componentImage, orgRect, transformRect, null);
    }

}
