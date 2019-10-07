package LogiSim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class ComponentSidebarButton extends SidebarButton {

    final int Rresouce;

    public ComponentSidebarButton(ScreenPoint point, int length, String action, int Rresource, AbstractScreenPartition partition) {
        super(point, new Size(length, length), action, partition);
        this.Rresouce = Rresource;
    }

    @Override
    public void draw() {
        super.draw();
        drawComponentImage();
    }

    private Bitmap getComponentImage() {
        return BitmapFactory.decodeResource(partition.screenManager.appContext.getResources(), Rresouce);
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
