package logisim.history;

import java.util.LinkedList;
import java.util.List;

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

    public void pushAction(String action, UndoProcedure procedure) {
        HistoryItem newItem = new HistoryItem(procedure);
        newItem.action = action;
        if (currentItem != null)
            currentItem.nextItem = newItem;
        newItem.prevItem = currentItem;
        currentItem = newItem;
    }

    public void addDebugInformation() {
        stateManager.debugText.addText("History");
        stateManager.debugText.addText("-------------");
        for (HistoryItem item : getHistoryList()) {
            stateManager.debugText.addText(item.toString() + (item == currentItem ? " <-- |" : "     |"));
        }
    }

    private List<HistoryItem> getHistoryList() {
        LinkedList<HistoryItem> items = new LinkedList<>();
        HistoryItem look = currentItem;
        if (look != null) {
            while (look.nextItem != null) {
                look = look.nextItem;
            }
            while (look != null) {
                items.add(look);
                look = look.prevItem;
            }
        }
        return items;
    }

}
