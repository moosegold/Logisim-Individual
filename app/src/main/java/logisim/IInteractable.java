package logisim;

import logisim.tiles.IDraggable;

public interface IInteractable {

    void onTap();

    IDraggable onDrag();

}
