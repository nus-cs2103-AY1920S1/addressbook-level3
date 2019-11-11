package seedu.sugarmummy.model.records;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.util.AppUtil.checkArgument;

/**
 * Represents a Blood Sugar Record's sugar concentration in the record book. Guarantees: immutable; is valid as declared
 * in {@link #isValidConcentration(String)}
 */
public class Concentration {

    public static final String VALIDATION_REGEX = "^+?\\d*\\.{0,1}\\d+$";
    private static final double CONCENTRATION_CONSTRAINT = 33;
    public static final String MESSAGE_CONSTRAINTS =
            "Concentration should only contain a positive real number between 0 and "
                    + CONCENTRATION_CONSTRAINT + " exclusive after rounding to 2 d.p.";
    private final double concentration;

    public Concentration(String concentration) {
        requireNonNull(concentration);
        checkArgument(isValidConcentration(concentration), MESSAGE_CONSTRAINTS);

        this.concentration = roundDouble(concentration);
    }

    /**
     * Returns true if a given string is a valid concentration.
     * A concentration is considered valid if it complies with VALIDATION_REGEX and
     * it is less than the CONCENTRATION_CONSTRAINT after rounding.
     */
    public static boolean isValidConcentration(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        return roundDouble(test) > 0 && roundDouble(test) < CONCENTRATION_CONSTRAINT;
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
