package seedu.address.model.activity;

import static java.util.Objects.requireNonNull;

import java.time.LocalTime;

import seedu.address.model.field.Name;

/**
 * Class to contain an activity Name and the timing to be scheduled.
 * @@author oscarsu97
 */
public class NameWithTime {
    public static final String MESSAGE_CONSTRAINTS =
            "Value of priority should be a non-zero positive integer";

    public final Name name;
    public final LocalTime time;

    public NameWithTime(Name name, LocalTime time) {
        requireNonNull(name);
        this.name = name;
        this.time = time;
    }

    public Name getName() {
        return name;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return name + time.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameWithTime // instanceof handles nulls
                && name.equals(((NameWithTime) other).name)
                && time.equals(((NameWithTime) other).time)); // state check
    }

    @Override
    public int hashCode() {
        return time != null
                ? name.hashCode() + time.hashCode()
                : name.hashCode();
    }
}
