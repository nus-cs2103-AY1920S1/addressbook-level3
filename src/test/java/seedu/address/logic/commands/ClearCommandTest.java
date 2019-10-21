package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.recipe.TypicalRecipes.getTypicalRecipeBook;
import static seedu.address.testutil.exercise.TypicalExercises.getTypicalExercises;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exercise.ClearExerciseCommand;
import seedu.address.logic.commands.recipe.ClearRecipeCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.recipe.RecipeBook;

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
