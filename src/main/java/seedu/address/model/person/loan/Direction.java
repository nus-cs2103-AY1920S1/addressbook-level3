package seedu.address.model.person.loan;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the Direction (OUT/IN) of a Loan.
 * Guarantees: immutable; is valid as declared in {@link #isValidDirection(String)}
 */
public class Direction {

    public static final String MESSAGE_CONSTRAINTS =
            "Direction of a Loan can only be OUT or IN (case-sensitive).";

    public final String direction;

    /**
     * Constructs a {@code direction}.
     * @param direction A valid direction.
     */
    public Direction(String direction) {
        requireNonNull(direction);
        checkArgument(isValidDirection(direction), MESSAGE_CONSTRAINTS);
        this.direction = direction;
    }

    /**
     * Returns true if the given string is a valid direction.
     * @param testDirection The string to test for validity.
     * @return True if the string is "OUT" or "IN", false otherwise.
     */
    public static boolean isValidDirection(String testDirection) {
        return testDirection.equals("OUT") || testDirection.equals("IN");
    }

    @Override
    public String toString() {
        return direction;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Direction)) {
            return false;
        }

        Direction otherDirection = (Direction) other;
        return direction.equals(otherDirection.direction);
    }

    @Override
    public int hashCode() {
        return direction.hashCode();
    }
}
