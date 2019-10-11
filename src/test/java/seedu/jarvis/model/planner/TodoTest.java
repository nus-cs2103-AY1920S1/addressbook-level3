package seedu.jarvis.model.planner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.tag.Tag;

class TodoTest {

    @Test
    void addPriority() {
        Todo t = new Todo("homework");
        t.addPriority("high");
        assertNotNull(t.priority);
    }

    @Test
    void addFrequency() {
        Date start = new Date(2019, 10, 10);
        Date end = new Date(2019, 10, 11);

        Todo t = new Todo("homework");
        t.addFrequency("weekly");
        assertNotNull(t.frequency);
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
        assertEquals(t.getTags().contains(tag), true);
    }
}
