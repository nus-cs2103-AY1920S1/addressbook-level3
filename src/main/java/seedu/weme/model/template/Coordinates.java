package seedu.weme.model.template;

import java.util.Objects;

/**
 * Represents a pair of coordinates.
 */
public class Coordinates {

    private final float x;
    private final float y;

    public Coordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Coordinates)) {
            return false;
        }

        Coordinates otherCoorinates = (Coordinates) other;
        return x == otherCoorinates.x
            && y == otherCoorinates.y;
    }

}
