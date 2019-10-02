package LogiSim;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ORGate extends CommutativeComponent {

    ORGate(AbstractTile tile) {
        super(tile);
    }

    public boolean eval() {
        return this.getInput(0) || this.getInput(1);
    }

    @Override
    public Bitmap getImage() {
        return BitmapFactory.decodeResource(screenManager.appContext.getResources(), R.drawable.or_gate);
    }
}
