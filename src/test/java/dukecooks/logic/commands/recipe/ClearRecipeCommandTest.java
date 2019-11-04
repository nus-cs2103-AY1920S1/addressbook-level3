package dukecooks.logic.commands.recipe;

import static dukecooks.logic.commands.CommandTestUtil.assertCommandSuccess;
import static dukecooks.testutil.recipe.TypicalRecipes.getTypicalRecipeBook;

import org.junit.jupiter.api.Test;

import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.recipe.RecipeBook;

public class ClearRecipeCommandTest {

    @Test
    public void execute_emptyRecipeBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearRecipeCommand(), model, ClearRecipeCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyRecipeBook_success() {
        Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalRecipeBook(), new UserPrefs());
        expectedModel.setRecipeBook(new RecipeBook());

        assertCommandSuccess(new ClearRecipeCommand(), model, ClearRecipeCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
