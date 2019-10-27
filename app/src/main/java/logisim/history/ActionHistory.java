package logisim.history;

import java.util.LinkedList;
import java.util.List;

import logisim.state.StateManager;

public class ActionHistory {

    private final StateManager stateManager;

    private AbstractHistoryItem currentItem;

    public ActionHistory(StateManager stateManager) {
        this.stateManager = stateManager;
        currentItem = new StackFloorDummyItem(stateManager);
    }


    public void undo() {
        if (currentItem != null) {
            currentItem.performUndo();
            if (currentItem.hasPrevItem())
                currentItem = currentItem.getPrevItem();
        } else {
            stateManager.setStatusBarText("Nothing to undo");
        }
        stateManager.screenManager.draw();
    }

    public void redo() {
        if (currentItem.hasNextItem())
            currentItem = currentItem.getNextItem();
        currentItem.performRedo();
        stateManager.screenManager.draw();
    }

    public void pushAction(String action, UndoProcedure procedure) {
        AbstractHistoryItem newItem = new HistoryItem(procedure);
        newItem.action = action;
        if (currentItem != null)
            currentItem.setNextItem(newItem);
        newItem.setPrevItem(currentItem);
        currentItem = newItem;
    }

    public void addDebugInformation() {
        stateManager.debugText.addText("History");
        stateManager.debugText.addText("-------------");
        for (AbstractHistoryItem item : getHistoryList()) {
            stateManager.debugText.addText(item.toString() + (item == currentItem ? " <-- |" : "     |"));
        }
    }

    private List<AbstractHistoryItem> getHistoryList() {
        LinkedList<AbstractHistoryItem> items = new LinkedList<>();
        AbstractHistoryItem look = currentItem;
        if (look != null) {
            while (look.hasNextItem()) {
                look = look.getNextItem();
            }
            while (look.hasPrevItem()) {
                items.add(look);
                look = look.getPrevItem();
            }
        }
        return items;
    }

}
