package seedu.jarvis.model.planner;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.address.tag.Tag;
import seedu.jarvis.model.planner.exceptions.InvalidFrequencyException;
import seedu.jarvis.model.planner.exceptions.InvalidPriorityException;


class DeadlineTest {

    @Test
    void addPriority_validInput_success() {
        Calendar due = Calendar.getInstance();
        Deadline d = new Deadline("homework", due);
        d.addPriority("high");
        assertNotNull(d.priority);
    }

    @Test
    void addPriority_invalidInput_exceptionThrown() {
        Calendar due = Calendar.getInstance();
        Deadline d = new Deadline("homework", due);

        assertThrows(InvalidPriorityException.class, () -> d.addPriority("highest"));
    }

    @Test
    void addFrequency_validInput_success() {
        Calendar due = Calendar.getInstance();
        Deadline d = new Deadline("homework", due);
        d.addFrequency("weekly");
        assertNotNull(d.frequency);
    }

    @Test
    void addFrequency_invalidInput_exceptionThrown() {
        Calendar due = Calendar.getInstance();
        Deadline d = new Deadline("homework", due);
        assertThrows(InvalidFrequencyException.class, () -> d.addFrequency("every week"));
    }

    @Test
    void addTag_validInput_success() {
        Calendar due = Calendar.getInstance();
        Deadline d = new Deadline("homework", due);
        Tag t = new Tag("school");
        d.addTag(t);
        assertNotNull(d.tags);
    }

    @Test
    void getTags_validInput_success() {
        Calendar due = Calendar.getInstance();
        Deadline d = new Deadline("homework", due);
        Tag t = new Tag("school");
        d.addTag(t);
        assertTrue(d.getTags().contains(t));
    }

    @Test
    void isEqual_validInput_true() {
//        Deadline deadlineOne = Deadline;
    }

    @Test
    void isEqual_validInput_false() {

    }

    @Test
    void getDueDate() {
    }
}
