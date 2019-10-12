package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the Amount in a Transaction.
 * Guarantees: immutable, is valid as declared in {@link #isValidAmount(long)}
 */
public class Amount {

    public final String MESSAGE_CONSTRAINTS =
            "Amounts should be non-negative, and should not be blank.";

    public final String VALIDATION_REGEX = "^\\d+$";

    public final long amount;

    /**
     * Constructs an {@code Amount}
     *
     * @param amount a valid amount.
     */
    public Amount(long amount) {
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        this.amount = amount;
    }

    /**
     * Returns true if a given long is a valid Amount.
     */
    public static boolean isValidAmount(long test) {
        return test >= 0;
    }

    @Override
    public String toString(){
        return Long.toString(amount);
    }

    @Override
    public boolean equals(Object other){
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && amount == ((Amount) other).amount);
    }

    @Override
    public int hashCode(){
        return Long.hashCode(amount);
    }
}
