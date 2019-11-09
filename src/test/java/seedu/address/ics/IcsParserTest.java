package seedu.address.ics;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.DateTime;
import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;
import seedu.address.model.tasks.TaskSourceBuilder;

public class IcsParserTest {
    @Test
    public void eventSourceCreationCorrectness_parseSingleEventNormalWithoutEndDateTime_valueAsExpected()
            throws ParseException, IcsException {
        String icsEventObjectString = "BEGIN:VEVENT\n"
                + "UID:2019-11-09T07:32:17.516817800Z@Horo\n"
                + "DTSTAMP:20191109T073217Z\n"
                + "DTSTART:20191109T043400Z\n"
                + "SUMMARY:Test Description\n"
                + "END:VEVENT";
        assertDoesNotThrow(() -> DateTime.fromIcsString("20191109T043400Z"));
        DateTime startDateTime = DateTime.fromIcsString("20191109T043400Z");
        assertEquals(startDateTime, DateTime.fromUserString("09/11/2019 12:34"));
        String description = "Test Description";

        EventSource expected = EventSource
                .newBuilder(description, startDateTime)
                .build();
        EventSource outcome = IcsParser.parseSingleEvent(icsEventObjectString);
        assertEquals(expected.getDescription(), outcome.getDescription());
        assertTrue(expected.getStartDateTime().equals(outcome.getStartDateTime()));
    }

    @Test
    public void eventSourceCreationCorrectness_parseSingleEventNormalWithEndDateTime_valueAsExpected()
            throws ParseException, IcsException {
        String icsEventObjectString = "BEGIN:VEVENT\n"
                + "UID:2019-11-09T07:32:17.516817800Z@Horo\n"
                + "DTSTAMP:20191109T073217Z\n"
                + "DTSTART:20191109T043400Z\n"
                + "SUMMARY:Test Description\n"
                + "DTEND:20191109T054500Z\n"
                + "END:VEVENT";
        assertDoesNotThrow(() -> DateTime.fromUserString("09/11/2019 12:34"));
        DateTime startDateTime = DateTime.fromUserString("09/11/2019 12:34");
        assertDoesNotThrow(() -> DateTime.fromUserString("09/11/2019 13:45"));
        DateTime endDateTime = DateTime.fromUserString("09/11/2019 13:45");
        String description = "Test Description";

        EventSource expected = EventSource
                .newBuilder(description, startDateTime)
                .setEnd(endDateTime)
                .build();
        EventSource outcome = IcsParser.parseSingleEvent(icsEventObjectString);

        assertEquals(expected.getDescription(), outcome.getDescription());
        assertEquals(expected.getStartDateTime(), outcome.getStartDateTime());
        assertEquals(expected.getEndDateTime(), outcome.getEndDateTime());
    }

    @Test
    public void taskSourceCreationCorrectness_parseSingleTaskNormalWithoutDueDateTime_valueAsExpected()
            throws IcsException {
        String icsTaskObjectString = "BEGIN:VTODO\n"
                + "UID:2019-11-09T07:32:17.517840600Z@Horo\n"
                + "DTSTAMP:20191109T073217Z\n"
                + "SUMMARY:hello\n"
                + "END:VTODO";
        String description = "hello";
        TaskSource expected = TaskSource.newBuilder(description).build();
        TaskSource outcome = IcsParser.parseSingleTask(icsTaskObjectString);

        assertEquals(expected.getDescription(), outcome.getDescription());
    }

    @Test
    public void taskSourceCreationCorrectness_parseSingleTaskNormalWithDueDateTime_valueAsExpected()
            throws ParseException, IcsException {
        String icsTaskObjectString = "BEGIN:VTODO\n"
                + "UID:2019-11-09T07:32:17.517840600Z@Horo\n"
                + "DTSTAMP:20191109T073217Z\n"
                + "SUMMARY:hello\n"
                + "DUE:20191109T043400Z\n"
                + "END:VTODO";
        String description = "hello";
        assertDoesNotThrow(() -> DateTime.fromUserString("09/11/2019 12:34"));
        DateTime dueDate = DateTime.fromUserString("09/11/2019 12:34");

        TaskSourceBuilder builder = TaskSource.newBuilder(description);
        builder.setDueDate(dueDate);
        TaskSource expected = builder.build();
        TaskSource outcome = IcsParser.parseSingleTask(icsTaskObjectString);

        assertEquals(expected.getDescription(), outcome.getDescription());
        assertEquals(expected.getDueDate(), outcome.getDueDate());
    }
}
