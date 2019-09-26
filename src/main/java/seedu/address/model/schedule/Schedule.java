package seedu.address.model.schedule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.order.Order;

/**
 * Represents a Schedule, with the Order, Date, Time and Location of the meetup.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Schedule {

    // Identity fields
    private final Order order;

    // Data fields
    private final DateTime dateTime;
    private final Venue venue;

    /**
     * Every field must be present and not null.
     */
    public Schedule(Order order, DateTime dateTime, Venue venue) {
        requireAllNonNull(order, dateTime, venue);
        this.order = order;
        this.dateTime = dateTime;
        this.venue = venue;
    }

    public Order getOrder() {
        return order;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public Venue getVenue() {
        return venue;
    }

    /**
     * Returns true if both schedules have the same order.
     * This defines a weaker notion of equality between two schedules.
     */
    public boolean isSameSchedule(Schedule otherSchedule) {
        if (otherSchedule == this) {
            return true;
        }

        return otherSchedule != null
                && otherSchedule.getOrder().equals(getOrder());
    }

    /**
     * Returns true if both persons have the same order and data fields.
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
        return otherSchedule.getOrder().equals(getOrder())
                && otherSchedule.getDateTime().equals(getDateTime())
                && otherSchedule.getVenue().equals(getVenue());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(order, dateTime, venue);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Order: ")
                .append(getOrder())
                .append(" Date and Time: ")
                .append(getDateTime())
                .append(" Venue: ")
                .append(getVenue());
        return builder.toString();
    }
}