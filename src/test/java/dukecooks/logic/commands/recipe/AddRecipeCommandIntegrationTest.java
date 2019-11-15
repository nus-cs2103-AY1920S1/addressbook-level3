package dukecooks.logic.commands.recipe;

import static dukecooks.testutil.recipe.TypicalRecipes.getTypicalRecipeBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.recipe.components.Recipe;

/**
 * Contains integration tests (interaction with the Model) for {@code AddRecipeCommand}.
 */
public class AddRecipeCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalRecipeBook(), new UserPrefs());
    }

    @Test
    public void execute_duplicateRecipe_throwsCommandException() {
        Recipe recipeInList = model.getRecipeBook().getRecipeList().get(0);
        CommandTestUtil.assertRecipeCommandFailure(new AddRecipeCommand(recipeInList), model,
                AddRecipeCommand.MESSAGE_DUPLICATE_RECIPE);
    }

}
