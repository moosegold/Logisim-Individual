package logisim.history;

import logisim.state.StateManager;

public class ActionHistory {

    private final StateManager stateManager;

    public ActionHistory(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    private HistoryItem currentItem;

    public void undo() {
        if (currentItem != null) {
            currentItem.procedure.performUndo();
            currentItem = currentItem.prevItem;
        } else {
            stateManager.setStatusBarText("Nothing to undo");
        }
    }

    public void redo() {
        if (currentItem != null) {
            currentItem.procedure.performRedo();
            currentItem = currentItem.prevItem;
        } else {
            stateManager.setStatusBarText("Nothing to redo");
        }
    }

    public void pushAction(UndoProcedure procedure) {
        HistoryItem newItem = new HistoryItem(procedure);
        currentItem.nextItem = newItem;
        currentItem = newItem;
    }

}
