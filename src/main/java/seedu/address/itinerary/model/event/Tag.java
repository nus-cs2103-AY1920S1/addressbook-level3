package seedu.address.itinerary.model.event;

import static java.util.Objects.requireNonNull;

/**
 * Tag of the event in the itinerary.
 */
public class Tag {
    public static final String MESSAGE_CONSTRAINTS =
            "Tag should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the country must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "(.{0,20})";
    public final String tag;

    public Tag(String tag) {
        requireNonNull(tag);
        this.tag = tag;
    }

    /**
     * Returns true if a given string is a valid tag.
     */
    public static boolean isValidTag(String tag) {
        return tag.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return tag;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && tag.equals(((Tag) other).tag)); // state check
    }
}
