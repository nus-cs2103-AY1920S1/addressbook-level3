package thrift.model.transaction;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Transaction's message description in the Transactions list.
 * Guarantees: immutable.
 */
public class Description {

    public final String value;

    /**
     * Constructs a {@code Description}.
     *
     * @param description Description describing the Transaction.
     */
    public Description(String description) {
        requireNonNull(description);
        value = description;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && value.equals(((Description) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
