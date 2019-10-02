package LogiSim;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class ComponentSidebar extends AbstractScreenPartition {

    final Paint backgroundColor = new Paint();

    ComponentSidebar(ScreenPoint origin, Size size) {
        super(origin, size);
        setupPaints();
    }

    @Override
    public void draw() {
        fillBackground();
    }

    public void processTouch(ScreenPoint localPoint) {

    }

    private void setupPaints() {
        backgroundColor.setColor(Color.GRAY);
    }

    public void fillBackground() {
        canvas.drawRect(new Rect(0, 0, getSize().width, getSize().height), backgroundColor);
    }

}
