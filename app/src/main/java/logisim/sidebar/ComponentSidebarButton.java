package logisim.sidebar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;

import java.lang.reflect.Constructor;

import logisim.AbstractScreenPartition;
import logisim.Grid;
import logisim.util.GridPoint;
import logisim.util.Paints;
import logisim.util.ScreenPoint;
import logisim.util.Size;
import logisim.tiles.Tile;
import logisim.tiles.components.Component;
import logisim.util.TextDrawUtil;

public class ComponentSidebarButton extends SidebarButton {

    private final int Rresouce;

    private Constructor componentConstructor;

    public ComponentSidebarButton(ScreenPoint point, int length, String componentName, int Rresource, AbstractScreenPartition partition, Class<? extends Component> representedComponent) {
        super(point, new Size(length, length), componentName, partition);
        this.Rresouce = Rresource;
        try {
            componentConstructor = representedComponent.getDeclaredConstructor(Tile.class);
        } catch (Exception ex) {
            System.out.println("Unable to get constructor for component: " + ex.getLocalizedMessage());
            System.out.println("The app will now close, as it will not work properly.");
            System.exit(1);
        }
    }

    @Override
    public void draw() {
        super.draw();
        drawComponentImage();
    }

    private Bitmap getComponentImage() {
        return BitmapFactory.decodeResource(partition.screenManager.appContext.getResources(), Rresouce);
    }

    @Override
    public void handleDragStart() {
        partition.screenManager.dragSourceButton = this;
        partition.screenManager.setDraggedObject(this.getComponentImage());
        partition.screenManager.setStatusBarText(this.label);
    }

    @Override
    public void handleTap() {
        // Do Nothing
    }

    @Override
    public void drawLabel() {
        Paint paint = Paints.LABEL_TEXT;
        int xPos = getLocalCenter().x - TextDrawUtil.getTextWidthPx(label, paint) / 2;
        int yPos = size.height - TextDrawUtil.getTextHeightPx(label, paint);
        debugText.addText("labelPos: " + new ScreenPoint(xPos, yPos));
        canvas.drawText(label, xPos, yPos, paint);
    }

    public void createNewComponent(GridPoint gridPoint, Grid grid) {
        Tile existingTile = grid.getTile(gridPoint);
        try {
            if (existingTile != null) {
                grid.setTile(gridPoint, (Tile) componentConstructor.newInstance(existingTile));
            }
        } catch (Exception ex) {
            System.out.println("Caught exception creating component: " + ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    private void drawComponentImage() {
        Rect orgImgRect = new Rect(0, 0, getComponentImage().getWidth(), getComponentImage().getWidth());
        Rect transformImgRect = new Rect(0, 0, size.width, size.height);
        transformImgRect.offsetTo(0, size.height / 4);
        debugText.addText("Img size: " + new Size(transformImgRect.width(), transformImgRect.height()));
        debugText.addText("Img at: " + new ScreenPoint(transformImgRect.left, transformImgRect.top));
        canvas.drawBitmap(getComponentImage(), orgImgRect, transformImgRect, Paints.IMAGE_OPAQUE);
    }

}
