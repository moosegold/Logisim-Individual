package logisim.history;

import androidx.annotation.NonNull;

class HistoryItem {

    final UndoProcedure procedure;

    HistoryItem prevItem = null;
    HistoryItem nextItem = null;

    String action;

    HistoryItem(UndoProcedure procedure) {
        this.procedure = procedure;
    }

    @NonNull
    @Override
    public String toString() {
        return action;
    }
}
