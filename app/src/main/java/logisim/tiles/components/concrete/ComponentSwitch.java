package logisim.tiles.components.concrete;

import android.graphics.Canvas;

import java.util.Scanner;

import logisim.R;
import logisim.tiles.Tile;
import logisim.tiles.components.Component;
import logisim.util.LocalPoint;

public class ComponentSwitch extends Component {

    private boolean state;

    public ComponentSwitch(Tile tile) {
        super(tile);
    }

    @Override
    public String getName() {
        return "Switch";
    }

    @Override
    public void processConnection(Component source) {

    }

    @Override
    public void onTap() {
        this.state = !this.state;
    }

    @Override
    public boolean eval() {
        return state;
    }

    @Override
    public void drawWires(Canvas canvas) {

    }

    @Override
    public String getStorageID() {
        return "switch";
    }

    @Override
    public String getAdditionalStorageData() {
        return " " + state;
    }

    @Override
    public void loadAdditionalStorageData(Scanner scanner) {
        this.state = scanner.nextBoolean();
    }

    @Override
    public int getRresource() {
        return state ? R.drawable.switch_on : R.drawable.switch_off;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public boolean hasOutput() {
        return true;
    }

    /**
     * Returns the point local to grid partition of the input that the wire should be
     * routed to.
     */
    public LocalPoint getInputPosFor(Component component) {
        return null;
    }

}
