package seedu.exercise.logic.commands;

import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_REGIME_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_SCHEDULE_COMPARATOR;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;

import org.junit.jupiter.api.Test;

import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.ui.ListResourceType;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        assertCommandFailure(new ClearCommand(), model, ClearCommand.MESSAGE_EMPTY_EXERCISE_LIST);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalExerciseBook(),
                new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalExerciseBook(),
                new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR), new UserPrefs());
        expectedModel.setExerciseBook(new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR));

        CommandResult expectedCommandResult = new CommandResult(ClearCommand.MESSAGE_SUCCESS,
            ListResourceType.EXERCISE);
        assertCommandSuccess(new ClearCommand(), model, expectedCommandResult, expectedModel);
    }

}
