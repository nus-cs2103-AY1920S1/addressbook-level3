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


class DeadlineTest {

    @Test
    void addPriority_validInput_success() {
        LocalDate due = LocalDate.parse("10/10/2019", Task.getDateFormat());
        Deadline d = new Deadline("homework", due);
        d.addPriority(Priority.HIGH);
        assertNotNull(d.priority);
    }

    @Test
    void addFrequency_validInput_success() {
        LocalDate due = LocalDate.parse("10/10/2019", Task.getDateFormat());
        Deadline d = new Deadline("homework", due);
        d.addFrequency(Frequency.MONTHLY);
        assertNotNull(d.frequency);
    }

    @Test
    void addTag_validInput_success() {
        LocalDate due = LocalDate.parse("10/10/2019", Task.getDateFormat());
        Deadline d = new Deadline("homework", due);
        Tag t = new Tag("school");
        d.addTag(t);
        assertNotNull(d.tags);
    }

    @Test
    void getTags_validInput_success() {
        LocalDate due = LocalDate.parse("10/10/2019", Task.getDateFormat());
        Deadline d = new Deadline("homework", due);
        Tag t = new Tag("school");
        d.addTag(t);
        assertTrue(d.getTags().contains(t));
    }

    @Test
    void equals_validInput_true() {
        LocalDate deadlineOneCal = LocalDate.parse("10/10/2019", Task.getDateFormat());
        LocalDate deadlineTwoCal = LocalDate.parse("10/10/2019", Task.getDateFormat());
        Deadline deadlineOne = new Deadline("borrow book", deadlineOneCal);
        Deadline deadlineTwo = new Deadline("borrow book", deadlineTwoCal);
        assertTrue(deadlineOne.equals(deadlineTwo));
    }

    @Test
    void equals_validInput_false() {
        LocalDate deadlineOneCal = LocalDate.parse("10/10/2019", Task.getDateFormat());
        LocalDate deadlineTwoCal = LocalDate.parse("10/10/2019", Task.getDateFormat());
        Deadline deadlineOne = new Deadline("borrow hello", deadlineOneCal);
        Deadline deadlineTwo = new Deadline("borrow book", deadlineTwoCal);
        assertFalse(deadlineOne.equals(deadlineTwo));
    }

    @Test
    void getDueDate() {
        LocalDate due = LocalDate.parse("10/10/2019", Task.getDateFormat());
        Deadline d = new Deadline("homework", due);
        assertEquals(due, d.getDueDate());
    }

    @Test
    void toString_withAllAttributesPresent() {
        LocalDate due = LocalDate.parse("10/10/2019", Task.getDateFormat());
        Deadline d = new Deadline("homework", due);
        d.addPriority(Priority.LOW);
        d.addFrequency(Frequency.MONTHLY);
        d.addTag(new Tag("school"));
        d.addTag(new Tag("cs"));

        String expected = "Deadline: homework by 2019-10-10\nPriority: LOW\nFrequency: MONTHLY"
                            + "\nTags: [[cs], [school]]";

        assertEquals(expected, d.toString());
    }

    @Test
    void toString_withNoAttributesPresent() {
        LocalDate due = LocalDate.parse("10/10/2019", Task.getDateFormat());
        Deadline d = new Deadline("homework", due);

        String expected = "Deadline: homework by 2019-10-10";

        assertEquals(expected, d.toString());
    }
}
