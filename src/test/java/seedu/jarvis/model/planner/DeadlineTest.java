package seedu.jarvis.model.planner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.tag.Tag;



class DeadlineTest {

    @Test
    void addPriority() {
        Date due = new Date(2019, 10, 10);
        Deadline d = new Deadline("homework", due);
        d.addPriority("high");
        assertNotNull(d.priority);
    }

    @Test
    void addFrequency() {
        Date due = new Date(2019, 10, 10);
        Deadline d = new Deadline("homework", due);
        d.addFrequency("weekly");
        assertNotNull(d.frequency);
    }

    @Test
    void addTag() {
        Date due = new Date(2019, 10, 10);
        Deadline d = new Deadline("homework", due);
        Tag t = new Tag("school");
        d.addTag(t);
        assertNotNull(d.tags);
    }

    @Test
    void getTags() {
        Date due = new Date(2019, 10, 10);
        Deadline d = new Deadline("homework", due);
        Tag t = new Tag("school");
        d.addTag(t);
        assertEquals(d.getTags().contains(t), true);
    }
}
