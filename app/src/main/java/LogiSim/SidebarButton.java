package LogiSim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class SidebarButton {

    final AbstractScreenPartition partition;

    final ScreenPoint point;
    final int size;
    final String action;
    final int Rresource;

    public SidebarButton(ScreenPoint point, int size, String action, int Rresource, AbstractScreenPartition partition) {
        this.point = point;
        this.size = size;
        this.action = action;
        this.Rresource = Rresource;

        this.partition = partition;
    }

    public void draw() {
        //partition.canvas.drawBitmap(getImage(), point.x, point.y, null);
        Rect orgImgRect = new Rect(point.x, point.y, getImage().getWidth(), getImage().getWidth());
        Rect transformImgRect = new Rect(point.x, point.y, point.x + size, point.y + size);

        partition.canvas.drawBitmap(getImage(), orgImgRect, transformImgRect, null);
    }

    private Bitmap getImage() {
        return BitmapFactory.decodeResource(partition.screenManager.appContext.getResources(), Rresource);
    }

}
