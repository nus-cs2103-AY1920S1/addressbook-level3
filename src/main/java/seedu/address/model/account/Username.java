package seedu.address.model.account;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a User's username.
 * Guarantees: immutable; is valid as declared in {@link #isValidUsername(String)}
 */
public class Username {

    public static final String MESSAGE_CONSTRAINTS =
            "Username should only contain alphanumeric characters "
                    + "with at least 5 characters, and it should not be blank";

    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9._-]{5,}$";

    public final String userId;

    /**
     * Constructs a {@code Username}.
     *
     * @param id A valid username.
     */
    public Username(String id) {
        requireNonNull(id);
        checkArgument(isValidUsername(id), MESSAGE_CONSTRAINTS);
        userId = id;
    }

    /**
     * Returns true if a given string is a valid amount.
     */
    public static boolean isValidUsername(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public Username lowerCase() {
        return new Username(userId.toLowerCase());
    }

    @Override
    public String toString() {
        return userId;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Username // instanceof handles nulls
                && userId.equals(((Username) other).userId)); // state check
    }
}
