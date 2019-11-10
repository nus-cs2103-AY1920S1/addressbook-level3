package seedu.address.ics;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.DateTime;
import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;
import seedu.address.model.tasks.TaskSourceBuilder;

//@@author marcusteh1238
public class IcsParserTest {

    //@@author marcusteh1238
    /**
     * Helper function to check if the tags in the parsed ICS file is valid.
     * @param expectedTags The tags that are expected to be in the new object.
     * @param outcomeTags The tags that are actually in the new object.
     * @return A boolean that indicates if the output tags are the same as the expected tags.
     */
    public boolean checkTags(ArrayList<String> expectedTags, Set<String> outcomeTags) {
        boolean containsAllTags = true;
        for (String tag : expectedTags) {
            if (outcomeTags.contains(tag)) {
                outcomeTags.remove(tag);
            } else {
                return false;
            }
        }
        return outcomeTags.isEmpty();
    }

    //@@author marcusteh1238
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
        String description = "Test Description";

        EventSource expected = EventSource
                .newBuilder(description, startDateTime)
                .build();
        EventSource outcome = IcsParser.parseSingleEvent(icsEventObjectString);
        assertEquals(expected.getDescription(), outcome.getDescription());
        assertEquals(expected.getStartDateTime(), outcome.getStartDateTime());
    }

    //@@author marcusteh1238
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
        assertDoesNotThrow(() -> DateTime.fromIcsString("20191109T043400Z"));
        DateTime startDateTime = DateTime.fromIcsString("20191109T043400Z");
        assertDoesNotThrow(() -> DateTime.fromIcsString("20191109T054500Z"));
        DateTime endDateTime = DateTime.fromIcsString("20191109T054500Z");
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

    //@@author marcusteh1238
    @Test
    public void eventSourceCreationCorrectness_parseSingleEventNormalWithEndDateTimeWithTags_valueAsExpected()
            throws ParseException, IcsException {
        String icsEventObjectString = "BEGIN:VEVENT\n"
                + "UID:2019-11-09T07:32:17.516817800Z@Horo\n"
                + "DTSTAMP:20191109T073217Z\n"
                + "DTSTART:20191109T043400Z\n"
                + "SUMMARY:Test Description\n"
                + "DTEND:20191109T054500Z\n"
                + "DESCRIPTION:[tag1][tag2]\n"
                + "END:VEVENT";
        assertDoesNotThrow(() -> DateTime.fromIcsString("20191109T043400Z"));
        DateTime startDateTime = DateTime.fromIcsString("20191109T043400Z");
        assertDoesNotThrow(() -> DateTime.fromIcsString("20191109T054500Z"));
        DateTime endDateTime = DateTime.fromIcsString("20191109T054500Z");
        String description = "Test Description";
        ArrayList<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("tag2");
        EventSource expected = EventSource
                .newBuilder(description, startDateTime)
                .setEnd(endDateTime)
                .setTags(tags)
                .build();
        EventSource outcome = IcsParser.parseSingleEvent(icsEventObjectString);

        assertEquals(expected.getDescription(), outcome.getDescription());
        assertEquals(expected.getStartDateTime(), outcome.getStartDateTime());
        assertEquals(expected.getEndDateTime(), outcome.getEndDateTime());
        Set<String> outcomeTags = outcome.getTags();
        assertNotNull(outcomeTags);
        assertTrue(checkTags(tags, outcomeTags));
    }

    //@@author marcusteh1238
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

    //@@author marcusteh1238
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
        assertDoesNotThrow(() -> DateTime.fromIcsString("20191109T043400Z"));
        DateTime dueDate = DateTime.fromIcsString("20191109T043400Z");

        TaskSourceBuilder builder = TaskSource.newBuilder(description);
        builder.setDueDate(dueDate);
        TaskSource expected = builder.build();
        TaskSource outcome = IcsParser.parseSingleTask(icsTaskObjectString);

        assertEquals(expected.getDescription(), outcome.getDescription());
        assertEquals(expected.getDueDate(), outcome.getDueDate());
    }

    //@@author marcusteh1238
    @Test
    public void taskSourceCreationCorrectness_parseSingleTaskNormalWithDueDateTimeWithTags_valueAsExpected()
            throws ParseException, IcsException {
        String icsTaskObjectString = "BEGIN:VTODO\n"
                + "UID:2019-11-09T07:32:17.517840600Z@Horo\n"
                + "DTSTAMP:20191109T073217Z\n"
                + "SUMMARY:hello\n"
                + "DUE:20191109T043400Z\n"
                + "DESCRIPTION:[tag1][tag2]\n"
                + "END:VTODO";
        String description = "hello";
        assertDoesNotThrow(() -> DateTime.fromIcsString("20191109T043400Z"));
        DateTime dueDate = DateTime.fromIcsString("20191109T043400Z");
        ArrayList<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("tag2");

        TaskSourceBuilder builder = TaskSource.newBuilder(description);
        builder.setDueDate(dueDate);
        TaskSource expected = builder.build();
        TaskSource outcome = IcsParser.parseSingleTask(icsTaskObjectString);

        assertEquals(expected.getDescription(), outcome.getDescription());
        assertEquals(expected.getDueDate(), outcome.getDueDate());
        assertTrue(checkTags(tags, outcome.getTags()));
    }
}
