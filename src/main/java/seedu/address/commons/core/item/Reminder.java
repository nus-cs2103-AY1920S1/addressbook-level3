package seedu.address.commons.core.item;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.databind.JsonNode;

import seedu.address.commons.util.JsonUtil;


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

    /**
     * Changes the dateTime that the reminder occurs. Removes the previous reminder so it does not occur.
     * @param dateTime A LocalDateTime object which dictates the dateTime the reminder occurs.
     * @return A new Reminder with the new dateTime for the reminder.
     */
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

        if (!(other instanceof Reminder)) {
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

    /**
     * Creates a reminder object from a JSON string.
     * @param jsonString the JSON string that represents the reminder
     * @return the reminder object that is created
     * @throws IOException when the jsonString is not in JSON format
     */
    public static Reminder fromJson(String jsonString) throws IOException {
        JsonNode node = JsonUtil.getObjectMapper().readTree(jsonString);
        String dateTimeString = node.get("dateTime").asText();
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString);

        return new Reminder(dateTime);
    }
}
