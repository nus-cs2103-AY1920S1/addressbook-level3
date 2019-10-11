package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Amount {


    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should only contain numbers, and it should be at least 3 digits long";
    //public static final String VALIDATION_REGEX = "\\d{3,}";
    public final double value;

    /**
     * Constructs an {@code Amount}.
     *
     * @param amount A valid phone number.
     */
    public Amount(double amount) {
        requireNonNull(amount);
        //checkArgument(isValidPhone(amount), MESSAGE_CONSTRAINTS);
        value = amount;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    //public static boolean isValidAmount(int test) {
    //    return test.matches(VALIDATION_REGEX);
    //}

    @Override
    public String toString() {
        return value + "";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount) // instanceof handles nulls
                && value == ((Amount) other).value; // state check
    }

}
