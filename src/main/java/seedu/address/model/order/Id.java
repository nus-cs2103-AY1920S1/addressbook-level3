package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Order's ID in the SML.
 * Guarantees: immutable; is valid as declared in {@link #isValidId(String)}
 */
public class Id {

    public static final String MESSAGE_CONSTRAINTS = "Order ID can take any unique values, and it should not be blank";

    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String id;

    /**
     * Constructs an {@code ID}.
     *
     * @param id A valid order ID.
     */
    public Id(String id) {
        requireNonNull(id);
        checkArgument(isValidId(id), MESSAGE_CONSTRAINTS);
        this.id = id;
    }

    /**
     * Returns true if a given string is a valid order ID.
     */
    public static boolean isValidId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Id // instanceof handles nulls
                && id.equals(((Id) other).id)); // state check
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
