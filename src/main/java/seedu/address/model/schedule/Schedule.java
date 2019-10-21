package seedu.address.model.schedule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.Identifiable;
import seedu.address.model.tag.Tag;

/**
 * Represents a Schedule in the SML.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Schedule implements Identifiable<Schedule> {

    // Identity fields
    private final UUID id;

    // Data fields
    private final Calendar calendar;
    private final Venue venue;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Schedule(UUID id, Calendar calendar, Venue venue, Set<Tag> tags) {
        requireAllNonNull(id, calendar, venue, tags);
        this.id = id;
        this.calendar = calendar;
        this.venue = venue;
        this.tags.addAll(tags);
    }

    public UUID getId() {
        return id;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public Venue getVenue() {
        return venue;
    }

    /**
     * Return a formatted String of the Calendar Object.
     */
    public String getCalendarString() {
        StringBuilder sb = new StringBuilder();
        // month to be changed back to 1-based
        sb.append(calendar.get(Calendar.YEAR) + ".")
                .append(String.format("%02d", calendar.get(Calendar.MONTH) + 1) + ".")
                .append(String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + " ")
                .append(String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ":")
                .append(String.format("%02d", calendar.get(Calendar.MINUTE)));
        return sb.toString();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both schedules have the same identity and data fields.
     * This defines a stronger notion of equality between two schedules.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Schedule)) {
            return false;
        }

        Schedule otherSchedule = (Schedule) other;
        return otherSchedule.getId().equals(getId())
                && otherSchedule.getCalendar().equals(getCalendar())
                && otherSchedule.getVenue().equals(getVenue())
                && otherSchedule.getTags().equals((getTags()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(id, calendar, venue, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" # ")
                .append(getId())
                .append(" Date and Time: ")
                .append(getCalendarString())
                .append(" Venue: ")
                .append(getVenue())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    @Override
    public boolean isSameAs(Schedule other) {
        if (other == this) {
            return true;
        }

        return other != null
                && other.getId().equals(getId());
    }

}
