package seedu.address.logic.commands;

import static seedu.address.logic.commands.AssignCommand.MESSAGE_ASSIGN_SUCCESS;
import static seedu.address.logic.commands.AssignCommand.MESSAGE_EVENT_START_BEFORE_NOW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
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
import seedu.address.model.person.Schedule;

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
        expectedModel.getDriver(VALID_DRIVER.getId()).getSchedule().add(proposed);
        expectedModel.getTask(VALID_TASK_ID)
                .setDriverAndEventTime(Optional.of(expectedModel.getDriver(VALID_DRIVER.getId())),
                        Optional.of(proposed));

        assertCommandSuccess(cmd, model, new CommandResult(String.format(MESSAGE_ASSIGN_SUCCESS, VALID_TASK_ID,
                VALID_DRIVER.getName().fullName, proposed)), expectedModel);
    }

    @Test
    void execute_addLateTime_shouldSuggestsTimeThrowsException() {
        EventTime proposed = EventTime.parse("1600", "1700");
        AssignCommand cmd = new AssignCommand(VALID_DRIVER.getId(), VALID_TASK_ID, proposed, false);

        assertCommandFailure(cmd, model,
                String.format(Schedule.MESSAGE_SUGGEST_TIME_FORMAT, EventTime.parse("1400", "1500").toString()));
    }


    @Test
    void executeForce_addLateTime_shouldSucceed() {
        EventTime proposed = EventTime.parse("1600", "1700");
        AssignCommand cmd = new AssignCommand(VALID_DRIVER.getId(), VALID_TASK_ID, proposed, true);

        // construct expected by setting both driver and task
        Model expectedModel = getSampleFreshModel();
        expectedModel.getDriver(VALID_DRIVER.getId()).getSchedule().add(proposed);
        expectedModel.getTask(VALID_TASK_ID)
                .setDriverAndEventTime(Optional.of(expectedModel.getDriver(VALID_DRIVER.getId())),
                        Optional.of(proposed));

        assertCommandSuccess(cmd, model, new CommandResult(String.format(MESSAGE_ASSIGN_SUCCESS, VALID_TASK_ID,
                VALID_DRIVER.getName().fullName, proposed)), expectedModel);
    }

    @Test
    void execute_addPastTime_throwsException() {
        EventTime proposed = EventTime.parse("900", "1100");
        AssignCommand cmd = new AssignCommand(VALID_DRIVER.getId(), VALID_TASK_ID, proposed, false);
        assertCommandFailure(cmd, model, MESSAGE_EVENT_START_BEFORE_NOW);
    }


}
