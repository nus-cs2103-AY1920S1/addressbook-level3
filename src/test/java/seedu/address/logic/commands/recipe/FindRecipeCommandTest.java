package seedu.address.logic.commands.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_RECIPE_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.recipe.TypicalRecipes.CHICKEN;
import static seedu.address.testutil.recipe.TypicalRecipes.TEA;
import static seedu.address.testutil.recipe.TypicalRecipes.TUNA;
import static seedu.address.testutil.recipe.TypicalRecipes.getTypicalRecipeBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.recipe.components.RecipeNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindRecipeCommand}.
 */
public class FindRecipeCommandTest {
    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalRecipeBook(), new UserPrefs());

    @Test
    public void equals() {
        RecipeNameContainsKeywordsPredicate firstPredicate =
                new RecipeNameContainsKeywordsPredicate(Collections.singletonList("first"));
        RecipeNameContainsKeywordsPredicate secondPredicate =
                new RecipeNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindRecipeCommand findFirstCommand = new FindRecipeCommand(firstPredicate);
        FindRecipeCommand findSecondCommand = new FindRecipeCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindRecipeCommand findFirstCommandCopy = new FindRecipeCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different recipe -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noRecipeFound() {
        String expectedMessage = String.format(MESSAGE_RECIPE_LISTED_OVERVIEW, 0);
        RecipeNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindRecipeCommand command = new FindRecipeCommand(predicate);
        expectedModel.updateFilteredRecipeList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRecipeList());
    }

    @Test
    public void execute_multipleKeywords_multipleRecipesFound() {
        String expectedMessage = String.format(MESSAGE_RECIPE_LISTED_OVERVIEW, 3);
        RecipeNameContainsKeywordsPredicate predicate = preparePredicate("Sandwich Fried Tea");
        FindRecipeCommand command = new FindRecipeCommand(predicate);
        expectedModel.updateFilteredRecipeList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TUNA, CHICKEN, TEA), model.getFilteredRecipeList());
    }

    /**
     * Parses {@code userInput} into a {@code RecipeNameContainsKeywordsPredicate}.
     */
    private RecipeNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new RecipeNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
