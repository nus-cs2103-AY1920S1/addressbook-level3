package seedu.jarvis.model.planner.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.address.tag.Tag;
import seedu.jarvis.model.planner.Frequency;
import seedu.jarvis.model.planner.Priority;

class EventTest {

    @Test
    void addPriority() {
        LocalDate start = LocalDate.parse("18/10/2019", Task.getDateFormat());
        LocalDate end = LocalDate.parse("19/10/2019", Task.getDateFormat());
        Event e = new Event("homework", start, end);
        e.addPriority(Priority.HIGH);
        assertNotNull(e.priority);
    }

    @Test
    void addFrequency() {
        LocalDate start = LocalDate.parse("18/10/2019", Task.getDateFormat());
        LocalDate end = LocalDate.parse("19/10/2019", Task.getDateFormat());
        Event e = new Event("homework", start, end);
        e.addFrequency(Frequency.DAILY);
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

}
