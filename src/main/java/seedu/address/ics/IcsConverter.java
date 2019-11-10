package seedu.address.ics;

import java.time.Instant;
import java.util.Set;

import seedu.address.model.DateTime;
import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;

//@@author marcusteh1238
/**
 * This is the class responsible for converting Events and Tasks into their relevant ICS strings.
 */
public class IcsConverter {

    //@@author marcusteh1238
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
        String tagString = event.getTags() != null
                ? getTagString(event.getTags())
                : "";

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
        if (!tagString.equals("")) {
            icsStringBuilder.append("\n").append("DESCRIPTION:").append(tagString);
        }

        icsStringBuilder.append("\n").append("END:VEVENT");
        return icsStringBuilder.toString();
    }

    //@@author marcusteh1238
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
        String tagString = task.getTags() != null
                ? getTagString(task.getTags())
                : "";
        icsStringBuilder
                .append("\n").append("UID:").append(uid)
                .append("\n").append("DTSTAMP:").append(dtStamp)
                .append("\n").append("SUMMARY:").append(task.getDescription());

        if (task.getDueDate() != null) {
            icsStringBuilder.append("\n").append("DUE:").append(task.getDueDate().toIcsString());
        }
        if (!tagString.equals("")) {
            icsStringBuilder.append("\n").append("DESCRIPTION:").append(tagString);
        }
        icsStringBuilder.append("\n").append("END:VTODO");
        return icsStringBuilder.toString();
    }

    //@@author marcusteh1238
    /**
     * Generates a unique UID that complies with section RFC5545 of the iCalendar specification.
     * This is achieved by using the Instant of the function call.
     * @return A UID String.
     */
    public static String generateUid() {
        Instant currentInstant = Instant.now();
        return currentInstant + "@Horo";
    }

    //@@author marcusteh1238
    /**
     * Generates a String representing the tags to be stored in the ICS object's description.
     * @param tags The tags of the EventSource or TaskSource object.
     * @return The tags represented by a string, where each tag is surrounded by square brackets (eg. [School][Home]).
     */
    public static String getTagString(Set<String> tags) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String tag : tags) {
            stringBuilder
                    .append("[")
                    .append(tag)
                    .append("]");
        }
        return stringBuilder.toString();
    }
}
