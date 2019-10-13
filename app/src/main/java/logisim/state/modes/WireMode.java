package logisim.state.modes;

import android.graphics.Paint;

import logisim.Grid;
import logisim.state.StateManager;
import logisim.tiles.Tile;
import logisim.tiles.components.Component;
import logisim.util.Paints;
import logisim.util.ScreenPoint;
import logisim.util.Util;

public class WireMode extends AbstractMode {

    Grid grid;

    public WireMode(StateManager stateManager, Grid grid) {
        super(stateManager);
        this.grid = grid;
    }

    @Override
    public void updateDrag(ScreenPoint screenPoint) {

    }

    @Override
    public void processTap(Object touchedObject) {
        stateManager.resetMode();
    }

    @Override
    public void processDrag(Object dest) {
        if (dest instanceof Component) {
            Component component = (Component) dest;
            if (isLoopSafe((Component) stateManager.getDraggedObject(), (Tile) dest))
                component.processConnection((Component) stateManager.getDraggedObject());
        }
        stateManager.resetMode();
    }

    // There is currently a bug here. It is possible to create a loop through a series
    // of difficult to reproduce steps, but I don't have time to come up with a fix.
    // This will keep the app out of that state for most situations.
    // If a loop occurs, the app will crash as soon as a LED is connected and evaluated.
    private boolean isLoopSafe(Tile source, Tile dest) {
        for (Tile input : source.getInputs()) {
            if (input.equals(dest))
                return false;
            else
                return isLoopSafe(input, dest);
        }
        return true;
    }

    @Override
    public void draw() {
        drawLine();
        drawSelection();
    }

    private void drawLine() {
        stateManager.debugText.addText("Source: " + stateManager.getTouchedObjectStart());
        Component source = (Component) stateManager.getTouchedObjectStart();
        ScreenPoint start = grid.convertToScreenPoint(source.getOutputPos());
        ScreenPoint end = stateManager.getDragPoint();
        stateManager.debugText.addText("Start: " + start);
        stateManager.debugText.addText("End: " + end);
        stateManager.canvas.drawLine(start.x, start.y, end.x, end.y, Paints.WIRE);
    }

    private void drawSelection() {
        Util.drawTileOutline((Tile) stateManager.getTouchedObjectStart(), grid, stateManager.canvas, Paints.TILE_OUTLINE_SOURCE);
        ScreenPoint screenPoint = stateManager.getDragPoint();
        if (grid.containsTouch(screenPoint)) {
            Tile hoverTile = grid.getTile(grid.convertToGridPoint(screenPoint));
            if (hoverTile instanceof Component) {
                Paint paint = isLoopSafe((Tile) stateManager.getTouchedObjectStart(), hoverTile) && hoverTile.canAcceptWire() ? Paints.TILE_OUTLINE_ALLOW_PLACE : Paints.TILE_OUTLINE_DENY_PLACE;
                Util.drawTileOutline(grid.getTile(grid.convertToGridPoint(screenPoint)), grid, stateManager.canvas, paint);
            }
        }
    }

    @Override
    public void finalizeMode() {

    }
}
