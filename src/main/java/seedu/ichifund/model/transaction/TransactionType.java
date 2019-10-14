package seedu.ichifund.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.util.AppUtil.checkArgument;

/**
 * Represents a Transaction's type in the transaction recorder.
 * Guarantees: immutable; is valid as declared in {@link #isValidCategory(String)}
 */
public class TransactionType {

    public static final String MESSAGE_CONSTRAINTS =
            "TransactionType can only be 'income' or 'expenditure'";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "income|expenditure";

    public final String transactionType;

    /**
     * Constructs a {@code Category}.
     *
     * @param transactionType A valid transaction type.
     */
    public TransactionType(String transactionType) {
        requireNonNull(transactionType);
        checkArgument(isValidTransactionType(transactionType), MESSAGE_CONSTRAINTS);
        this.transactionType = transactionType;
    }

    /**
     * Returns true if a given string is a valid category.
     */
    public static boolean isValidTransactionType(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public boolean isExpenditure() {
        return transactionType.equals("expenditure");
    }

    @Override
    public String toString() {
        return transactionType;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionType // instanceof handles nulls
                && transactionType.equals(((TransactionType) other).transactionType)); // state check
    }

    @Override
    public int hashCode() {
        return transactionType.hashCode();
    }
}

