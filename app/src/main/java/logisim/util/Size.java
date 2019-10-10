package logisim.util;

/**
 * Immutable class containing information about an object's length
 */
public class Size {
    public final int width;
    public final int height;

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "(" + width + ", " + height + ")";
    }
}
