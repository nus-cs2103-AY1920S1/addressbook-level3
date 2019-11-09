package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.EditTaskCommand.MESSAGE_CANNOT_BE_EDITED;
import static seedu.address.logic.commands.EditTaskCommand.MESSAGE_DATE_IS_BEFORE;
import static seedu.address.logic.commands.EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS;
import static seedu.address.logic.commands.EditTaskCommand.MESSAGE_NOTHING_TO_EDIT;
import static seedu.address.testutil.SampleEntity.FOURTH_VALID_CUSTOMER;
import static seedu.address.testutil.SampleEntity.FOURTH_VALID_DESCRIPTION;
import static seedu.address.testutil.SampleEntity.FOURTH_VALID_EVENT_TIME;
import static seedu.address.testutil.SampleEntity.FOURTH_VALID_LOCAL_DATE;
import static seedu.address.testutil.SampleEntity.FOURTH_VALID_TASK_ID;
import static seedu.address.testutil.SampleEntity.SECOND_VALID_CUSTOMER;
import static seedu.address.testutil.SampleEntity.SECOND_VALID_DESCRIPTION;
import static seedu.address.testutil.SampleEntity.VALID_CUSTOMER;
import static seedu.address.testutil.SampleEntity.VALID_DAY_AFTER_TODAY;
import static seedu.address.testutil.SampleEntity.VALID_DAY_BEFORE_TODAY;
import static seedu.address.testutil.SampleEntity.VALID_DRIVER;
import static seedu.address.testutil.SampleEntity.VALID_TODAY_DATE;
import static seedu.address.testutil.SampleEntity.getFourthSampleCompletedTask;
import static seedu.address.testutil.SampleEntity.getOnGoingTask;
import static seedu.address.testutil.SampleEntity.getSampleFreshModel;
import static seedu.address.testutil.SampleEntity.getUnassignedTask;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.GlobalClock;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.model.Model;
import seedu.address.model.person.Driver;
import seedu.address.model.person.Schedule;
import seedu.address.model.task.Task;
import seedu.address.testutil.EditTaskDescriptorBuilder;


class EditTaskCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeAll
    static void setTodayToFixedDate() {
        GlobalClock.setFixedClock();
    }

    @AfterAll
    static void setClockBackToRealTime() {
        GlobalClock.setRealClock();
    }

    @BeforeEach
    void setUp() {
        //get a sample data thats contains first and second valid tasks that is not assigned
        model = getSampleFreshModel();
        expectedModel = getSampleFreshModel();
    }

    @Test
    void execute_invalidTaskId_invalidId() {
        Task sampleTask = getUnassignedTask(FOURTH_VALID_TASK_ID, FOURTH_VALID_DESCRIPTION, FOURTH_VALID_LOCAL_DATE,
                FOURTH_VALID_CUSTOMER);
        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptorBuilder(sampleTask).build();

        //sample data task id only has 1 and 2
        int invalidId = 1000;
        EditTaskCommand editTaskCommand = new EditTaskCommand(invalidId, editTaskDescriptor);
        String expectedMessage = Task.MESSAGE_INVALID_ID;
        assertCommandFailure(editTaskCommand, model, expectedMessage);
    }

    @Test
    void execute_editToSameValue_nothingEdited() {
        Task sampleTask = getUnassignedTask(FOURTH_VALID_TASK_ID, FOURTH_VALID_DESCRIPTION, FOURTH_VALID_LOCAL_DATE,
                VALID_CUSTOMER);
        model.addTask(sampleTask);
        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptorBuilder(sampleTask).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(FOURTH_VALID_TASK_ID, editTaskDescriptor);
        assertCommandFailure(editTaskCommand, model, MESSAGE_NOTHING_TO_EDIT);
    }

    @Test
    void execute_editOnlyDescription_success() {
        //change only description, same customer, and date
        Task sampleTask = getUnassignedTask(FOURTH_VALID_TASK_ID, FOURTH_VALID_DESCRIPTION, FOURTH_VALID_LOCAL_DATE,
                VALID_CUSTOMER);
        model.addTask(sampleTask);
        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptorBuilder(sampleTask)
                .changeDescriptionTo(SECOND_VALID_DESCRIPTION).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(FOURTH_VALID_TASK_ID, editTaskDescriptor);

        //expected model
        //change FOURTH_VALID_DESCRIPTION to SECOND_VALID_DESCRIPTION
        Task expectedTask = getUnassignedTask(FOURTH_VALID_TASK_ID, SECOND_VALID_DESCRIPTION, FOURTH_VALID_LOCAL_DATE,
                VALID_CUSTOMER);
        expectedModel.addTask(expectedTask);
        String expectedMessage = String.format(MESSAGE_EDIT_TASK_SUCCESS, expectedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_editOnlyCustomer_success() {
        //change only customer, same description and date
        Task sampleTask = getUnassignedTask(FOURTH_VALID_TASK_ID, FOURTH_VALID_DESCRIPTION, FOURTH_VALID_LOCAL_DATE,
                VALID_CUSTOMER);
        model.addTask(sampleTask);
        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptorBuilder(sampleTask)
                .changeCustomerTo(SECOND_VALID_CUSTOMER.getId()).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(FOURTH_VALID_TASK_ID, editTaskDescriptor);

        //expected model
        //change VALID_CUSTOMER to SECOND_VALID_CUSTOMER
        Task expectedTask = getUnassignedTask(FOURTH_VALID_TASK_ID, FOURTH_VALID_DESCRIPTION, FOURTH_VALID_LOCAL_DATE,
                SECOND_VALID_CUSTOMER);
        expectedModel.addTask(expectedTask);
        String expectedMessage = String.format(MESSAGE_EDIT_TASK_SUCCESS, expectedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_editToDateBeforeToday_updatedDateCannotBeBeforeToday() {
        //edit to before today
        Task sampleTask = getUnassignedTask(FOURTH_VALID_TASK_ID, FOURTH_VALID_DESCRIPTION, FOURTH_VALID_LOCAL_DATE,
                VALID_CUSTOMER);
        model.addTask(sampleTask);
        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptorBuilder(sampleTask)
                .changeDateTo(VALID_DAY_BEFORE_TODAY).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(FOURTH_VALID_TASK_ID, editTaskDescriptor);

        assertCommandFailure(editTaskCommand, model, MESSAGE_DATE_IS_BEFORE);
    }

    @Test
    void execute_editToDateToday_success() {
        //task that is day before today, edit to today date
        Task sampleTask = getUnassignedTask(FOURTH_VALID_TASK_ID, FOURTH_VALID_DESCRIPTION, FOURTH_VALID_LOCAL_DATE,
                VALID_CUSTOMER);
        model.addTask(sampleTask);
        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptorBuilder(sampleTask)
                .changeDateTo(VALID_TODAY_DATE).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(FOURTH_VALID_TASK_ID, editTaskDescriptor);

        //expected model
        //change FOURTH_VALID_LOCAL_DATE to VALID_TODAY_DATE
        Task expectedTask = getUnassignedTask(FOURTH_VALID_TASK_ID, FOURTH_VALID_DESCRIPTION, VALID_TODAY_DATE,
                VALID_CUSTOMER);
        expectedModel.addTask(expectedTask);
        String expectedMessage = String.format(MESSAGE_EDIT_TASK_SUCCESS, expectedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_editToDateAfterToday_success() {
        //task that is today, edit to day after today
        Task sampleTask = getUnassignedTask(FOURTH_VALID_TASK_ID, FOURTH_VALID_DESCRIPTION, VALID_TODAY_DATE,
                VALID_CUSTOMER);
        model.addTask(sampleTask);
        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptorBuilder(sampleTask)
                .changeDateTo(VALID_DAY_AFTER_TODAY).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(FOURTH_VALID_TASK_ID, editTaskDescriptor);

        //expected model
        Task expectedTask = getUnassignedTask(FOURTH_VALID_TASK_ID, FOURTH_VALID_DESCRIPTION, VALID_DAY_AFTER_TODAY,
                VALID_CUSTOMER);
        expectedModel.addTask(expectedTask);
        String expectedMessage = String.format(MESSAGE_EDIT_TASK_SUCCESS, expectedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_editCompletedTask_completedTaskCannotEdit() {
        Task completedTask = getFourthSampleCompletedTask();
        model.addTask(completedTask);
        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptorBuilder(completedTask).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(FOURTH_VALID_TASK_ID, editTaskDescriptor);

        assertCommandFailure(editTaskCommand, model, MESSAGE_CANNOT_BE_EDITED);
    }

    @Test
    void execute_editDateToLaterDateForOnGoingTask_success() {
        //edit ongoing task's date (also checks if the driver is freed)
        Task onGoingTask = getOnGoingTask(FOURTH_VALID_TASK_ID, FOURTH_VALID_DESCRIPTION, VALID_CUSTOMER,
                VALID_DRIVER, FOURTH_VALID_EVENT_TIME);
        model.addTask(onGoingTask);
        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptorBuilder(onGoingTask)
                .changeDateTo(VALID_DAY_AFTER_TODAY).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(FOURTH_VALID_TASK_ID, editTaskDescriptor);

        //on going task driver -> driver schedule should not be available at that time.
        Driver onGoingTaskDriver = onGoingTask.getDriver().get();
        Schedule driverSchedule = onGoingTaskDriver.getSchedule();
        assertFalse(driverSchedule.isAvailable(FOURTH_VALID_EVENT_TIME));

        //expected model
        //change Ongoing task's today date to VALID_DAY_AFTER_TODAY
        Task expectedOnGoingTask = getUnassignedTask(FOURTH_VALID_TASK_ID, FOURTH_VALID_DESCRIPTION,
                VALID_DAY_AFTER_TODAY, VALID_CUSTOMER);
        expectedModel.addTask(expectedOnGoingTask);
        String expectedMessage = String.format(MESSAGE_EDIT_TASK_SUCCESS, expectedOnGoingTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);

        //after changing delivery date, driver should be available now
        assertTrue(driverSchedule.isAvailable(FOURTH_VALID_EVENT_TIME));
    }
}
