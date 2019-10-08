package seedu.address.model.person;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Represents an Item's Reminder in ELISA.
 * Guarantees: immutable;
 */
public class Reminder {
    
    private final LocalDateTime dateTime;

    /**
     * Constructs a {@code Task}.
     *
     * @param dateTime A valid LocalDateTime object.
     */
    public Reminder(LocalDateTime dateTime) {
        requireNonNull(dateTime);
        this.dateTime = dateTime;
    }
    

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Reminder changeDateTime(LocalDateTime dateTime) {
        //When Reminder is implemented, the previous reminder notification should also be removed here
        return new Reminder(dateTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Reminder DateTime: ")
                .append(getDateTime().toString());
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Item)) {
            return false;
        }

        Reminder otherReminder = (Reminder) other;
        return otherReminder.getDateTime().equals(getDateTime());
    }

    //Possibility of high number of hash collisions and as a result slower performance
    @Override
    public int hashCode() {
        return Objects.hash(dateTime);
    }

}
