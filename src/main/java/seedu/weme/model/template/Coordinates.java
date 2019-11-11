package seedu.weme.model.template;

import java.util.Objects;

import seedu.weme.model.template.exceptions.InvalidCoordinatesException;

/**
 * Represents a pair of coordinates.
 */
public class Coordinates {

    public static final String MESSAGE_INVALID_COORDINATES = "Coordinates must be numbers between 0 and 1 inclusive";

    private final float x;
    private final float y;

    public Coordinates(float x, float y) throws InvalidCoordinatesException {
        if (x < 0 || x > 1 || y < 0 || y > 1) {
            throw new InvalidCoordinatesException();
        }
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

        Coordinates otherCoordinates = (Coordinates) other;
        return x == otherCoordinates.x
            && y == otherCoordinates.y;
    }

}
