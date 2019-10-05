package LogiSim;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ANDGate extends CommutativeComponent {

    ANDGate(AbstractTile tile) {
        super(tile);
    }

    public boolean eval() {
        return this.getInput(0) && this.getInput(1);
    }

    public Bitmap getComponentImage() {
        return BitmapFactory.decodeResource(grid.screenManager.appContext.getResources(), R.drawable.and_gate);
    }

}
