package logisim.tiles.components;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import logisim.IInteractable;
import logisim.state.modes.ComponentInteractMode;
import logisim.tiles.IDraggable;
import logisim.util.LocalPoint;
import logisim.util.Paints;
import logisim.tiles.Tile;
import logisim.util.Util;

public abstract class Component extends Tile implements IDraggable, IInteractable {

    public Component(Tile tile) {
        super(tile);
    }

    /**
     * A wire is being dragged to the component. It is up to the component to decide what
     * to do.
     */
    public abstract void processConnection(Component source);

    public abstract int getRresource();

    public abstract boolean hasOutput();

    public abstract boolean hasInput();

    public abstract void drawWires(Canvas canvas);

    /**
     * Get the result of the component, by evaluating a tree of connected inputs recursively,
     * as shown in Daryl Posnett's Logisim Evaluation Example.
     */
    public abstract boolean eval();

    public final Bitmap getComponentImage() {
        return BitmapFactory.decodeResource(grid.screenManager.appContext.getResources(), getRresource());
    }

    public boolean isReplaceable() {
        return false;
    }

    @Override
    public void draw() {
        super.draw();
        drawComponentImage();
    }

    private void drawComponentImage() {
        Bitmap componentImage = getComponentImage();
        Rect orgRect = new Rect(0, 0, componentImage.getWidth(), componentImage.getWidth());
        Rect transformRect = new Rect(0, 0, grid.tileLength, grid.tileLength);
        transformRect.offsetTo(0, grid.tileLength / 4);
        canvas.drawBitmap(componentImage, orgRect, transformRect, Paints.IMAGE_OPAQUE);
    }

    protected void drawWire(Canvas canvas, Component source, Component dest) {
        if (source != null && dest != null)
            Util.drawWire(canvas, source, dest);
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

    protected LocalPoint convertToGridSpace(LocalPoint localTilePoint) {
        return new LocalPoint(localTilePoint.x + grid.tileLength * gridPoint.x, localTilePoint.y + grid.tileLength * gridPoint.y);
    }

    @NonNull
    @Override
    public String toString() {
        return getName() + getPoint();
    }
}
