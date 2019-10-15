package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertRecipeCommandFailure;
import static seedu.address.testutil.TypicalRecipes.getTypicalRecipeBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.recipe.Recipe;

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
        assertRecipeCommandFailure(new AddRecipeCommand(recipeInList), model,
                AddRecipeCommand.MESSAGE_DUPLICATE_RECIPE);
    }

}
