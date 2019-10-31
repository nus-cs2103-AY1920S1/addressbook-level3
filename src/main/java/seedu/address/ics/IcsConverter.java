package seedu.address.ics;

import java.time.Instant;

import seedu.address.model.DateTime;
import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;

/**
 * This is the class responsible for converting Events and Tasks into their relevant ICS strings.
 */
public class IcsConverter {

    /**
     * Converts an EventSource object into its String representation in the Ics file format.
     * Its details and start time will be saved in this String representation.
     * @param event The EventSource object to be converted to the Ics file format.
     * @return The String representation of the EventSource in the Ics file format.
     */
    public static String convertEvent(EventSource event) {
        StringBuilder icsStringBuilder = new StringBuilder("BEGIN:VEVENT");

        String uid = generateUid();
        String dtStamp = DateTime.now().toIcsString();
        String start = event.getStartDateTime().toIcsString();

        icsStringBuilder
                .append("\n").append("UID:").append(uid)
                .append("\n").append("DTSTAMP:").append(dtStamp)
                .append("\n").append("DTSTART:").append(start)
                .append("\n").append("SUMMARY:").append(event.getDescription());
        if (event.getEndDateTime() != null) {
            String end = event.getEndDateTime().toIcsString();
            icsStringBuilder
                    .append("\n").append("DTEND:").append(end);
        }

        icsStringBuilder.append("\n").append("END:VEVENT");
        return icsStringBuilder.toString();
    }

    /**
     * Converts a TaskSource object into its String representation in the Ics file format.
     * Its details and due date will be saved in this String representation.
     * @param task The TaskSource object to be converted to the Ics file format.
     * @return The String representation of the TaskSource in the Ics file format.
     */
    public static String convertTask(TaskSource task) {
        StringBuilder icsStringBuilder = new StringBuilder("BEGIN:VTODO");

        String uid = generateUid();
        String dtStamp = DateTime.now().toIcsString();

        icsStringBuilder
                .append("\n").append("UID:").append(uid)
                .append("\n").append("DTSTAMP:").append(dtStamp)
                .append("\n").append("SUMMARY:").append(task.getDescription());

        if (task.getDueDate() != null) {
            icsStringBuilder.append("\n").append("DUE:").append(task.getDueDate().toIcsString());
        }

        icsStringBuilder.append("\n").append("END:VTODO");
        return icsStringBuilder.toString();
    }

    /**
     * Generates a unique UID that complies with section RFC5545 of the iCalendar specification.
     * This is achieved by using the Instant of the function call.
     * @return A UID String.
     */
    public static String generateUid() {
        Instant currentInstant = Instant.now();
        return currentInstant + "@Horo";
    }
}
