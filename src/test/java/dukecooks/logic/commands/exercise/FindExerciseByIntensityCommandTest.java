package dukecooks.logic.commands.exercise;

import static dukecooks.testutil.exercise.TypicalExercises.getTypicalWorkoutPlanner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.workout.exercise.components.IntensityContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindExerciseByIntensityCommandTest {
    private Model model = new ModelManager(getTypicalWorkoutPlanner(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalWorkoutPlanner(), new UserPrefs());

    @Test
    public void equals() {
        IntensityContainsKeywordsPredicate firstPredicate =
                new IntensityContainsKeywordsPredicate(Collections.singletonList("first"));
        IntensityContainsKeywordsPredicate secondPredicate =
                new IntensityContainsKeywordsPredicate(Collections.singletonList("second"));

        FindExerciseByIntensityCommand findFirstCommand = new FindExerciseByIntensityCommand(firstPredicate);
        FindExerciseByIntensityCommand findSecondCommand = new FindExerciseByIntensityCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindExerciseByIntensityCommand findFirstCommandCopy = new FindExerciseByIntensityCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noExerciseFound() {
        String expectedMessage = String.format(Messages.MESSAGE_EXERCISES_LISTED_OVERVIEW, 0);
        IntensityContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindExerciseByIntensityCommand command = new FindExerciseByIntensityCommand(predicate);
        expectedModel.updateFilteredExerciseList(predicate);
        CommandTestUtil.assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredExerciseList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private IntensityContainsKeywordsPredicate preparePredicate(String userInput) {
        return new IntensityContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
