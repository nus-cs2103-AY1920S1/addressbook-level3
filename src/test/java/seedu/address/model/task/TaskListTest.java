package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.SampleEntity.SECOND_VALID_LOCAL_DATE;
import static seedu.address.testutil.SampleEntity.SECOND_VALID_TASK_ID;
import static seedu.address.testutil.SampleEntity.VALID_TASK_ID;
import static seedu.address.testutil.SampleEntity.getFirstSampleCompletedTask;
import static seedu.address.testutil.SampleEntity.getSecondSampleCompletedTask;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.task.execeptions.TaskNotFoundException;

public class TaskListTest {

    private TaskList tasks;

    @BeforeEach
    public void setUp() {
        tasks = new TaskList();
        Task newTask = getFirstSampleCompletedTask();
        tasks.addTask(newTask);
    }

    @Test
    public void getTask() {
        Task expectedTask = getFirstSampleCompletedTask();
        Task taskFound = tasks.getTask(VALID_TASK_ID);
        assertEquals(expectedTask, taskFound);
    }

    @Test
    public void getTask_noTaskFound_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> tasks.getTask(SECOND_VALID_TASK_ID));
    }

    @Test
    public void hasTask() {
        Task taskToFind = getFirstSampleCompletedTask();
        assertTrue(tasks.hasTask(taskToFind));

        Task taskNotInList = getSecondSampleCompletedTask();
        assertFalse(tasks.hasTask(taskNotInList));
    }

    @Test
    public void hasTask_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> tasks.hasTask(null));
    }

    @Test
    public void deleteTask() {
        Task newTask = getSecondSampleCompletedTask();
        tasks.addTask(newTask);
        tasks.deleteTask(newTask);
        assertFalse(tasks.hasTask(newTask));
    }

    @Test
    public void deleteTask_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> tasks.deleteTask(null));
    }

    @Test
    public void deleteTask_taskNotFound_throwsTaskNotFoundException() {
        Task taskNotInList = getSecondSampleCompletedTask();
        assertThrows(TaskNotFoundException.class, () -> tasks.deleteTask(taskNotInList));
    }

    @Test
    public void setTask() {
        Task taskToEdit = tasks.getTask(VALID_TASK_ID);
        Task editedTask = getFirstSampleCompletedTask();
        editedTask.setDate(SECOND_VALID_LOCAL_DATE);

        tasks.setTask(taskToEdit, editedTask);
        Task updatedTask = tasks.getTask(VALID_TASK_ID);

        assertEquals(editedTask, updatedTask);
    }

    @Test
    public void setTask_nullTask_throwsNullPointerException() {
        //if task to be edited is null
        assertThrows(NullPointerException.class, () -> tasks.setTask(tasks.getTask(VALID_TASK_ID), null));

        //if edited task is null
        Task editedTask = getFirstSampleCompletedTask();
        editedTask.setDate(SECOND_VALID_LOCAL_DATE);
        assertThrows(NullPointerException.class, () -> tasks.setTask(null, editedTask));
    }

    @Test
    public void setTask_taskToEditNotFound_throwsTaskNotFoundException() {
        Task taskNotInList = getSecondSampleCompletedTask();
        Task editedTask = getSecondSampleCompletedTask();
        editedTask.setDate(SECOND_VALID_LOCAL_DATE);
        assertThrows(TaskNotFoundException.class, () -> tasks.setTask(taskNotInList, editedTask));
    }
}
