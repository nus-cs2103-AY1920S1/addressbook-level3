package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppData.CONAN_TASK_DEFAULT;
import static seedu.address.testutil.TypicalAppData.CONAN_TASK_DONE;
import static seedu.address.testutil.TypicalAppData.CONAN_TASK_FOR_NOTE;
import static seedu.address.testutil.TypicalAppData.CONAN_TASK_MODIFIED_DATE;
import static seedu.address.testutil.TypicalAppData.CONAN_TASK_MODIFIED_TIME;

import org.junit.jupiter.api.Test;

import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

class TaskListTest {
    private final TaskList taskList = new TaskList();

    @Test
    void contains_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.contains(null));
    }

    @Test
    void contains_taskNotInList_returnsFalse() {
        assertFalse(taskList.contains(CONAN_TASK_DEFAULT));
    }

    @Test
    void contains_taskInList_returnsTrue() {
        taskList.add(CONAN_TASK_DEFAULT);
        assertTrue(taskList.contains(CONAN_TASK_DEFAULT));
    }

    @Test
    void add_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.add(null));
    }

    @Test
    void add_duplicateTask_throwsDuplicateTaskException() {
        taskList.add(CONAN_TASK_DEFAULT);
        assertThrows(DuplicateTaskException.class, () -> taskList.add(CONAN_TASK_DEFAULT));
    }

    @Test
    void setTask_nullTargetTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.setTask(null, CONAN_TASK_DEFAULT));
    }

    @Test
    void setTask_nullEditedTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.setTask(CONAN_TASK_DONE, null));
    }

    @Test
    void setTask_targetTaskNotInList_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> taskList.setTask(CONAN_TASK_DEFAULT, CONAN_TASK_DEFAULT));
    }

    @Test
    void setTask_editedTaskHasNonUniqueIdentity_throwsDuplicateTaskException() {
        taskList.add(CONAN_TASK_DEFAULT);
        taskList.add(CONAN_TASK_DONE);
        assertThrows(DuplicateTaskException.class, () -> taskList.setTask(CONAN_TASK_DEFAULT, CONAN_TASK_DONE));
    }

    @Test
    void setTask_editedTaskHasDifferentDate_success() {
        taskList.add(CONAN_TASK_FOR_NOTE);
        taskList.setTask(CONAN_TASK_FOR_NOTE, CONAN_TASK_MODIFIED_DATE);
        TaskList expectedTaskList = new TaskList();
        expectedTaskList.add(CONAN_TASK_MODIFIED_DATE);
        assertEquals(taskList, expectedTaskList);
    }

    @Test
    void setTask_editedTaskHasDifferentTime_success() {
        taskList.add(CONAN_TASK_FOR_NOTE);
        taskList.setTask(CONAN_TASK_FOR_NOTE, CONAN_TASK_MODIFIED_TIME);
        TaskList expectedTaskList = new TaskList();
        expectedTaskList.add(CONAN_TASK_MODIFIED_TIME);
        assertEquals(taskList, expectedTaskList);
    }

    @Test
    void setTask_editedTaskHasDifferentStatus_success() {
        taskList.add(CONAN_TASK_FOR_NOTE);
        taskList.setTask(CONAN_TASK_FOR_NOTE, CONAN_TASK_FOR_NOTE.markAsDone());
        TaskList expectedTaskList = new TaskList();
        expectedTaskList.add(CONAN_TASK_FOR_NOTE); //isDone field of CONAN_TASK_FOR_NOTE has been changed to true
        assertEquals(taskList, expectedTaskList);
        CONAN_TASK_FOR_NOTE.markAsNotDone();
    }

    @Test
    void remove_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.remove(null));
    }

    @Test
    void remove_taskDoesNotExist_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> taskList.remove(CONAN_TASK_DEFAULT));
    }

    @Test
    void remove_existingTask_removesTask() {
        taskList.add(CONAN_TASK_DEFAULT);
        taskList.remove(CONAN_TASK_DEFAULT);
        TaskList expectedTaskList = new TaskList();
        assertEquals(taskList, expectedTaskList);
    }
}
