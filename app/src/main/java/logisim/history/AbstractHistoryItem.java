package logisim.history;

import androidx.annotation.NonNull;

abstract class AbstractHistoryItem {

    String action;

    abstract void performUndo();

    abstract void performRedo();

    abstract AbstractHistoryItem getPrevItem();

    abstract void setPrevItem(AbstractHistoryItem prevItem);

    abstract AbstractHistoryItem getNextItem();

    abstract void setNextItem(AbstractHistoryItem nextItem);

    abstract boolean hasPrevItem();

    abstract boolean hasNextItem();

    @NonNull
    @Override
    public String toString() {
        return action;
    }
}
