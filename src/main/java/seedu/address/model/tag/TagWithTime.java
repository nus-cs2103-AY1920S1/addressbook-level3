package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;

import java.time.LocalTime;

import seedu.address.model.activity.NameWithTime;

/**
 * Class to contain a Tag class and the timing to be scheduled.
 * @@author oscarsu97
 */
public class TagWithTime {
    public static final String MESSAGE_CONSTRAINTS =
            "Value of priority should be a non-zero positive integer";

    public final Tag tag;
    public final LocalTime time;

    public TagWithTime(Tag tag, LocalTime time) {
        requireNonNull(tag);
        this.tag = tag;
        this.time = time;
    }

    public Tag getTag() {
        return tag;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return tag + time.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameWithTime // instanceof handles nulls
                && tag.equals(((NameWithTime) other).name)
                && time.equals(((NameWithTime) other).time)); // state check
    }

    @Override
    public int hashCode() {
        return time != null
                ? tag.hashCode() + time.hashCode()
                : tag.hashCode();
    }
}
