package seedu.moolah.model.budget;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a percentage rounded to the nearest non-negative integer value.
 */
public class Percentage {
    public static final String MESSAGE_CONSTRAINTS =
            "Percentage should be a non-negative integer.";

    private final int value;

    /**
     * Constructs a Percentage object with a specified value.
     *
     * @param value A non-negative integer that represents the percentage.
     */
    public Percentage(int value) {
        checkArgument(isValidPercentage(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    /**
     * Calculates the percentage through double division.
     *
     * @param num The numerator.
     * @param den The denominator.
     * @return A Percentage representing the proportion of num over den, rounded to nearest integer.
     * @throws IllegalArgumentException If num and den have different signs.
     */
    public static Percentage calculate(double num, double den) throws IllegalArgumentException {
        if (num * den < 0) {
            throw new IllegalArgumentException("Numerator and denominator must have same sign");
        } else {
            return new Percentage((int) Math.round(num / den * 100));
        }
    }

    /**
     * Checks if the percentage has reached the specified threshold.
     *
     * @param threshold The specified threshold.
     * @return True if the percentage is larger or equal than the threshold. False otherwise.
     */
    public boolean reach(Percentage threshold) {
        requireNonNull(threshold);
        return this.getValue() >= threshold.getValue();
    }

    /**
     * Returns true if a given integer is a valid percentage.
     *
     * @param input The integer inputted.
     * @return True if the integer is non-negative, false otherwise.
     */
    public static boolean isValidPercentage(int input) {
        return input >= 0;
    }

    /**
     * Generates a string representation of this Percentage.
     *
     * @return A string that contains the value, followed by a percentage sign.
     */
    @Override
    public String toString() {
        return value + "%";
    }

    /**
     * Creates a hash code by hashing all relevant attributes of this Percentage object.
     *
     * @return A hash code of this Percentage object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }


    /**
     * Checks whether another object is identical to this Percentage.
     *
     * @param other The other object to be compared.
     * @return True if the other object is a Percentage with the same value, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Percentage)) {
            return false;
        }

        Percentage otherPercentage = (Percentage) other;
        return this.value == otherPercentage.value;
    }
}
