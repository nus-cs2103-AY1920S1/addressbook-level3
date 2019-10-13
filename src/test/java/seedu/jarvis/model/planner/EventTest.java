package seedu.jarvis.model.planner;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.address.tag.Tag;
import seedu.jarvis.model.planner.exceptions.InvalidFrequencyException;
import seedu.jarvis.model.planner.exceptions.InvalidPriorityException;

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
}
