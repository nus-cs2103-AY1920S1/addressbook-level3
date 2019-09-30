package seedu.address.model.itinerary;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Expenditure {
    public static final String MESSAGE_CONSTRAINTS = "Expenditure can take any positive numerical value with no more than 2 decimal places, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[+]?[0-9]+([.][0-9]{1,2})?$";

    public final Double value;

    /**
     * Constructs an {@code Expenditure}.
     *
     * @param value A valid expenditure.
     */
    public Expenditure(String value) {
        requireNonNull(value);
        checkArgument(isValidExpenditure(value), MESSAGE_CONSTRAINTS);
        this.value = Double.parseDouble(value);
    }

    public Expenditure(double value){
        requireNonNull(value);
        this.value = (double) Math.round(value * 100) / 100;
    }

    /**
     * Returns true if a given string is a valid expenditure.
     */
    public static boolean isValidExpenditure(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public static boolean isValidExpenditure(double test){
        return test > 0;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Expenditure // instanceof handles nulls
                && value.equals(((Expenditure) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


}
