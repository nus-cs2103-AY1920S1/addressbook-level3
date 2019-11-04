package seedu.exercise.logic.commands;

import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_REGIME_COMPARATOR;
import static seedu.exercise.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.resource.Schedule;
import seedu.exercise.testutil.typicalutil.TypicalExercises;
import seedu.exercise.testutil.typicalutil.TypicalIndexes;
import seedu.exercise.testutil.typicalutil.TypicalSchedule;
import seedu.exercise.ui.ListResourceType;

/**
 * Contains integration test for {@code ScheduleCompleteCommand}.
 */
public class ScheduleCompleteCommandTest {

    private final ScheduleCommand scheduleCompleteCommandWithOutOfBoundsIndex =
        new ScheduleCompleteCommand(TypicalIndexes.INDEX_VERY_LARGE_NUMBER);
    private final ScheduleCommand scheduleCompleteCommandWithValidIndex =
        new ScheduleCompleteCommand(TypicalIndexes.INDEX_ONE_BASED_FIRST);

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalExercises.getTypicalExerciseBook(),
                new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
                TypicalSchedule.getTypicalScheduleBook(),
                new UserPrefs());
    }

    @Test
    public void execute_outOfBoundsIndex_throwsCommandException() {
        String expectedMessage = Messages.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX;
        CommandTestUtil.assertCommandFailure(scheduleCompleteCommandWithOutOfBoundsIndex, model, expectedMessage);
    }

    @Test
    public void execute_validCommand_success() {
        String expectedMessage = String.format(ScheduleCompleteCommand.MESSAGE_SUCCESS,
            Integer.toString(TypicalIndexes.INDEX_ONE_BASED_FIRST.getOneBased()));

        Schedule toComplete = model.getSortedScheduleList().get(0);
        Model expectedModel = new ModelManager(model.getExerciseBookData(),
            new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR), model.getAllScheduleData(),
            new UserPrefs());
        expectedModel.completeSchedule(toComplete);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, ListResourceType.EXERCISE);
        CommandTestUtil.assertCommandSuccess(scheduleCompleteCommandWithValidIndex,
            model, expectedCommandResult, expectedModel);

    }

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ScheduleCompleteCommand(null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> scheduleCompleteCommandWithValidIndex.execute(null));
    }
}
