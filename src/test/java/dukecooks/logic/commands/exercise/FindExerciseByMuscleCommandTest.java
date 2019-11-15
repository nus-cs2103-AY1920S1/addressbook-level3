package dukecooks.logic.commands.exercise;

import static dukecooks.testutil.exercise.TypicalExercises.ABS_ROLLOUT;
import static dukecooks.testutil.exercise.TypicalExercises.BURPEES;
import static dukecooks.testutil.exercise.TypicalExercises.EXPLOSIVE_PUSHUP;
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
import dukecooks.model.workout.exercise.components.MusclesTrainedContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindExerciseByMuscleCommandTest {
    private Model model = new ModelManager(getTypicalWorkoutPlanner(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalWorkoutPlanner(), new UserPrefs());

    @Test
    public void equals() {
        MusclesTrainedContainsKeywordsPredicate firstPredicate =
                new MusclesTrainedContainsKeywordsPredicate(Collections.singletonList("first"));
        MusclesTrainedContainsKeywordsPredicate secondPredicate =
                new MusclesTrainedContainsKeywordsPredicate(Collections.singletonList("second"));

        FindExerciseByMuscleCommand findFirstCommand = new FindExerciseByMuscleCommand(firstPredicate);
        FindExerciseByMuscleCommand findSecondCommand = new FindExerciseByMuscleCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindExerciseByMuscleCommand findFirstCommandCopy = new FindExerciseByMuscleCommand(firstPredicate);
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
        MusclesTrainedContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindExerciseByMuscleCommand command = new FindExerciseByMuscleCommand(predicate);
        expectedModel.updateFilteredExerciseList(predicate);
        CommandTestUtil.assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredExerciseList());
    }

    @Test
    public void execute_multipleKeywords_multipleExercisesFound() {
        String expectedMessage = String.format(Messages.MESSAGE_EXERCISES_LISTED_OVERVIEW, 3);
        MusclesTrainedContainsKeywordsPredicate predicate = preparePredicate("Abs Cardiovascular");
        FindExerciseByMuscleCommand command = new FindExerciseByMuscleCommand(predicate);
        expectedModel.updateFilteredExerciseList(predicate);
        CommandTestUtil.assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ABS_ROLLOUT, BURPEES, EXPLOSIVE_PUSHUP), model.getFilteredExerciseList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private MusclesTrainedContainsKeywordsPredicate preparePredicate(String userInput) {
        return new MusclesTrainedContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
