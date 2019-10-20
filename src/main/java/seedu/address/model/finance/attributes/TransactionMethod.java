package seedu.address.model.finance.attributes;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the transaction method of an log entry in the finance log.
 */
public class TransactionMethod {

    public static final String MESSAGE_CONSTRAINTS = "Transaction methods should be in characters,"
            + " and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\D].*";

    public final String value;

    /**
     * Constructs an {@code TransactionMethod}.
     *
     * @param tMethod A valid transaction method.
     */
    public TransactionMethod(String tMethod) {
        requireNonNull(tMethod);
        checkArgument(isValidTransactionMet(tMethod), MESSAGE_CONSTRAINTS);
        value = tMethod;
    }

    /**
     * Returns true if a given string is a valid transaction method.
     */
    public static boolean isValidTransactionMet(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionMethod // instanceof handles nulls
                && value.equals(((TransactionMethod) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
