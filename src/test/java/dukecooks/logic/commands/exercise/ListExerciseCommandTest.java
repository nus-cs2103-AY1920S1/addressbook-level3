package dukecooks.logic.commands.exercise;

import static dukecooks.testutil.exercise.TypicalExercises.getTypicalWorkoutPlanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListExerciseCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalWorkoutPlanner(), new UserPrefs());
        expectedModel = new ModelManager(model.getExerciseCatalogue(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        CommandTestUtil.assertCommandSuccess(new ListExerciseCommand(), model, ListExerciseCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        CommandTestUtil.showExerciseAtIndex(model, TypicalIndexes.INDEX_FIRST_EXERCISE);
        CommandTestUtil.assertCommandSuccess(new ListExerciseCommand(), model, ListExerciseCommand.MESSAGE_SUCCESS,
                expectedModel);
    }
}
