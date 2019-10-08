package seedu.address.model.claim;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Claim's claim amount in Contact.
 */
public class Amount {

    public static final String MESSAGE_CONSTRAINTS =
            "Claim amount should only contain numbers, and has a maximum decimal places of 2";
    public static final String VALIDATION_REGEX = "\\d+.\\d\\d";
    public final String value;

    /**
     * Constructs an {@code Amount}.
     *
     * @param amount A valid claim amount.
     */
    public Amount(String amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        value = amount;
    }

    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof Amount // instanceof handles nulls
                && value.equals(((Amount) obj).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
