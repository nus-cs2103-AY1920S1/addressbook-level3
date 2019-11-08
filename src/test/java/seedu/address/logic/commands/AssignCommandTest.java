package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.person.Schedule.MESSAGE_EVENT_START_BEFORE_NOW_FORMAT;
import static seedu.address.model.person.Schedule.MESSAGE_SCHEDULE_CONFLICT;
import static seedu.address.testutil.SampleEntity.THIRD_VALID_TASK_ID;
import static seedu.address.testutil.SampleEntity.VALID_DRIVER;
import static seedu.address.testutil.SampleEntity.VALID_TASK_ID;
import static seedu.address.testutil.SampleEntity.getSampleFreshModel;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.GlobalClock;
import seedu.address.model.EventTime;
import seedu.address.model.Model;
import seedu.address.model.person.Driver;
import seedu.address.model.person.SchedulingSuggestion;
import seedu.address.model.task.Task;

class AssignCommandTest {
    private Model model;

    /**
     * Sets the clock to 15 Oct 2019, 2pm.
     */
    @BeforeAll
    static void setStaticClock() {
        GlobalClock.setFixedClock();
    }

    @AfterAll
    static void setNormalClock() {
        GlobalClock.setRealClock();
    }

    @BeforeEach
    void setFreshModel() {
        this.model = getSampleFreshModel();
    }

    @Test
    void execute_addTaskNow_shouldSucceed() {
        EventTime proposed = EventTime.parse("1400", "1500");
        AssignCommand cmd = new AssignCommand(VALID_DRIVER.getId(), VALID_TASK_ID, proposed, false);

        // construct expected by setting both driver and task
        Model expectedModel = getSampleFreshModel();
        Driver targetDriver = expectedModel.getDriver(VALID_DRIVER.getId());
        Task targetTask = expectedModel.getTask(VALID_TASK_ID);

        targetDriver.getSchedule().add(proposed);
        targetTask.setDriverAndEventTime(
                Optional.of(expectedModel.getDriver(VALID_DRIVER.getId())),
                Optional.of(proposed));

        String response = AssignCommand.buildSuccessfulResponse(
                new SchedulingSuggestion("", Optional.empty(), proposed),
                targetTask,
                targetDriver,
                proposed);

        assertCommandSuccess(cmd, model, new CommandResult(response), expectedModel);
    }

    @Test
    void execute_addLateTime_shouldSucceed() {
        EventTime proposed = EventTime.parse("1600", "1700");
        AssignCommand cmd = new AssignCommand(VALID_DRIVER.getId(), VALID_TASK_ID, proposed, false);

        // construct expected by setting both driver and task
        Model expectedModel = getSampleFreshModel();
        Driver targetDriver = expectedModel.getDriver(VALID_DRIVER.getId());
        Task targetTask = expectedModel.getTask(VALID_TASK_ID);

        targetDriver.getSchedule().add(proposed);
        targetTask.setDriverAndEventTime(
                Optional.of(expectedModel.getDriver(VALID_DRIVER.getId())),
                Optional.of(proposed));

        String response = AssignCommand.buildSuccessfulResponse(
                new SchedulingSuggestion("", Optional.of(EventTime.parse("1400", "1500"))),
                targetTask,
                targetDriver,
                proposed);

        assertCommandSuccess(cmd, model, new CommandResult(response), expectedModel);
    }

    @Test
    void execute_addPastTime_throwsException() {
        EventTime proposed = EventTime.parse("900", "1100");
        AssignCommand cmd = new AssignCommand(VALID_DRIVER.getId(), VALID_TASK_ID, proposed, false);
        assertCommandFailure(cmd, model, String.format(MESSAGE_EVENT_START_BEFORE_NOW_FORMAT,
                GlobalClock.timeNow().format(EventTime.DISPLAY_TIME_FORMAT)));
    }

    @Test
    void execute_addConflictingTime_throwsException() {
        EventTime existing = EventTime.parse("1400", "1500");
        AssignCommand addExisting = new AssignCommand(VALID_DRIVER.getId(), VALID_TASK_ID, existing, false);
        try {
            addExisting.execute(model);
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventTime proposed = EventTime.parse("1430", "1600");
        AssignCommand addProposed = new AssignCommand(VALID_DRIVER.getId(), THIRD_VALID_TASK_ID, proposed, false);

        assertCommandFailure(addProposed, model, new SchedulingSuggestion(
                MESSAGE_SCHEDULE_CONFLICT,
                Optional.of(EventTime.parse("1500", "1630"))).toString());
    }

    @Test
    void executeForce_taskWithDriver_shouldSucceed() {
        EventTime existing = EventTime.parse("1400", "1500");
        AssignCommand addExisting = new AssignCommand(VALID_DRIVER.getId(), VALID_TASK_ID, existing, false);
        try {
            addExisting.execute(model);
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventTime proposed = EventTime.parse("1430", "1600");
        AssignCommand addProposed = new AssignCommand(VALID_DRIVER.getId(), VALID_TASK_ID, proposed, true);


        // construct expected by setting both driver and task
        Model expectedModel = getSampleFreshModel();
        Driver targetDriver = expectedModel.getDriver(VALID_DRIVER.getId());
        Task targetTask = expectedModel.getTask(VALID_TASK_ID);

        targetDriver.getSchedule().add(proposed);
        targetTask.setDriverAndEventTime(
                Optional.of(expectedModel.getDriver(VALID_DRIVER.getId())),
                Optional.of(proposed));

        String response = AssignCommand.buildSuccessfulResponse(
                new SchedulingSuggestion("", Optional.of(EventTime.parse("1400", "1530"))),
                targetTask,
                targetDriver,
                proposed);

        assertCommandSuccess(addProposed, model, new CommandResult(response), expectedModel);
    }


    @Test
    void executeForce_taskWithUnavailableDriver_throwsExceptionAndModelNotChanged() {
        EventTime existing1 = EventTime.parse("1400", "1500");
        EventTime existing2 = EventTime.parse("1500", "1600");

        AssignCommand cmd1 = new AssignCommand(VALID_DRIVER.getId(), VALID_TASK_ID, existing1, false);
        AssignCommand cmd2 = new AssignCommand(VALID_DRIVER.getId(), THIRD_VALID_TASK_ID, existing2, false);
        try {
            cmd1.execute(model);
            cmd2.execute(model);
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventTime proposed = EventTime.parse("1430", "1600");
        AssignCommand addProposed = new AssignCommand(VALID_DRIVER.getId(), VALID_TASK_ID, proposed, true);

        // construct expected by setting both driver and task
        Model expectedModel = getSampleFreshModel();
        try {
            cmd1.execute(expectedModel);
            cmd2.execute(expectedModel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertCommandFailure(addProposed, model, new SchedulingSuggestion(
                MESSAGE_SCHEDULE_CONFLICT,
                Optional.of(EventTime.parse("1600", "1730"))).toString());

        assertEquals(expectedModel, model);
    }

}
