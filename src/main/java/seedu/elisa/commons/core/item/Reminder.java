package seedu.elisa.commons.core.item;

import static java.util.Objects.requireNonNull;
import static seedu.elisa.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import com.fasterxml.jackson.databind.JsonNode;

import seedu.elisa.commons.util.JsonUtil;


/**
 * Represents an Item's Reminder in ELISA.
 * Guarantees: immutable;
 */
public class Reminder {

    private final LocalDateTime defaultDateTime;
    private final LocalDateTime occurrenceDateTime;

    /**
     * Constructs a {@code Reminder}.
     *
     * @param defaultDateTime A valid LocalDateTime object.
     */
    public Reminder(LocalDateTime defaultDateTime) {
        requireNonNull(defaultDateTime);
        this.defaultDateTime = defaultDateTime;
        occurrenceDateTime = defaultDateTime;
    }

    /**
     * Constructs a {@code Reminder}.
     *
     * @param defaultDateTime A valid LocalDateTime object that stores the original DateTime is intended to occur.
     * @param occurrenceDateTime A valid LocalDateTime object for the reminder to occur.
     */
    private Reminder(LocalDateTime defaultDateTime, LocalDateTime occurrenceDateTime) {
        requireAllNonNull(defaultDateTime, occurrenceDateTime);
        this.defaultDateTime = defaultDateTime;
        this.occurrenceDateTime = occurrenceDateTime;
    }

    public LocalDateTime getDefaultDateTime() {
        return defaultDateTime;
    }

    public LocalDateTime getOccurrenceDateTime() {
        return occurrenceDateTime;
    }

    /**
     * Changes the dateTime that the reminder occurs. Removes the previous reminder so it does not occur.
     * @param dateTime A LocalDateTime object which dictates the dateTime the reminder occurs.
     * @return A new Reminder with the new dateTime for the reminder.
     */
    public Reminder changeOccurrenceDateTime(LocalDateTime dateTime) {
        //When Reminder is implemented, the previous reminder notification should also be removed here
        return new Reminder(defaultDateTime, dateTime);
    }

    /**
     * Changes the default dateTime of the reminder.
     * @param dateTime A LocalDateTime object which dictates the default dateTime of the Reminder.
     * @return A new Reminder with the new default dateTime for the reminder.
     */
    public Reminder changeDefaultDateTime(LocalDateTime dateTime) {
        return new Reminder(dateTime, dateTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        if (defaultDateTime.isEqual(occurrenceDateTime)) {
            builder.append("\nReminder DateTime: ")
                    .append(getDefaultDateTime().format(DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")));
        } else {
            builder.append("\nOriginal Reminder DateTime: ")
                    .append(getDefaultDateTime().format(DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")));

            builder.append("\nReminder DateTime: ")
                    .append(getOccurrenceDateTime().format(DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")));
        }

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
        return otherReminder.getOccurrenceDateTime().equals(getOccurrenceDateTime())
                && otherReminder.getDefaultDateTime().equals(getDefaultDateTime());
    }

    //Possibility of high number of hash collisions and as a result slower performance
    @Override
    public int hashCode() {
        return Objects.hash(defaultDateTime, occurrenceDateTime);
    }

    /**
     * Creates a reminder object from a JSON string.
     * @param jsonString the JSON string that represents the reminder
     * @return the reminder object that is created
     * @throws IOException when the jsonString is not in JSON format
     */
    public static Reminder fromJson(String jsonString) throws IOException {
        JsonNode node = JsonUtil.getObjectMapper().readTree(jsonString);
        String defaultDateTimeString = node.get("defaultDateTime").asText();
        LocalDateTime dateTime = LocalDateTime.parse(defaultDateTimeString);

        String occurrenceDateTimeString = node.get("occurrenceDateTime").asText();
        LocalDateTime occurenceDateTime = LocalDateTime.parse(occurrenceDateTimeString);

        return new Reminder(dateTime, occurenceDateTime);
    }
}
