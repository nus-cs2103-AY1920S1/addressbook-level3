package seedu.ezwatchlist.model.show;

import static java.util.Objects.isNull;

/**
 * Represents a Show's name in the watchlist.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {
    public static final String DEFAULT_NAME = "na";
    public static final String MESSAGE_CONSTRAINTS =
            "Show names should only contain characters and spaces, and it should not be blank";

    /*
     * The first character of the show must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String showName;

    public Name() {
        showName = DEFAULT_NAME;
    }

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        if (isNull(name)) {
            name = DEFAULT_NAME;
        }
        showName = name;
    }

    public String getName() {
        return showName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return showName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && showName.equals(((Name) other).showName)); // state check
    }

    @Override
    public int hashCode() {
        return showName.hashCode();
    }

}
