package logisim.tiles.components;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import logisim.IInteractable;
import logisim.state.modes.ComponentInteractMode;
import logisim.tiles.IDraggable;
import logisim.util.LocalPoint;
import logisim.util.Paints;
import logisim.util.ScreenPoint;
import logisim.tiles.Tile;

public abstract class Component extends Tile implements ILogicComponent, IDraggable, IInteractable {

    public Component(Tile tile) {
        super(tile);
    }

    /**
     * A wire is being dragged to the component. It is up to the component to decide what
     * to do.
     */
    public abstract void processConnection(ILogicComponent source);

    public abstract int getRresource();

    public abstract boolean hasOutput();

    public abstract boolean hasInput();

    public final Bitmap getComponentImage() {
        return BitmapFactory.decodeResource(grid.screenManager.appContext.getResources(), getRresource());
    }

    public boolean isReplaceable() {
        return false;
    }

    @Override
    public void draw() {
        super.draw();
        debugText.addText("pos: " + grid.convertToLocalPoint(gridPoint));
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
        canvas.drawBitmap(componentImage, orgRect, transformRect, Paints.IMAGE_OPAQUE);
    }

    @Override
    public void onTouch() {
        grid.stateManager.setMode(new ComponentInteractMode(grid.stateManager, this, grid));
    }

    @Override
    public void onTap() {

    }

    @Override
    public IDraggable onDrag() {
        return this;
    }

    /**
     * Returns the point local to grid partition of the input that the wire should be
     * routed to.
     */
    public abstract LocalPoint getInputPosFor(Component component);

    /**
     * Returns the point local to the grid partition of the output that the wire should draw from
     */
    public LocalPoint getOutputPos() {
        return convertToGridSpace(new LocalPoint((int) ((128.0 / 150) * grid.tileLength), getRect().centerY()));
    }

    private LocalPoint convertToGridSpace(LocalPoint localTilePoint) {
        return new LocalPoint(localTilePoint.x + grid.tileLength * gridPoint.x, localTilePoint.y + grid.tileLength * gridPoint.y);
    }

}
