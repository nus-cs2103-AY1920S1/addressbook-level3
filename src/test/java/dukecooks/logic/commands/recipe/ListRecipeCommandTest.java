package dukecooks.logic.commands.recipe;

import static dukecooks.logic.commands.CommandTestUtil.assertCommandSuccess;
import static dukecooks.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static dukecooks.testutil.recipe.TypicalRecipes.getTypicalRecipeBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListRecipeCommand.
 */
public class ListRecipeCommandTest {

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
    public void execute_recipeListIsFiltered_showsEverything() {
        CommandTestUtil.showRecipeAtIndex(model, INDEX_FIRST_RECIPE);
        assertCommandSuccess(new ListRecipeCommand(), model, ListRecipeCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
