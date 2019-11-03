package seedu.sugarmummy.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.util.AppUtil.checkArgument;

/**
 * Represents a Blood Sugar Record's sugar concentration in the record book. Guarantees: immutable; is valid as declared
 * in {@link #isValidConcentration(String)}
 */
public class Concentration {

    private static final double CONCENTRATION_CONSTRAINT = 400;
    public static final String MESSAGE_CONSTRAINTS =
            "Concentration should only contain a positive real number "
                + "and must be less than " + CONCENTRATION_CONSTRAINT + " after rounding to 2 d.p.";
    public static final String VALIDATION_REGEX = "^+?\\d*\\.{0,1}\\d+$";
    private final double concentration;

    public Concentration(String concentration) {
        requireNonNull(concentration);
        checkArgument(isValidConcentration(concentration), MESSAGE_CONSTRAINTS);

        this.concentration = roundDouble(concentration);
    }

    public static boolean isValidConcentration(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        return roundDouble(test) < CONCENTRATION_CONSTRAINT;
    }

    private static double roundDouble(String test) {
        double doubleToRound = Double.parseDouble(test);
        return Math.round(doubleToRound * 100.0) / 100.0;
    }

    public double getConcentration() {
        return concentration;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Concentration // instanceof handles nulls
                && concentration == ((Concentration) other).concentration); // state check
    }

    @Override
    public int hashCode() {
        return ((Double) concentration).hashCode();
    }

    @Override
    public String toString() {
        return Double.toString(concentration);
    }
}
