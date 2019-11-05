package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

class TaskTest {
    @Test
    void getStatusIcon_default_returnsN() {
        assertEquals(new TaskForNote(CONAN, TaskBuilder.DEFAULT_DATE, TaskBuilder.DEFAULT_TIME).getStatusIcon(),
                "[N]");
        assertEquals(new TaskForQuestion(CONAN_QUESTION, TaskBuilder.DEFAULT_DATE, TaskBuilder.DEFAULT_TIME)
                .getStatusIcon(), "[N]");
        assertEquals(new Task(new Heading(TaskBuilder.DEFAULT_HEADING), TaskBuilder.DEFAULT_DATE,
                TaskBuilder.DEFAULT_TIME, false).getStatusIcon(), "[N]");
    }

    @Test
    void constructor_null_throwsNullPointerException() {
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
    void isValidStatusIcon_invalidIcon_returnsFalse() {
        assertFalse(isValidStatusIcon("hello"));
        assertFalse(isValidStatusIcon("1"));
        assertFalse(isValidStatusIcon(""));
        assertTrue(isValidStatusIcon("[Y]"));
        assertTrue(isValidStatusIcon("[N]"));
    }
    @Test
    void equals() {
        // same values -> returns true
        Task conanCopy = new TaskBuilder(CONAN_TASK_DEFAULT).build();
        assertEquals(CONAN_TASK_DEFAULT, conanCopy);

        // same object -> returns true
        assertEquals(CONAN_TASK_DEFAULT, CONAN_TASK_DEFAULT);

        // different type -> returns false
        assertNotEquals(5, CONAN_TASK_DEFAULT);

        // different date -> returns false
        assertNotEquals(CONAN_TASK_DEFAULT, CONAN_TASK_MODIFIED_DATE);

        //different time -> returns false
        assertNotEquals(CONAN_TASK_DEFAULT, CONAN_TASK_MODIFIED_TIME);

        //different status -> returns false
        assertNotEquals(CONAN_TASK_DEFAULT, CONAN_TASK_DONE);
    }
}
