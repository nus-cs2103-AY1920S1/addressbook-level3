package dukecooks.logic.commands;

import static dukecooks.logic.commands.CommandTestUtil.assertCommandSuccess;
import static dukecooks.testutil.recipe.TypicalRecipes.getTypicalRecipeBook;

import dukecooks.logic.commands.exercise.ClearExerciseCommand;
import dukecooks.logic.commands.recipe.ClearRecipeCommand;
import dukecooks.model.Model;
import org.junit.jupiter.api.Test;

import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.recipe.RecipeBook;

public class ClearCommandTest {

    @Test
    public void execute_emptyRecipeBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearRecipeCommand(), model, ClearRecipeCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyExerciseBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearExerciseCommand(), model, ClearExerciseCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyRecipeBook_success() {
        Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalRecipeBook(), new UserPrefs());
        expectedModel.setRecipeBook(new RecipeBook());

        assertCommandSuccess(new ClearRecipeCommand(), model, ClearRecipeCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
