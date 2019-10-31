package dukecooks.logic.commands;

import static dukecooks.logic.commands.CommandTestUtil.assertCommandSuccess;
import static dukecooks.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static dukecooks.testutil.dashboard.TypicalDashboard.getTypicalDashboardRecords;
import static dukecooks.testutil.recipe.TypicalRecipes.getTypicalRecipeBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.exercise.ListExerciseCommand;
import dukecooks.logic.commands.recipe.ListRecipeCommand;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private Model modelTask;
    private Model expectedModelTask;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalRecipeBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs());
        modelTask = new ModelManager(getTypicalDashboardRecords(), new UserPrefs());
        expectedModelTask = new ModelManager(modelTask.getDashboardRecords(), new UserPrefs());
    }

    @Test
    public void execute_recipeListIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListRecipeCommand(), model, ListRecipeCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_exerciseListIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListExerciseCommand(), model, ListExerciseCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_recipeListIsFiltered_showsEverything() {
        CommandTestUtil.showRecipeAtIndex(model, INDEX_FIRST_RECIPE);
        assertCommandSuccess(new ListRecipeCommand(), model, ListRecipeCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
