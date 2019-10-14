package seedu.jarvis.model.planner.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.address.tag.Tag;
import seedu.jarvis.model.planner.exceptions.InvalidFrequencyException;
import seedu.jarvis.model.planner.exceptions.InvalidPriorityException;
import seedu.jarvis.model.planner.tasks.Event;

class EventTest {

    @Test
    void addPriority() {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.set(Calendar.DAY_OF_MONTH, 20);
        Event e = new Event("homework", start, end);
        e.addPriority("high");
        assertNotNull(e.priority);
    }

    @Test
    void addPriority_invalidInput_exceptionThrown() {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.set(Calendar.DAY_OF_MONTH, 20);
        Event e = new Event("homework", start, end);
        assertThrows(InvalidPriorityException.class, () -> e.addPriority("highest"));
    }

    @Test
    void addFrequency() {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.set(Calendar.DAY_OF_MONTH, 20);
        Event e = new Event("homework", start, end);
        e.addFrequency("weekly");
        assertNotNull(e.frequency);
    }

    @Test
    void addFrequency_invalidInput_exceptionThrown() {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.set(Calendar.DAY_OF_MONTH, 20);
        Event e = new Event("homework", start, end);
        assertThrows(InvalidFrequencyException.class, () -> e.addFrequency("every week"));
    }

    @Test
    void addTag() {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.set(Calendar.DAY_OF_MONTH, 20);
        Event e = new Event("homework", start, end);
        Tag t = new Tag("school");
        e.addTag(t);
        assertNotNull(e.tags);
    }

    @Test
    void getTags() {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.set(Calendar.DAY_OF_MONTH, 20);
        Event e = new Event("homework", start, end);
        Tag t = new Tag("school");
        e.addTag(t);
        assertTrue(e.getTags().contains(t));
    }

    @Test
    void isEqual_validInput_true() {
        Calendar startOne = Calendar.getInstance();
        Calendar endOne = Calendar.getInstance();
        endOne.set(Calendar.DAY_OF_MONTH, 20);
        Event one = new Event("borrow book", startOne, endOne);

        Calendar startTwo = Calendar.getInstance();
        Calendar endTwo = Calendar.getInstance();
        endTwo.set(Calendar.DAY_OF_MONTH, 20);
        Event two = new Event("borrow book", startTwo, endTwo);

        assertTrue(one.isEqual(two));
    }

    @Test
    void isEqual_validInput_false() {
        Calendar startOne = Calendar.getInstance();
        startOne.set(2019, 12, 10);
        Calendar endOne = Calendar.getInstance();
        endOne.set(2019, 12, 10);
        Event one = new Event("borrow hello", startOne, endOne);

        Calendar startTwo = Calendar.getInstance();
        Calendar endTwo = Calendar.getInstance();
        endTwo.set(Calendar.DAY_OF_MONTH, 20);
        Event two = new Event("borrow book", startTwo, endTwo);

        assertFalse(one.isEqual(two));
    }

    @Test
    void getStartDate_true() {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.set(Calendar.DAY_OF_MONTH, 20);
        Event testEvent = new Event("borrow book", start, end);
        Calendar testCal = Calendar.getInstance();
        assertEquals(0, testCal.compareTo(testEvent.getStartDate()));
    }

    @Test
    void getEndDate_true() {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.set(Calendar.DAY_OF_MONTH, 20);
        Event testEvent = new Event("borrow book", start, end);
        Calendar testCal = Calendar.getInstance();
        testCal.set(Calendar.DAY_OF_MONTH, 20);
        assertEquals(0, testCal.compareTo(testEvent.getEndDate()));
    }

}
