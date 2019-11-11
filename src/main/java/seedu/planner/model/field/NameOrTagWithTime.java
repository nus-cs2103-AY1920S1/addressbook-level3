package seedu.planner.model.field;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

import seedu.planner.model.tag.Tag;

//@@author oscarsu97

/**
 * Represents a Name or Tag with a timing tagged to it.
 */
public class NameOrTagWithTime {
    private final Name name;
    private final Tag tag;
    private final LocalTime time;

    public NameOrTagWithTime(Name name, LocalTime time) {
        this.name = name;
        this.time = time;
        this.tag = null;
    }

    public NameOrTagWithTime(Tag tag, LocalTime time) {
        this.tag = tag;
        this.time = time;
        this.name = null;
    }

    public Optional<Name> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<Tag> getTag() {
        return Optional.ofNullable(tag);
    }

    public Optional<LocalTime> getTime() {
        return Optional.ofNullable(time);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameOrTagWithTime // instanceof handles nulls
                && tag.equals(((NameOrTagWithTime) other).tag)
                && name.equals(((NameOrTagWithTime) other).name)
                && time.equals(((NameOrTagWithTime) other).time)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tag, time);
    }
}
