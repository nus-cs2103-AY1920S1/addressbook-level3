package seedu.address.logic.commands.exercise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.exercise.TypicalExercises.CURTSY_LUNGE;
import static seedu.address.testutil.exercise.TypicalExercises.EXPLOSIVE_PUSHUP;
import static seedu.address.testutil.exercise.TypicalExercises.FLYE;
import static seedu.address.testutil.exercise.TypicalExercises.getTypicalWorkoutPlanner;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.exercise.components.ExerciseNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindExerciseCommandTest {
    private Model model = new ModelManager(getTypicalWorkoutPlanner(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalWorkoutPlanner(), new UserPrefs());

    @Test
    public void equals() {
        ExerciseNameContainsKeywordsPredicate firstPredicate =
                new ExerciseNameContainsKeywordsPredicate(Collections.singletonList("first"));
        ExerciseNameContainsKeywordsPredicate secondPredicate =
                new ExerciseNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindExerciseCommand findFirstCommand = new FindExerciseCommand(firstPredicate);
        FindExerciseCommand findSecondCommand = new FindExerciseCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindExerciseCommand findFirstCommandCopy = new FindExerciseCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        ExerciseNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindExerciseCommand command = new FindExerciseCommand(predicate);
        expectedModel.updateFilteredExerciseList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredExerciseList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        ExerciseNameContainsKeywordsPredicate predicate = preparePredicate("Curtsy Pushup Flye");
        FindExerciseCommand command = new FindExerciseCommand(predicate);
        expectedModel.updateFilteredExerciseList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CURTSY_LUNGE, EXPLOSIVE_PUSHUP, FLYE), model.getFilteredExerciseList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private ExerciseNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new ExerciseNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
