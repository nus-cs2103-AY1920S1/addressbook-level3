package seedu.sugarmummy.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.util.AppUtil.checkArgument;

/**
 * Represents a Bmi Record's weight in the record book. Guarantees: immutable; is valid as declared in {@link
 * #isValidWeight(String)}
 */
public class Weight {

    private static final double WEIGHT_CONSTRAINT = 500;
    public static final String MESSAGE_CONSTRAINTS =
            "Weight should only contain a positive real number "
                + "and must be less than " + WEIGHT_CONSTRAINT + " after rounding to 2 d.p.";
    public static final String VALIDATION_REGEX = "^+?\\d*\\.{0,1}\\d+$";
    private final double weight;

    public Weight(String weight) {
        requireNonNull(weight);
        checkArgument(isValidWeight(weight), MESSAGE_CONSTRAINTS);

        this.weight = roundDouble(weight);
    }

    public static boolean isValidWeight(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        return roundDouble(test) < WEIGHT_CONSTRAINT;
    }

    private static double roundDouble(String test) {
        double doubleToRound = Double.parseDouble(test);
        return Math.round(doubleToRound * 100.0) / 100.0;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Weight // instanceof handles nulls
                && weight == ((Weight) other).weight); // state check
    }

    @Override
    public int hashCode() {
        return ((Double) weight).hashCode();
    }

    @Override
    public String toString() {
        return Double.toString(weight);
    }
}
