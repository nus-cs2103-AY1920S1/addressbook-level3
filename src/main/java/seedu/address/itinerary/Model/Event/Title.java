package seedu.address.itinerary.model.Event;

import static java.util.Objects.requireNonNull;

/**
 * The title indicating the event in the itinerary.
 */
public class Title {
    public static final String MESSAGE_CONSTRAINTS =
            "Title should only contain any special characters and exceed 100 characters.\n"
            + "In addition, it should not be empty too.";

    public static final String VALIDATION_REGEX = "(.{1,100})";
    public final String title;

    public Title(String title) {
        requireNonNull(title);
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
}
