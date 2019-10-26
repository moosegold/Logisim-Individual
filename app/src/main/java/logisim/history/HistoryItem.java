package logisim.history;

class HistoryItem {

    UndoProcedure procedure;

    HistoryItem prevItem = null;
    HistoryItem nextItem = null;

    HistoryItem(UndoProcedure procedure) {
        this.procedure = procedure;
    }

}
