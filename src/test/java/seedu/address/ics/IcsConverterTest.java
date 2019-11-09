package seedu.address.ics;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.DateTime;
import seedu.address.model.events.EventSource;
import seedu.address.model.events.EventSourceBuilder;
import seedu.address.model.tasks.TaskSource;
import seedu.address.model.tasks.TaskSourceBuilder;

public class IcsConverterTest {

    @Test
    public void toString_normalEventNoEndTime_icsConversion() throws ParseException {

        String description = "Test Description";
        String start = "11/11/1111 11:00";
        assertDoesNotThrow(() -> DateTime.fromUserString(start));
        DateTime startDateTime = DateTime.fromUserString(start);
        EventSource eventSource = EventSource.newBuilder(description, startDateTime).build();

        String icsString = IcsConverter.convertEvent(eventSource);

        String[] icsStringArr = icsString.split("\\r?\\n");

        // validate start of ICS VEVENT object field.
        assertEquals("BEGIN:VEVENT", icsStringArr[0]);

        // validate UID.
        String uidString = icsStringArr[1];
        assertTrue(uidString.startsWith("UID:") && uidString.endsWith("@Horo"));
        uidString = uidString.replaceFirst("UID:", "");
        String instant = uidString.replaceFirst("@Horo", "");
        assertDoesNotThrow(() -> Instant.parse(instant));

        // validate DTSTAMP field.
        String dtStampString = icsStringArr[2];
        assertTrue(dtStampString.startsWith("DTSTAMP:"));
        String dtStamp = dtStampString.replaceFirst("DTSTAMP:", "");
        assertDoesNotThrow(() -> DateTime.fromIcsString(dtStamp));

        // validate DTSTART field.
        String dtStartString = icsStringArr[3];
        assertTrue(dtStartString.startsWith("DTSTART:"));
        dtStartString = dtStartString.replace("DTSTART:", "");
        assertEquals(DateTime.fromIcsString(dtStartString), startDateTime);

        // validate SUMMARY field.
        assertEquals("SUMMARY:Test Description", icsStringArr[4]);

        // validate end of ICS VEVENT object line
        assertEquals("END:VEVENT", icsStringArr[5]);
    }

    @Test
    public void toString_eventWithEndTime_icsConversion() throws ParseException {
        String description = "Test Description";
        String start = "11/11/1111 11:00";
        assertDoesNotThrow(() -> DateTime.fromUserString(start));
        DateTime startDateTime = DateTime.fromUserString(start);

        String end = "11/11/1111 12:00";
        assertDoesNotThrow(() -> DateTime.fromUserString(end));
        DateTime endDateTime = DateTime.fromUserString(end);

        EventSourceBuilder eventSourceBuilder = EventSource.newBuilder(description, startDateTime);
        eventSourceBuilder.setEnd(endDateTime);
        EventSource eventSource = eventSourceBuilder.build();

        String icsString = IcsConverter.convertEvent(eventSource);

        String[] icsStringArr = icsString.split("\\r?\\n");

        // validate start of ICS VEVENT object field.
        assertEquals("BEGIN:VEVENT", icsStringArr[0]);

        // validate UID.
        String uidString = icsStringArr[1];
        assertTrue(uidString.startsWith("UID:") && uidString.endsWith("@Horo"));
        uidString = uidString.replaceFirst("UID:", "");
        String instant = uidString.replaceFirst("@Horo", "");
        assertDoesNotThrow(() -> Instant.parse(instant));

        // validate DTSTAMP field.
        String dtStampString = icsStringArr[2];
        assertTrue(dtStampString.startsWith("DTSTAMP:"));
        String dtStamp = dtStampString.replaceFirst("DTSTAMP:", "");
        assertDoesNotThrow(() -> DateTime.fromIcsString(dtStamp));

        // validate DTSTART field.
        String dtStartString = icsStringArr[3];
        assertTrue(dtStartString.startsWith("DTSTART:"));
        dtStartString = dtStartString.replace("DTSTART:", "");
        assertEquals(startDateTime, DateTime.fromIcsString(dtStartString));

        // validate SUMMARY field.
        assertEquals("SUMMARY:Test Description", icsStringArr[4]);

        // validate DTEND field.
        String dtEndString = icsStringArr[5];
        assertTrue(dtEndString.startsWith("DTEND:"));
        dtEndString = dtEndString.replace("DTEND:", "");
        assertEquals(endDateTime, DateTime.fromIcsString(dtEndString));

        // validate end of ICS VEVENT object field.
        assertEquals("END:VEVENT", icsStringArr[6]);
    }

