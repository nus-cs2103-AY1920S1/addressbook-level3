package seedu.exercise.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;
import static seedu.exercise.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.MainApp;
import seedu.exercise.commons.core.State;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.resource.Schedule;
import seedu.exercise.testutil.typicalutil.TypicalConflict;
import seedu.exercise.testutil.typicalutil.TypicalDates;
import seedu.exercise.testutil.typicalutil.TypicalRegime;
import seedu.exercise.testutil.typicalutil.TypicalSchedule;
import seedu.exercise.ui.ListResourceType;

//@@author t-cheepeng
public class ScheduleRegimeCommandTest {

    private final ScheduleCommand scheduleRegimeCommandWithUnknownRegime =
        new ScheduleRegimeCommand(new Name(TypicalRegime.VALID_REGIME_NAME_CHEST),
            TypicalDates.DATE_3);
    private final ScheduleCommand scheduleRegimeCommandWithConflictingDate =
        new ScheduleRegimeCommand(new Name(TypicalRegime.VALID_REGIME_NAME_LEGS),
            TypicalDates.DATE_1);
    private final ScheduleCommand scheduleRegimeCommandWithEpochDate =
        new ScheduleRegimeCommand(new Name(TypicalRegime.VALID_REGIME_NAME_CARDIO),
            TypicalDates.DATE_EPOCH);
    private final ScheduleCommand validScheduleRegimeCommand =
        new ScheduleRegimeCommand(new Name(TypicalRegime.VALID_REGIME_NAME_CARDIO),
            TypicalDates.DATE_3);

    private Model model;

    @BeforeEach
    public void setUp() {
        MainApp.setState(State.NORMAL);
        model = new ModelManager(new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
                TypicalRegime.getTypicalRegimeBook(),
                new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
                TypicalSchedule.getTypicalScheduleBook(), new UserPrefs());
    }

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ScheduleRegimeCommand(null, null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> validScheduleRegimeCommand.execute(null));
    }

    @Test
    public void execute_nonExistentRegime_throwsCommandException() {
        String expectedMessage = String.format(ScheduleRegimeCommand.MESSAGE_REGIME_NOT_FOUND,
            TypicalRegime.VALID_REGIME_NAME_CHEST);

        CommandTestUtil.assertCommandFailure(scheduleRegimeCommandWithUnknownRegime, model, expectedMessage);
    }

    @Test
    public void execute_dateBeforeCurrentDate_throwsCommandException() {
        String expectedMessage = String.format(ScheduleRegimeCommand.MESSAGE_DATE_BEFORE_CURRENT_DATE,
            Date.getToday().toString());

        CommandTestUtil.assertCommandFailure(scheduleRegimeCommandWithEpochDate, model, expectedMessage);
    }

    /**
     * Command will be successful in the sense that it returns a {@code CommandResult}.
     */
    @Test
    public void execute_conflictingDate_successfulCommandAndAlterMainAppState() {
        String expectedMessage = ScheduleRegimeCommand.MESSAGE_CONFLICT;

        Model expectedModel = new ModelManager(new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
                model.getAllRegimeData(),
                new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
                model.getAllScheduleData(), new UserPrefs());

        CommandTestUtil.assertCommandSuccess(scheduleRegimeCommandWithConflictingDate, model,
            new CommandResult(expectedMessage, false, false, true, false), expectedModel);

        //Ensure state is in conflict after execution of command
        assertEquals(State.IN_CONFLICT, MainApp.getState());

        //Extra state check as equals method in ModelManager does not check conflict variable.
        expectedModel.setConflict(TypicalConflict.VALID_CONFLICT);
        assertEquals(model.getConflict(), expectedModel.getConflict());
    }

    @Test
    public void execute_validScheduleRegimeCommand_success() {
        String expectedMessage = String.format(ScheduleRegimeCommand.MESSAGE_SUCCESS,
            TypicalRegime.VALID_REGIME_NAME_CARDIO, TypicalDates.DATE_3);

        Model expectedModel = new ModelManager(
                new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR), model.getAllRegimeData(),
                new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
                model.getAllScheduleData(), new UserPrefs());
        expectedModel.addSchedule(new Schedule(TypicalRegime.VALID_REGIME_CARDIO, TypicalDates.DATE_3));

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, ListResourceType.SCHEDULE);
        CommandTestUtil.assertCommandSuccess(validScheduleRegimeCommand, model, expectedCommandResult, expectedModel);
    }
}
