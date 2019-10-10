package LogiSim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.lang.reflect.Constructor;

public class ComponentSidebarButton extends SidebarButton {

    final int Rresouce;

    Constructor componentConstructor;

    public ComponentSidebarButton(ScreenPoint point, int length, String action, int Rresource, AbstractScreenPartition partition, Class<? extends AbstractComponent> representation) {
        super(point, new Size(length, length), action, partition);
        this.Rresouce = Rresource;
        //this.representation = representation;
        try {
            componentConstructor = representation.getDeclaredConstructor(AbstractTile.class);
        } catch (Exception ex) {
            System.out.println("Unable to create new component: " + ex.getLocalizedMessage());
            ex.printStackTrace();
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
    public Bitmap getDragImage() {
        return getComponentImage();
    }

    public void createNewComponent(GridPoint gridPoint, Grid grid) {
        AbstractTile existingTile = grid.getTile(gridPoint);
        try {
            if (existingTile != null) {
                grid.setTile(gridPoint, (AbstractTile) componentConstructor.newInstance(existingTile));
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
        canvas.drawBitmap(getComponentImage(), orgImgRect, transformImgRect, null);
    }

}
