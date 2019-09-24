package seedu.address.model.expense;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Expense's unique identifier in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUniqueIdentifier(String)}
 */
public class UniqueIdentifier {


    public static final String MESSAGE_CONSTRAINTS =
            "A unique identifier should begin with the prefix \'Expense@\', followed by a 36 character"
            + " Universally Unique Identifier which consits of a sequence of up of hex digits (4 chars each)"
            + " and 4 “-” symbols. More specifically a sequence of <8 hex digits>, <4 hex digit>,"
            + " <4 hex digits>, <4 hex digits>, and <12 hex digits> with a dash in between each.";

    public static final String UNIQUE_IDENTIFIER_PREFIX = "Expense@";
    public static final String UUID_VALIDATION_REGEX = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}+-[0-9a-f]{4}-[0-9a-f]{12}";
    public static final String VALIDATION_REGEX = UNIQUE_IDENTIFIER_PREFIX + UUID_VALIDATION_REGEX;

    public final String value;

    /**
     * Constructs a {@code UniqueIdentifier}.
     *
     * @param uniqueIdentifier A valid unique identifier.
     */
    public UniqueIdentifier(String uniqueIdentifier) {
        requireNonNull(uniqueIdentifier);
        checkArgument(isValidUniqueIdentifier(uniqueIdentifier), MESSAGE_CONSTRAINTS);
        value = uniqueIdentifier;
    }

    /**
     * Returns true if a given string is a valid unique identifier.
     */
    public static boolean isValidUniqueIdentifier(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueIdentifier // instanceof handles nulls
                && value.equals(((UniqueIdentifier) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
