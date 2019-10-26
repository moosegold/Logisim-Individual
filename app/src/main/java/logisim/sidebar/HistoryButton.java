package logisim.sidebar;

import android.graphics.Paint;

import logisim.history.ActionHistory;
import logisim.util.LocalPoint;
import logisim.util.Paints;
import logisim.util.ScreenPoint;
import logisim.util.Size;

public abstract class HistoryButton extends CenterLabelButton {

    ActionHistory history;

    public HistoryButton(LocalPoint point, Size size, String label, ComponentSidebar sidebar, ActionHistory history) {
        super(point, size, label, sidebar);
        this.history = history;
    }

    @Override
    Paint getPaint() {
        return Paints.LABEL_TEXT;
    }

    @Override
    public void handleDragStart(ScreenPoint screenPoint) {
        // Do nothing
    }
}
