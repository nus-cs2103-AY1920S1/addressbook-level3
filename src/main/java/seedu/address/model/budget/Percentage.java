package seedu.address.model.budget;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * dummy.
 */
public class Percentage {
    private final int proportion;
    public static final String MESSAGE_CONSTRAINTS =
            "Percentage should be a non-negative integer less than or equal to 100.";

    public Percentage(int proportion) {
        checkArgument(isValidPercentage(proportion), MESSAGE_CONSTRAINTS);
        this.proportion = proportion;
    }

    public int getProportion() {
        return proportion;
    }

    public static Percentage calculate(double num, double den) throws IllegalArgumentException {
        if (num > den) {
            throw new IllegalArgumentException("Numerator cannot be larger than denominator");
        } else if (num * den < 0) {
            throw new IllegalArgumentException("Numerator and denominator must have same sign");
        }
        return new Percentage ((int)Math.round(num / den * 100));
    }

    public boolean reach(Percentage threshold) {
        return getProportion() >= threshold.getProportion();
    }

    /**
     * Returns true if a given int is a valid percentage.
     */
    public static boolean isValidPercentage(int input) {
        return input >= 0 && input <= 100;
    }

    public static int getProportionFromString(String string) {
        requireAllNonNull(string);
        String proportionString = string.substring(0, string.length() - 1);
        return Integer.parseInt(proportionString);
    }

    @Override
    public String toString() {
        return proportion + "%";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Percentage)) {
            return false;
        }

        Percentage otherPercentage = (Percentage) other;
        return this.proportion == otherPercentage.proportion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(proportion);
    }
}
