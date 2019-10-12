package seedu.address.model;


/**
 * Enum that represents the direction of money flow (IN/OUT).
 * Guarantees: immutable;
 */
public enum Direction {
    IN("IN"),
    OUT("OUT");

    public final String direction;

    /**
     * Constructs a {@code direction}.
     * @param direction The String representing the direction of money flow.
     */
    Direction(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return direction;
    }
}
