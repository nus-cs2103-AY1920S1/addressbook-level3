package seedu.jarvis.model.planner;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.address.tag.Tag;
import seedu.jarvis.model.planner.exceptions.InvalidFrequencyException;
import seedu.jarvis.model.planner.exceptions.InvalidPriorityException;

class TodoTest {

    @Test
    void addPriority() {
        Todo t = new Todo("homework");
        t.addPriority("high");
        assertNotNull(t.priority);
    }

    @Test
    void addPriority_invalidInput_exceptionThrown() {
        Todo t = new Todo("homework");
        assertThrows(InvalidPriorityException.class, () -> t.addPriority("highest"));
    }

    @Test
    void addFrequency() {
        Todo t = new Todo("homework");
        t.addFrequency("weekly");
        assertNotNull(t.frequency);
    }

    @Test
    void addFrequency_invalidInput_exceptionThrown() {
        Todo t = new Todo("homework");
        assertThrows(InvalidFrequencyException.class, () -> t.addFrequency("every week"));
    }

    @Test
    void addTag() {
        Date start = new Date(2019, 10, 10);
        Date end = new Date(2019, 10, 11);
        Todo t = new Todo("homework");
        Tag tag = new Tag("school");
        t.addTag(tag);
        assertNotNull(t.tags);
    }

    @Test
    void getTags() {
        Date start = new Date(2019, 10, 10);
        Date end = new Date(2019, 10, 11);
        Todo t = new Todo("homework");
        Tag tag = new Tag("school");
        t.addTag(tag);
        assertTrue(t.getTags().contains(tag));
    }
}
