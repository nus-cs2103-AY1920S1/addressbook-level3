package budgetbuddy.model.attributes;

import java.util.Arrays;

/**
 * Enum that represents the direction of money flow (IN/OUT).
 * Guarantees: immutable;
 */
public enum Direction {
    IN("IN"),
    OUT("OUT");

    public static final String MESSAGE_CONSTRAINTS = "Direction can only be IN or OUT.";

    public final String direction;

    /**
     * Constructs a {@code direction}.
     * @param direction The String representing the direction of money flow.
     */
    Direction(String direction) {
        this.direction = direction;
    }

    /**
     * Returns true if a given string is a valid Direction value.
     * @param toTest The string to test.
     */
    public static boolean isValidDirection(String toTest) {
        return Arrays.stream(Direction.values())
                .map(Direction::toString)
                .anyMatch(directionStr -> directionStr.equals(toTest));
    }

    @Override
    public String toString() {
        return direction;
    }
}
