package seedu.jarvis.model.planner.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.planner.TypicalTasks.TODO;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.tag.Tag;
import seedu.jarvis.model.planner.enums.Frequency;
import seedu.jarvis.model.planner.enums.Priority;
import seedu.jarvis.model.planner.enums.Status;
import seedu.jarvis.model.planner.enums.TaskType;

class TodoTest {

    @Test
    void addPriority() {
        Todo t = new Todo("homework");
        t.setPriority(Priority.HIGH);
        assertNotNull(t.priority);
    }

    @Test
    void addFrequency() {
        Todo t = new Todo("homework");
        t.setFrequency(Frequency.DAILY);
        assertNotNull(t.frequency);
    }

    @Test
    void addTag() {
        Todo t = new Todo("homework");
        Tag tag = new Tag("school");
        t.addTag(tag);
        assertNotNull(t.tags);
    }

    @Test
    void getTags() {
        Todo t = new Todo("homework");
        Tag tag = new Tag("school");
        t.addTag(tag);
        assertTrue(t.getTags().contains(tag));
    }

    @Test
    void equals_validInput_true() {
        Todo tOne = new Todo("borrow book");
        Todo tTwo = new Todo("borrow book");
        assertEquals(tOne, tTwo);
    }

    @Test
    void equals_validInput_false() {
        Todo tOne = new Todo("hello");
        Todo tTwo = new Todo("hi there");
        assertNotEquals(tOne, tTwo);

    }

    @Test
    void toString_withAllAttributesPresent() {
        Todo t = new Todo("homework");
        t.setFrequency(Frequency.DAILY);
        t.setPriority(Priority.HIGH);
        t.addTag(new Tag("help"));

        String expected = "Todo: homework\nPriority: HIGH\nFrequency: DAILY"
                            + "\nTags: [[help]]";

        assertEquals(expected, t.toString());

    }

    @Test
    void toString_withNoAttributesPresent() {
        Todo t = new Todo("homework");

        String expected = "Todo: homework";

        assertEquals(expected, t.toString());
    }

    @Test
    void getTaskDes_success() {
        Todo t = new Todo("homework");
        String expected = "homework";

        assertEquals(expected, t.getTaskDes());
    }

    @Test
    public void adaptToJsonAdaptedTodo() throws Exception {
        assertEquals(TODO, TODO.adaptToJsonAdaptedTask().toModelType());
    }

    @Test
    void markAsDone() {
        Todo t = new Todo("homework");

        t.markAsDone();

        assertEquals(Status.DONE, t.getStatus());
    }

    @Test
    void markAsNotDone() {
        Todo t = new Todo("homework");
        t.markAsDone();
        assertEquals(Status.DONE, t.getStatus());

        t.markAsNotDone();
        assertEquals(Status.NOT_DONE, t.getStatus());
    }

    @Test
    void getStatus() {
        Todo t = new Todo("homework");

        assertEquals(Status.NOT_DONE, t.getStatus());
    }

    @Test
    void getTaskType() {
        Todo t = new Todo("homework");

        TaskType expected = TaskType.TODO;
        assertEquals(expected, t.getTaskType());
    }
}
