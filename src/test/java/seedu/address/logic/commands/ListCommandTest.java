package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showRecipeAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.address.testutil.recipe.TypicalRecipes.getTypicalRecipeBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exercise.ListExerciseCommand;
import seedu.address.logic.commands.recipe.ListRecipeCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalRecipeBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs());
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
        showRecipeAtIndex(model, INDEX_FIRST_RECIPE);
        assertCommandSuccess(new ListRecipeCommand(), model, ListRecipeCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
