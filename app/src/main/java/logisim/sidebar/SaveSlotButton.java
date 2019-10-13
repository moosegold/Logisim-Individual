package logisim.sidebar;

import logisim.state.modes.SaveMode;
import logisim.state.states.ShowMessageState;
import logisim.util.LocalPoint;
import logisim.util.Size;

public class SaveSlotButton extends LabelSidebarButton {

    public SaveSlotButton(LocalPoint point, Size size, String label, ComponentSidebar sidebar) {
        super(point, size, label, sidebar);
    }

    @Override
    public void handleTap() {
        String feedback = "";
        if (sidebar.stateManager.getMode() instanceof SaveMode) {
            boolean result = sidebar.grid.saveGrid(label);
            feedback = result ? "Layout saved to " + label : "Failed to save layout";
        } else {
            boolean result = sidebar.grid.loadGrid(label);
            feedback = result ? "Loaded layout " + label : "Failed to load layout " + label;
        }
        sidebar.stateManager.setState(new ShowMessageState(feedback));
        sidebar.stateManager.resetMode();
    }
}
