package seedu.jarvis.model.planner.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.planner.TypicalTasks.EVENT;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.tag.Tag;
import seedu.jarvis.model.planner.enums.Frequency;
import seedu.jarvis.model.planner.enums.Priority;
import seedu.jarvis.model.planner.enums.Status;
import seedu.jarvis.model.planner.enums.TaskType;

class EventTest {

    @Test
    void addPriority() {
        LocalDate start = LocalDate.parse("18/10/2019", Task.getDateFormat());
        LocalDate end = LocalDate.parse("19/10/2019", Task.getDateFormat());
        Event e = new Event("homework", start, end);
        e.setPriority(Priority.HIGH);
        assertNotNull(e.priority);
    }

    @Test
    void addFrequency() {
        LocalDate start = LocalDate.parse("18/10/2019", Task.getDateFormat());
        LocalDate end = LocalDate.parse("19/10/2019", Task.getDateFormat());
        Event e = new Event("homework", start, end);
        e.setFrequency(Frequency.DAILY);
        assertNotNull(e.frequency);
    }

    @Test
    void addTag() {
        LocalDate start = LocalDate.parse("18/10/2019", Task.getDateFormat());
        LocalDate end = LocalDate.parse("19/10/2019", Task.getDateFormat());
        Event e = new Event("homework", start, end);
        Tag t = new Tag("school");
        e.addTag(t);
        assertNotNull(e.tags);
    }

    @Test
    void getTags() {
        LocalDate start = LocalDate.parse("18/10/2019", Task.getDateFormat());
        LocalDate end = LocalDate.parse("19/10/2019", Task.getDateFormat());
        Event e = new Event("homework", start, end);
        Tag t = new Tag("school");
        e.addTag(t);
        assertTrue(e.getTags().contains(t));
    }

    @Test
    void equals_validInput_true() {
        LocalDate startOne = LocalDate.parse("18/10/2019", Task.getDateFormat());
        LocalDate endOne = LocalDate.parse("19/10/2019", Task.getDateFormat());
        Event one = new Event("borrow book", startOne, endOne);

        LocalDate startTwo = LocalDate.parse("18/10/2019", Task.getDateFormat());
        LocalDate endTwo = LocalDate.parse("19/10/2019", Task.getDateFormat());
        Event two = new Event("borrow book", startTwo, endTwo);

        assertTrue(one.equals(two));
    }

    @Test
    void equals_validInput_false() {
        LocalDate startOne = LocalDate.parse("18/10/2019", Task.getDateFormat());
        LocalDate endOne = LocalDate.parse("19/10/2019", Task.getDateFormat());
        Event one = new Event("borrow hello", startOne, endOne);

        LocalDate startTwo = LocalDate.parse("18/10/2019", Task.getDateFormat());
        LocalDate endTwo = LocalDate.parse("19/10/2019", Task.getDateFormat());
        Event two = new Event("borrow book", startTwo, endTwo);

        assertFalse(one.equals(two));
    }

    @Test
    void getStartDate_true() {
        LocalDate start = LocalDate.parse("18/10/2019", Task.getDateFormat());
        LocalDate end = LocalDate.parse("19/10/2019", Task.getDateFormat());
        Event testEvent = new Event("borrow book", start, end);
        LocalDate testCal = LocalDate.parse("18/10/2019", Task.getDateFormat());
        assertEquals(0, testCal.compareTo(testEvent.getStartDate()));
    }

    @Test
    void getEndDate_true() {
        LocalDate start = LocalDate.parse("18/10/2019", Task.getDateFormat());
        LocalDate end = LocalDate.parse("19/10/2019", Task.getDateFormat());
        Event testEvent = new Event("borrow book", start, end);
        LocalDate testCal = LocalDate.parse("19/10/2019", Task.getDateFormat());
        assertEquals(0, testCal.compareTo(testEvent.getEndDate()));
    }

    @Test
    void toString_withAllAttributesPresent() {
        LocalDate start = LocalDate.parse("18/10/2019", Task.getDateFormat());
        LocalDate end = LocalDate.parse("19/10/2019", Task.getDateFormat());
        Event testEvent = new Event("borrow book", start, end);

        testEvent.setFrequency(Frequency.WEEKLY);
        testEvent.setPriority(Priority.HIGH);
        testEvent.addTag(new Tag("school"));

        String expected = "Event: borrow book from 2019-10-18 to 2019-10-19\nPriority: High\nFrequency: Weekly"
                           + "\nTags: [[school]]";

        assertEquals(expected, testEvent.toString());

    }

    @Test
    void toString_withNoAttributesPresent() {
        LocalDate start = LocalDate.parse("18/10/2019", Task.getDateFormat());
        LocalDate end = LocalDate.parse("19/10/2019", Task.getDateFormat());
        Event testEvent = new Event("borrow book", start, end);

        String expected = "Event: borrow book from 2019-10-18 to 2019-10-19";

        assertEquals(expected, testEvent.toString());

    }

    @Test
    void getTaskDes_success() {
        LocalDate start = LocalDate.parse("18/10/2019", Task.getDateFormat());
        LocalDate end = LocalDate.parse("19/10/2019", Task.getDateFormat());
        Event testEvent = new Event("borrow book", start, end);

        String expected = "borrow book";

        assertEquals(expected, testEvent.getTaskDes());
    }

    @Test
    public void adaptToJsonAdaptedEvent() throws Exception {
        assertEquals(EVENT, EVENT.adaptToJsonAdaptedTask().toModelType());
    }

    @Test
    void markAsDone() {
        LocalDate start = LocalDate.parse("18/10/2019", Task.getDateFormat());
        LocalDate end = LocalDate.parse("19/10/2019", Task.getDateFormat());
        Event testEvent = new Event("borrow book", start, end);

        testEvent.markAsDone();

        assertEquals(Status.DONE, testEvent.getStatus());
    }

    @Test
    void markAsNotDone() {
        LocalDate start = LocalDate.parse("18/10/2019", Task.getDateFormat());
        LocalDate end = LocalDate.parse("19/10/2019", Task.getDateFormat());
        Event testEvent = new Event("borrow book", start, end);

        testEvent.markAsDone();
        assertEquals(Status.DONE, testEvent.getStatus());

        testEvent.markAsNotDone();
        assertEquals(Status.NOT_DONE, testEvent.getStatus());
    }

    @Test
    void getStatus() {
        LocalDate start = LocalDate.parse("18/10/2019", Task.getDateFormat());
        LocalDate end = LocalDate.parse("19/10/2019", Task.getDateFormat());
        Event testEvent = new Event("borrow book", start, end);

        assertEquals(Status.NOT_DONE, testEvent.getStatus());
    }

    @Test
    void getTaskType() {
        LocalDate start = LocalDate.parse("18/10/2019", Task.getDateFormat());
        LocalDate end = LocalDate.parse("19/10/2019", Task.getDateFormat());
        Event e = new Event("borrow book", start, end);

        TaskType expected = TaskType.EVENT;
        assertEquals(expected, e.getTaskType());
    }

}
