package seedu.jarvis.model.planner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.tag.Tag;

class EventTest {

    @Test
    void addPriority() {
        Date start = new Date(2019, 10, 10);
        Date end = new Date(2019, 10, 11);
        Event e = new Event("homework", start, end);
        e.addPriority("high");
        assertNotNull(e.priority);
    }

    @Test
    void addFrequency() {
        Date start = new Date(2019, 10, 10);
        Date end = new Date(2019, 10, 11);

        Event e = new Event("homework", start, end);
        e.addFrequency("weekly");
        assertNotNull(e.frequency);
    }

    @Test
    void addTag() {
        Date start = new Date(2019, 10, 10);
        Date end = new Date(2019, 10, 11);
        Event e = new Event("homework", start, end);
        Tag t = new Tag("school");
        e.addTag(t);
        assertNotNull(e.tags);
    }

    @Test
    void getTags() {
        Date start = new Date(2019, 10, 10);
        Date end = new Date(2019, 10, 11);
        Event e = new Event("homework", start, end);
        Tag t = new Tag("school");
        e.addTag(t);
        assertEquals(e.getTags().contains(t), true);
    }
}
