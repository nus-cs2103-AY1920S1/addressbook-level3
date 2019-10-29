package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.SampleEntity.SECOND_VALID_CUSTOMER;
import static seedu.address.testutil.SampleEntity.SECOND_VALID_DESCRIPTION;
import static seedu.address.testutil.SampleEntity.SECOND_VALID_DRIVER;
import static seedu.address.testutil.SampleEntity.SECOND_VALID_EVENT_TIME;
import static seedu.address.testutil.SampleEntity.SECOND_VALID_LOCAL_DATE;
import static seedu.address.testutil.SampleEntity.SECOND_VALID_TASK_ID;
import static seedu.address.testutil.SampleEntity.VALID_CUSTOMER;
import static seedu.address.testutil.SampleEntity.VALID_DESCRIPTION;
import static seedu.address.testutil.SampleEntity.VALID_DRIVER;
import static seedu.address.testutil.SampleEntity.VALID_EVENT_TIME;
import static seedu.address.testutil.SampleEntity.VALID_LOCAL_DATE;
import static seedu.address.testutil.SampleEntity.VALID_TASK_ID;
import static seedu.address.testutil.SampleEntity.getCompleteTask;
import static seedu.address.testutil.SampleEntity.getFirstSampleCompletedTask;
import static seedu.address.testutil.SampleEntity.getIncompleteTask;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.task.execeptions.TaskException;

public class TaskTest {
    //featureUnderTest_testScenario_expectedBehavior()

    @Test
    public void isValidId() {
        //isValidId should only work for number and more than 0.
        String number = "3";
        assertTrue(Task.isValidId(number));

        String zero = "0";
        assertFalse(Task.isValidId(zero));

        String strWithLetters = "3d";
        assertFalse(Task.isValidId(strWithLetters));

        String strWithSpace = "    ";
        assertFalse(Task.isValidId(strWithSpace));

        String nullValue = null;
        assertFalse(Task.isValidId(nullValue));
    }

    @Test
    public void setDriver() {
        Task newTask = getIncompleteTask(VALID_TASK_ID, VALID_DESCRIPTION, VALID_LOCAL_DATE);
        newTask.setDriver(Optional.of(VALID_DRIVER));

        assertEquals(VALID_DRIVER, newTask.getDriver().get());
    }

    @Test
    public void setEventTime() {
        Task newTask = getIncompleteTask(VALID_TASK_ID, VALID_DESCRIPTION, VALID_LOCAL_DATE);
        newTask.setEventTime(Optional.of(VALID_EVENT_TIME));

        assertEquals(VALID_EVENT_TIME, newTask.getEventTime().get());
    }

    @Test
    public void testEquals() {
        Task expectedTask = getFirstSampleCompletedTask();
        Task taskToCompare = getFirstSampleCompletedTask();

        assertEquals(expectedTask, taskToCompare);

        //test different task id -> false
        Task taskWithAnotherTaskId = getCompleteTask(SECOND_VALID_TASK_ID, VALID_DESCRIPTION, VALID_LOCAL_DATE,
                VALID_CUSTOMER, VALID_DRIVER, VALID_EVENT_TIME);
        assertNotEquals(expectedTask, taskWithAnotherTaskId);

        //test different description -> false
        Task taskWithAnotherDescription = getFirstSampleCompletedTask();
        taskWithAnotherDescription.setDescription(SECOND_VALID_DESCRIPTION);
        assertNotEquals(expectedTask, taskWithAnotherDescription);

        //test different local date -> false
        Task taskWithAnotherDate = getFirstSampleCompletedTask();
        taskWithAnotherDate.setDate(SECOND_VALID_LOCAL_DATE);
        assertNotEquals(expectedTask, taskWithAnotherDate);

        //test different customer -> false
        Task taskWithAnotherCustomer = getFirstSampleCompletedTask();
        taskWithAnotherCustomer.setCustomer(SECOND_VALID_CUSTOMER);
        assertNotEquals(expectedTask, taskWithAnotherCustomer);

        //test different driver -> false
        Task taskWithAnotherDriver = getFirstSampleCompletedTask();
        taskWithAnotherDriver.setDriver(Optional.of(SECOND_VALID_DRIVER));
        assertNotEquals(expectedTask, taskWithAnotherDriver);

        //test different event time -> false
        Task taskWithAnotherEventTime = getFirstSampleCompletedTask();
        taskWithAnotherEventTime.setEventTime(Optional.of(SECOND_VALID_EVENT_TIME));
        assertNotEquals(expectedTask, taskWithAnotherEventTime);
    }

    @Test
    public void setStatus_noChangeInStatus_taskExceptionThrown() {
        Task sampleTask = getFirstSampleCompletedTask();

        assertThrows(TaskException.class, () -> sampleTask.setStatus(TaskStatus.COMPLETED));
    }
}
