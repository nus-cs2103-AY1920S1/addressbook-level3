package seedu.address.model.card;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Cards's CVC in the card book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCvc(String)}
 */
public class Cvc {

    public static final String MESSAGE_CONSTRAINTS =
            "CVC should only contain 3 digit numeric characters, and it should not be blank";

    public static final String VALIDATION_REGEX = "3[0-9]";

    public final String value;

    /**
     * Constructs a {@code Cvc}.
     *
     * @param cvc A valid cvc.
     */
    public Cvc(String cvc) {
        requireNonNull(cvc);
        checkArgument(isValidCvc(cvc), MESSAGE_CONSTRAINTS);
        this.value = cvc;
    }

    /**
     * Returns true if a given string is a valid CVC.
     */
    public static boolean isValidCvc(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Cvc // instanceof handles nulls
                && value.equals(((Cvc) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
