package seedu.address.profile.records;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.logic.parser.DateParser;

/**
 * Represents the user's timestamp of birth.
 */
public class Timestamp {
    public static final String MESSAGE_CONSTRAINTS =
            "Timestamp should only contain numeric characters in the format of DD/MM/YYYY HH:mm, "
                    + "and it should not be blank";

    public final String timestamp;

    /**
     * Constructs a {@code timestamp}.
     *
     * @param timestamp A valid timestamp in String form.
     */
    public Timestamp(String timestamp) {
        requireNonNull(timestamp);
        checkArgument(isValidDateTime(timestamp), MESSAGE_CONSTRAINTS);
        this.timestamp = timestamp;
    }

    /**
     * Returns true if a given string is a valid timestamp.
     */
    public static boolean isValidDateTime(String timestamp) {
        return DateParser.isValidDateTime(timestamp);
    }

    @Override
    public String toString() {
        return timestamp;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Timestamp // instanceof handles nulls
                && timestamp.equals(((Timestamp) other).timestamp)); // state check
    }

    @Override
    public int hashCode() {
        return timestamp.hashCode();
    }

}