    @Test
    public void toString_normalTask_icsConversion() {
        String description = "Test Description";
        TaskSource taskSource = TaskSource.newBuilder(description).build();

        String icsString = IcsConverter.convertTask(taskSource);
        String[] icsStringArr = icsString.split("\\r?\\n");

        // validate start of ICS VTODO object field.
        assertEquals("BEGIN:VTODO", icsStringArr[0]);

        // validate UID.
        String uidString = icsStringArr[1];
        assertTrue(uidString.startsWith("UID:") && uidString.endsWith("@Horo"));
        uidString = uidString.replaceFirst("UID:", "");
        String instant = uidString.replaceFirst("@Horo", "");
        assertDoesNotThrow(() -> Instant.parse(instant));

        // validate DTSTAMP field.
        String dtStampString = icsStringArr[2];
        assertTrue(dtStampString.startsWith("DTSTAMP:"));
        String dtStamp = dtStampString.replaceFirst("DTSTAMP:", "");
        assertDoesNotThrow(() -> DateTime.fromIcsString(dtStamp));

        // validate SUMMARY field.
        assertEquals("SUMMARY:Test Description", icsStringArr[3]);

        // validate end of ICS VTODO object field.
        assertEquals("END:VTODO", icsStringArr[4]);
    }

    @Test
    public void toString_taskWithDueDate_icsConversion() throws ParseException {
        String description = "Test Description";
        TaskSourceBuilder taskSourceBuilder = TaskSource.newBuilder(description);

        String due = "11/11/1111 12:00";
        assertDoesNotThrow(() -> DateTime.fromUserString(due));
        DateTime dueDateTime = DateTime.fromUserString(due);

        taskSourceBuilder.setDueDate(dueDateTime);
        TaskSource taskSource = taskSourceBuilder.build();

        String icsString = IcsConverter.convertTask(taskSource);
        String[] icsStringArr = icsString.split("\\r?\\n");

        // validate start of ICS VTODO object field.
        assertEquals("BEGIN:VTODO", icsStringArr[0]);

        // validate UID.
        String uidString = icsStringArr[1];
        assertTrue(uidString.startsWith("UID:") && uidString.endsWith("@Horo"));
        uidString = uidString.replaceFirst("UID:", "");
        String instant = uidString.replaceFirst("@Horo", "");
        assertDoesNotThrow(() -> Instant.parse(instant));

        // validate DTSTAMP field.
        String dtStampString = icsStringArr[2];
        assertTrue(dtStampString.startsWith("DTSTAMP:"));
        String dtStamp = dtStampString.replaceFirst("DTSTAMP:", "");
        assertDoesNotThrow(() -> DateTime.fromIcsString(dtStamp));

        // validate SUMMARY field.
        assertEquals("SUMMARY:Test Description", icsStringArr[3]);

        // validate DUE field.
        assertTrue(icsStringArr[4].startsWith("DUE:"));
        String dueDateOutcome = icsStringArr[4].replace("DUE:", "");
        assertEquals(dueDateTime, DateTime.fromIcsString(dueDateOutcome));

        // validate end of ICS VTODO object field.
        assertEquals("END:VTODO", icsStringArr[5]);
    }

}
