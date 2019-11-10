package seedu.address.itinerary.model.event;

import static java.util.Objects.requireNonNull;

/**
 * The title indicating the event in the itinerary.
 */
public class Title {
    public static final String MESSAGE_CONSTRAINTS =
            "Title should not exceed 70 characters.\n"
            + "In addition, it should not be empty too.";

    public static final String VALIDATION_REGEX = "(.{1,70})";
    public final String title;

    public Title(String title) {
        requireNonNull(title);
        assert isValidTitle(title);
        this.title = title;
    }

    /**
     * Returns true if a given string is a valid title for the event in the itinerary.
     */
    public static boolean isValidTitle(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && title.equals(((Title) other).title)); // state check
    }
}
