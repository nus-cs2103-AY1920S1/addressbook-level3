package seedu.address.model.calendar;

import java.util.Optional;

/**
 * Represents an Event in the calendar.
 */
public class Event extends CalendarEntry {
    private Optional<DateTime> endingDateTime;
    private Optional<Venue> venue;
    private Optional<Person> person;
    private Optional<Reminder> autoReminder;

    public Event(Description description, DateTime dateTime) {
        super(description, dateTime);
        endingDateTime = Optional.empty();
        venue = Optional.empty();
        person = Optional.empty();
        autoReminder = Optional.empty();
    }

    public void setVenue(Venue venue) {
        this.venue = Optional.of(venue);
    }

    public void setPerson(Person person) {
        this.person = Optional.of(person);
    }

    public void setAutoReminder(Reminder reminder) {
        this.autoReminder = Optional.of(reminder);
    }

    public Optional<DateTime> getEndingDateTime() {
        return endingDateTime;
    }

    public Optional<Venue> getVenue() {
        return venue;
    }

    public Optional<Person> getPerson() {
        return person;
    }

    public Optional<Reminder> getAutoReminder() {
        return autoReminder;
    }

    /**
     * Returns true if both events have the same description, dateTime, endingDateTime, venue and person.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getDescription().equals(getDescription())
                && otherEvent.getDateTime().equals(getDateTime())
                && otherEvent.getEndingDateTime().equals(getEndingDateTime())
                && otherEvent.getPerson().equals(getPerson())
                && otherEvent.getVenue().equals(getVenue());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Event")
                .append(" Description: ")
                .append(getDescription())
                .append(" From: ")
                .append(getDateTime())
                .append(getEndingTimeString())
                .append(getVenueString())
                .append(getPersonString());
        return builder.toString();
    }

    private String getEndingTimeString() {
        if (endingDateTime.isPresent()) {
            return " To: " + endingDateTime.get();
        } else {
            return "";
        }
    }

    private String getVenueString() {
        if (venue.isPresent()) {
            return " at: " + venue.get();
        } else {
            return "";
        }
    }

    private String getPersonString() {
        if (person.isPresent()) {
            return " with: " + person.get();
        } else {
            return "";
        }
    }
}
