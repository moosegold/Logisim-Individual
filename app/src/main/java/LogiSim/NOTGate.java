package LogiSim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

class NOTGate extends UnaryComponent {

    NOTGate(AbstractTile tile) {
        super(tile);
    }

    @Override
    public boolean eval() {
        return !this.getInput();
    }

    @Override
    public Bitmap getImage() {
        return BitmapFactory.decodeResource(screenManager.appContext.getResources(), R.drawable.not_gate);
    }

}
