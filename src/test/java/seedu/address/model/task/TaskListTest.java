package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.CS2100;
import static seedu.address.testutil.TypicalTasks.CS2103T;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;
import seedu.address.testutil.TaskBuilder;

public class TaskListTest {
    private static final String VALID_PICTURE_BOB = "bob.jpg";
    private final TaskList taskList = new TaskList();

    @Test
    public void contains_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.contains(null));
    }

    @Test
    public void contains_taskNotInList_returnsFalse() {
        assertFalse(taskList.contains(CS2103T));
    }

    @Test
    public void contains_taskInList_returnsTrue() {
        taskList.add(CS2103T);
        assertTrue(taskList.contains(CS2103T));
    }

    @Test
    public void contains_taskWithSameIdentityFieldsInList_returnsTrue() {
        taskList.add(CS2103T);
        Task editedCS2103T = new TaskBuilder(CS2103T).build();
        assertTrue(taskList.contains(editedCS2103T));
    }

    @Test
    public void add_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.add(null));
    }

    @Test
    public void add_duplicateTask_throwsDuplicateTaskException() {
        taskList.add(CS2103T);
        assertThrows(DuplicateTaskException.class, () -> taskList.add(CS2103T));
    }

    @Test
    public void setTask_nullTargetTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.setTask(null, CS2103T));
    }

    @Test
    public void setTask_nullEditedTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.setTask(CS2103T, null));
    }

    @Test
    public void setTask_targetTaskNotInList_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> taskList.setTask(CS2103T, CS2103T));
    }

    @Test
    public void setTask_editedTaskIsSameTask_success() {
        taskList.add(CS2103T);
        taskList.setTask(CS2103T, CS2103T);
        TaskList expectedTaskList = new TaskList();
        expectedTaskList.add(CS2103T);
        assertEquals(expectedTaskList, taskList);
    }

    @Test
    public void setTask_editedTaskHasSameIdentity_success() {
        taskList.add(CS2103T);
        Task editedCS2103T = new TaskBuilder(CS2103T).build();
        taskList.setTask(CS2103T, editedCS2103T);
        TaskList expectedTaskList = new TaskList();
        expectedTaskList.add(editedCS2103T);
        assertEquals(expectedTaskList, taskList);
    }

    @Test
    public void setTask_editedTaskHasDifferentIdentity_success() {
        taskList.add(CS2103T);
        taskList.setTask(CS2103T, CS2100);
        TaskList expectedTaskList = new TaskList();
        expectedTaskList.add(CS2100);
        assertEquals(expectedTaskList, taskList);
    }

    @Test
    public void setTask_editedTaskHasNonUniqueIdentity_throwsDuplicateTaskException() {
        taskList.add(CS2103T);
        taskList.add(CS2100);
        assertThrows(DuplicateTaskException.class, () -> taskList.setTask(CS2103T, CS2100));
    }

    @Test
    public void remove_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.remove(null));
    }

    @Test
    public void remove_taskDoesNotExist_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> taskList.remove(CS2103T));
    }

    @Test
    public void remove_existingTask_removesTask() {
        taskList.add(CS2100);
        taskList.remove(CS2100);
        TaskList expectedTaskList = new TaskList();
        assertEquals(expectedTaskList, taskList);
    }

    @Test
    public void setTasks_nullTaskList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.setTasks((TaskList) null));
    }

    @Test
    public void setTasks_taskList_replacesOwnListWithProvidedTaskList() {
        taskList.add(CS2103T);
        TaskList expectedTaskList = new TaskList();
        expectedTaskList.add(CS2100);
        taskList.setTasks(expectedTaskList);
        assertEquals(expectedTaskList, taskList);
    }

    @Test
    public void setTasks_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.setTasks((List<Task>) null));
    }

    @Test
    public void setTasks_list_replacesOwnListWithProvidedList() {
        taskList.add(CS2103T);
        List<Task> newTaskList = Collections.singletonList(CS2100);
        taskList.setTasks(newTaskList);
        TaskList expectedTaskList = new TaskList();
        expectedTaskList.add(CS2100);
        assertEquals(expectedTaskList, taskList);
    }

    @Test
    public void setTasks_listWithDuplicateTasks_throwsDuplicateTaskException() {
        List<Task> listWithDuplicateTasks = Arrays.asList(CS2100, CS2100);
        assertThrows(DuplicateTaskException.class, () -> taskList.setTasks(listWithDuplicateTasks));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> taskList.asUnmodifiableObservableList().remove(0));
    }
}
