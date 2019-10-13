package logisim.sidebar;

import logisim.state.modes.SaveMode;
import logisim.state.states.ShowMessageState;
import logisim.util.LocalPoint;
import logisim.util.Size;

public class SaveButton extends LabelSidebarButton {

    public SaveButton(LocalPoint point, Size size, String label, ComponentSidebar sidebar) {
        super(point, size, label, sidebar);
    }

    @Override
    public void handleTap() {
        if (!(sidebar.stateManager.getMode() instanceof SaveMode)) {
            sidebar.stateManager.setState(new ShowMessageState("Select A Save Slot"));
            sidebar.stateManager.setMode(new SaveMode(sidebar.stateManager));
        } else {
            sidebar.stateManager.resetMode();
        }
    }
}
