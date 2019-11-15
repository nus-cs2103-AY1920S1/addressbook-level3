package dukecooks.logic.commands.recipe;

import static dukecooks.testutil.recipe.TypicalRecipes.getTypicalRecipeBook;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.recipe.RecipeBook;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.testutil.TypicalIndexes;
import dukecooks.testutil.recipe.EditRecipeDescriptorBuilder;
import dukecooks.testutil.recipe.RecipeBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditRecipeCommand.
 */
public class EditRecipeCommandTest {

    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Recipe editedRecipe = new RecipeBuilder().build();

        String expectedMessage = String.format(EditRecipeCommand.MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe);

        Model expectedModel = new ModelManager(new RecipeBook(model.getRecipeBook()), new UserPrefs());
        expectedModel.setRecipe(model.getFilteredRecipeList().get(0), editedRecipe);

        EditRecipeCommand.EditRecipeDescriptor descriptor =
                new EditRecipeDescriptorBuilder(model.getFilteredRecipeList().get(0), editedRecipe).build();
        EditRecipeCommand editRecipeCommand = new EditRecipeCommand(TypicalIndexes.INDEX_FIRST_RECIPE, descriptor);

        CommandTestUtil.assertCommandSuccess(editRecipeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastRecipe = Index.fromOneBased(model.getFilteredRecipeList().size());
        Recipe lastRecipe = model.getFilteredRecipeList().get(indexLastRecipe.getZeroBased());

        RecipeBuilder recipeInList = new RecipeBuilder(lastRecipe);
        Recipe editedRecipe = recipeInList.withName(CommandTestUtil.VALID_NAME_BURGER)
                .withIngredients(CommandTestUtil.VALID_INGREDIENT_BURGER).build();

        EditRecipeCommand.EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder()
                .withRecipeName(CommandTestUtil.VALID_NAME_BURGER)
                .withIngredientsToAdd(CommandTestUtil.VALID_INGREDIENT_BURGER)
                .withIngredientsToRemove(lastRecipe.getIngredients().stream().map(i
                    -> String.valueOf(i).replace("[", "").replace("]", ""))
                        .collect(Collectors.toList()).toArray(new String[lastRecipe.getIngredients().size()]))
                        .build();
        EditRecipeCommand editRecipeCommand = new EditRecipeCommand(indexLastRecipe, descriptor);

        String expectedMessage = String.format(EditRecipeCommand.MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe);

        Model expectedModel = new ModelManager(new RecipeBook(model.getRecipeBook()), new UserPrefs());
        expectedModel.setRecipe(lastRecipe, editedRecipe);

        CommandTestUtil.assertCommandSuccess(editRecipeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditRecipeCommand editRecipeCommand = new EditRecipeCommand(TypicalIndexes.INDEX_FIRST_RECIPE,
                new EditRecipeCommand.EditRecipeDescriptor());
        Recipe editedRecipe = model.getFilteredRecipeList().get(TypicalIndexes.INDEX_FIRST_RECIPE.getZeroBased());

        String expectedMessage = String.format(EditRecipeCommand.MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe);

        Model expectedModel = new ModelManager(new RecipeBook(model.getRecipeBook()), new UserPrefs());

        CommandTestUtil.assertCommandSuccess(editRecipeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        CommandTestUtil.showRecipeAtIndex(model, TypicalIndexes.INDEX_FIRST_RECIPE);

        Recipe recipeInFilteredList = model.getFilteredRecipeList().get(TypicalIndexes.INDEX_FIRST_RECIPE
                .getZeroBased());
        Recipe editedRecipe = new RecipeBuilder(recipeInFilteredList).withName(CommandTestUtil.VALID_NAME_BURGER)
                .build();
        EditRecipeCommand editRecipeCommand = new EditRecipeCommand(TypicalIndexes.INDEX_FIRST_RECIPE,
                new EditRecipeDescriptorBuilder().withRecipeName(CommandTestUtil.VALID_NAME_BURGER).build());

        String expectedMessage = String.format(EditRecipeCommand.MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe);

        Model expectedModel = new ModelManager(new RecipeBook(model.getRecipeBook()), new UserPrefs());
        expectedModel.setRecipe(model.getFilteredRecipeList().get(0), editedRecipe);

        CommandTestUtil.assertCommandSuccess(editRecipeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateRecipeUnfilteredList_failure() {
        Recipe firstRecipe = model.getFilteredRecipeList().get(TypicalIndexes.INDEX_FIRST_RECIPE.getZeroBased());
        EditRecipeCommand.EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder(firstRecipe).build();
        EditRecipeCommand editRecipeCommand = new EditRecipeCommand(TypicalIndexes.INDEX_SECOND_RECIPE, descriptor);

        CommandTestUtil.assertRecipeCommandFailure(editRecipeCommand, model,
                EditRecipeCommand.MESSAGE_DUPLICATE_RECIPE);
    }

    @Test
    public void execute_duplicateRecipeFilteredList_failure() {
        CommandTestUtil.showRecipeAtIndex(model, TypicalIndexes.INDEX_FIRST_RECIPE);

        // edit recipe in filtered list into a duplicate in RecipeBook
        Recipe recipeInList = model.getRecipeBook().getRecipeList().get(TypicalIndexes.INDEX_SECOND_RECIPE
                .getZeroBased());
        EditRecipeCommand editRecipeCommand = new EditRecipeCommand(TypicalIndexes.INDEX_FIRST_RECIPE,
                new EditRecipeDescriptorBuilder(recipeInList).build());

        CommandTestUtil.assertRecipeCommandFailure(editRecipeCommand, model,
                EditRecipeCommand.MESSAGE_DUPLICATE_RECIPE);
    }

    @Test
    public void execute_invalidRecipeIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecipeList().size() + 1);
        EditRecipeCommand.EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder()
                .withRecipeName(CommandTestUtil.VALID_NAME_BURGER).build();
        EditRecipeCommand editRecipeCommand = new EditRecipeCommand(outOfBoundIndex, descriptor);

        CommandTestUtil.assertRecipeCommandFailure(editRecipeCommand, model,
                Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of RecipeBook
     */
    @Test
    public void execute_invalidRecipeIndexFilteredList_failure() {
        CommandTestUtil.showRecipeAtIndex(model, TypicalIndexes.INDEX_FIRST_RECIPE);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_RECIPE;
        // ensures that outOfBoundIndex is still in bounds of RecipeBook list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecipeBook().getRecipeList().size());

        EditRecipeCommand editRecipeCommand = new EditRecipeCommand(outOfBoundIndex,
                new EditRecipeDescriptorBuilder().withRecipeName(CommandTestUtil.VALID_NAME_BURGER).build());

        CommandTestUtil.assertRecipeCommandFailure(editRecipeCommand, model,
                Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditRecipeCommand standardCommand = new EditRecipeCommand(TypicalIndexes.INDEX_FIRST_RECIPE,
                CommandTestUtil.DESC_FISH);

        // same values -> returns true
        EditRecipeCommand.EditRecipeDescriptor copyDescriptor = new EditRecipeCommand
                .EditRecipeDescriptor(CommandTestUtil.DESC_FISH);
        EditRecipeCommand commandWithSameValues = new EditRecipeCommand(TypicalIndexes.INDEX_FIRST_RECIPE,
                copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearRecipeCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditRecipeCommand(TypicalIndexes.INDEX_SECOND_RECIPE,
                CommandTestUtil.DESC_FISH)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditRecipeCommand(TypicalIndexes.INDEX_FIRST_RECIPE,
                CommandTestUtil.DESC_BURGER)));
    }

}
