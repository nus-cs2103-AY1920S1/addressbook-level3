package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.task.Task.isValidStatusIcon;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppData.CONAN;
import static seedu.address.testutil.TypicalAppData.CONAN_QUESTION;
import static seedu.address.testutil.TypicalAppData.CONAN_TASK_DEFAULT;
import static seedu.address.testutil.TypicalAppData.CONAN_TASK_DONE;
import static seedu.address.testutil.TypicalAppData.CONAN_TASK_MODIFIED_DATE;
import static seedu.address.testutil.TypicalAppData.CONAN_TASK_MODIFIED_TIME;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TaskBuilder;

public class TaskTest {

    @Test
    public void getStatusIcon_default_returnsN() {
        assertEquals(new TaskForNote(CONAN, TaskBuilder.DEFAULT_DATE, TaskBuilder.DEFAULT_TIME).getStatusIcon(),
                "[N]");
        assertEquals(new TaskForQuestion(CONAN_QUESTION, TaskBuilder.DEFAULT_DATE, TaskBuilder.DEFAULT_TIME)
                .getStatusIcon(), "[N]");
        assertEquals(new Task(new Heading(TaskBuilder.DEFAULT_HEADING), TaskBuilder.DEFAULT_DATE,
                TaskBuilder.DEFAULT_TIME, false).getStatusIcon(), "[N]");
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        //null date
        assertThrows(NullPointerException.class, () -> new TaskForNote(CONAN, null, TaskBuilder.DEFAULT_TIME));
        assertThrows(NullPointerException.class, () -> new TaskForQuestion(CONAN_QUESTION, null,
                TaskBuilder.DEFAULT_TIME));
        assertThrows(NullPointerException.class, () -> new Task(new Heading(TaskBuilder.DEFAULT_HEADING), null,
                TaskBuilder.DEFAULT_TIME, false));

        //null time
        assertThrows(NullPointerException.class, () -> new TaskForNote(CONAN, TaskBuilder.DEFAULT_DATE, null));
        assertThrows(NullPointerException.class, () -> new TaskForQuestion(CONAN_QUESTION, TaskBuilder.DEFAULT_DATE,
                null));
        assertThrows(NullPointerException.class, () -> new Task(new Heading(TaskBuilder.DEFAULT_HEADING),
                TaskBuilder.DEFAULT_DATE, null, false));

        //null note
        assertThrows(NullPointerException.class, () -> new TaskForNote(null, TaskBuilder.DEFAULT_DATE,
                TaskBuilder.DEFAULT_TIME));

        //null question
        assertThrows(NullPointerException.class, () -> new TaskForQuestion(null, TaskBuilder.DEFAULT_DATE,
                TaskBuilder.DEFAULT_TIME));

        //null heading
        assertThrows(NullPointerException.class, () -> new Task(null, TaskBuilder.DEFAULT_DATE,
                TaskBuilder.DEFAULT_TIME, false));
    }

    @Test
    public void isValidStatusIcon_invalidIcon_returnsFalse() {
        assertFalse(isValidStatusIcon("hello"));
        assertFalse(isValidStatusIcon("1"));
        assertFalse(isValidStatusIcon(""));
        assertTrue(isValidStatusIcon("[Y]"));
        assertTrue(isValidStatusIcon("[N]"));
    }
    @Test
    public void equals() {
        // same values -> returns true
        Task conanCopy = new TaskBuilder(CONAN_TASK_DEFAULT).build();
        assertTrue(CONAN_TASK_DEFAULT.equals(conanCopy));

        // same object -> returns true
        assertTrue(CONAN_TASK_DEFAULT.equals(CONAN_TASK_DEFAULT));

        // different type -> returns false
        assertFalse(CONAN_TASK_DEFAULT.equals(5));

        // different date -> returns false
        assertFalse(CONAN_TASK_DEFAULT.equals(CONAN_TASK_MODIFIED_DATE));

        //different time -> returns false
        assertFalse(CONAN_TASK_DEFAULT.equals(CONAN_TASK_MODIFIED_TIME));

        //different status -> returns false
        assertFalse(CONAN_TASK_DEFAULT.equals(CONAN_TASK_DONE));
    }
}
