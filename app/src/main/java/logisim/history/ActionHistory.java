package logisim.history;

import java.util.LinkedList;
import java.util.List;

import logisim.state.StateManager;

public class ActionHistory {

    private final StateManager stateManager;

    private StackTopDummyItem topItem;
    private AbstractHistoryItem currentItem;
    private StackFloorDummyItem bottomItem;

    public ActionHistory(StateManager stateManager) {
        this.stateManager = stateManager;
        init();

    }

    private void init() {
        topItem = new StackTopDummyItem(stateManager);
        bottomItem = new StackFloorDummyItem(stateManager);
        bottomItem.setNextItem(topItem);
        topItem.setPrevItem(bottomItem);
        currentItem = bottomItem;
    }

    public void undo() {
        if (currentItem == topItem)
            currentItem = topItem.getPrevItem();
        currentItem.performUndo();
        if (currentItem.hasPrevItem())
            currentItem = currentItem.getPrevItem();
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
        if (currentItem == topItem) {
            // We are at the top of the stack, in the StackTopDummyItem;
            currentItem = topItem.getPrevItem();
        }
        topItem.setPrevItem(newItem);
        newItem.setNextItem(topItem);
        newItem.setPrevItem(currentItem);
        currentItem.setNextItem(newItem);
        currentItem = newItem;
    }

    public void clear() {
        bottomItem.setNextItem(topItem  );
        topItem.setPrevItem(bottomItem);
        currentItem = bottomItem;
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
            items.add(look);
        }
        return items;
    }

}
