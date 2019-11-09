package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DoneCommand.MESSAGE_MARK_TASK_COMPLETED;
import static seedu.address.logic.commands.DoneCommand.MESSAGE_TASK_NOT_ONGOING;
import static seedu.address.testutil.SampleEntity.FOURTH_VALID_CUSTOMER;
import static seedu.address.testutil.SampleEntity.FOURTH_VALID_DESCRIPTION;
import static seedu.address.testutil.SampleEntity.FOURTH_VALID_EVENT_TIME;
import static seedu.address.testutil.SampleEntity.FOURTH_VALID_TASK_ID;
import static seedu.address.testutil.SampleEntity.VALID_TASK_ID;
import static seedu.address.testutil.SampleEntity.getFourthSampleCompletedTask;
import static seedu.address.testutil.SampleEntity.getFourthSampleDriver;
import static seedu.address.testutil.SampleEntity.getFourthSampleOnGoingTask;
import static seedu.address.testutil.SampleEntity.getSampleFreshModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.GlobalClock;
import seedu.address.model.Model;
import seedu.address.model.person.Driver;
import seedu.address.model.person.Schedule;
import seedu.address.model.task.Task;
import seedu.address.testutil.SampleEntity;

class DoneCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    void setUp() {
        //get a sample data with no assigned tasks
        model = getSampleFreshModel();
        expectedModel = getSampleFreshModel();
    }

    @Test
    void execute_markIncompleteTaskAsDone_cannotMarkNotOnGoingTask() {
        DoneCommand doneCommand = new DoneCommand(VALID_TASK_ID);
        Task incompleteTask = model.getTask(VALID_TASK_ID);
        String expectedMessage = String.format(MESSAGE_TASK_NOT_ONGOING, incompleteTask.getStatus());
        assertCommandFailure(doneCommand, model, expectedMessage);
    }

    @Test
    void execute_markCompletedTaskAsDone_cannotMarkNotOnGoingTask() {
        //add a completed task into model
        Task completedTask = getFourthSampleCompletedTask();
        model.addTask(completedTask);

        DoneCommand doneCommand = new DoneCommand(FOURTH_VALID_TASK_ID);
        String expectedMessage = String.format(Task.MESSAGE_TASK_IS_ALREADY_COMPLETED, completedTask.getStatus());
        assertCommandFailure(doneCommand, model, expectedMessage);
    }

    @Test
    void execute_markOnGoingTaskAsDone_successfullyMarked() {
        //add an ongoing task into model
        Task onGoingTask = getFourthSampleOnGoingTask();
        model.addTask(onGoingTask);

        //on going task driver -> driver schedule should not be available at that time.
        Driver onGoingTaskDriver = onGoingTask.getDriver().get();
        Schedule driverSchedule = onGoingTaskDriver.getSchedule();
        assertFalse(driverSchedule.isAvailable(FOURTH_VALID_EVENT_TIME));

        DoneCommand doneCommand = new DoneCommand(FOURTH_VALID_TASK_ID);
        String expectedMessage = String.format(MESSAGE_MARK_TASK_COMPLETED, onGoingTask.getId());

        Task expectedTask = SampleEntity.getCompleteTask(FOURTH_VALID_TASK_ID, FOURTH_VALID_DESCRIPTION,
                GlobalClock.getStaticDate(), FOURTH_VALID_CUSTOMER, getFourthSampleDriver(), FOURTH_VALID_EVENT_TIME);
        expectedModel.addTask(expectedTask);

        assertCommandSuccess(doneCommand, model, expectedMessage, expectedModel);

        //check if driver schedule is free for that duration after task is done.
        assertTrue(driverSchedule.isAvailable(FOURTH_VALID_EVENT_TIME));
    }
}
