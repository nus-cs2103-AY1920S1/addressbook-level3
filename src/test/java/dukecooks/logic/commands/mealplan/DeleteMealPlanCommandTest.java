package dukecooks.logic.commands.mealplan;

import static dukecooks.testutil.mealplan.TypicalMealPlans.getTypicalMealPlanBook;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteMealPlanCommand}.
 */
public class DeleteMealPlanCommandTest {

    private Model model = new ModelManager(getTypicalMealPlanBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        MealPlan mealPlanToDelete = model.getFilteredMealPlanList().get(TypicalIndexes.INDEX_FIRST_RECIPE.getZeroBased());
        DeleteMealPlanCommand deleteMealPlanCommand = new DeleteMealPlanCommand(TypicalIndexes.INDEX_FIRST_RECIPE);

        String expectedMessage = String.format(DeleteMealPlanCommand.MESSAGE_DELETE_MEALPLAN_SUCCESS, mealPlanToDelete);

        ModelManager expectedModel = new ModelManager(model.getMealPlanBook(), new UserPrefs());
        expectedModel.deleteMealPlan(mealPlanToDelete);

        CommandTestUtil.assertCommandSuccess(deleteMealPlanCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMealPlanList().size() + 1);
        DeleteMealPlanCommand deleteMealPlanCommand = new DeleteMealPlanCommand(outOfBoundIndex);

        CommandTestUtil.assertMealPlanCommandFailure(deleteMealPlanCommand, model,
                Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        CommandTestUtil.showMealPlanAtIndex(model, TypicalIndexes.INDEX_FIRST_RECIPE);

        MealPlan mealPlanToDelete = model.getFilteredMealPlanList().get(TypicalIndexes.INDEX_FIRST_RECIPE.getZeroBased());
        DeleteMealPlanCommand deleteMealPlanCommand = new DeleteMealPlanCommand(TypicalIndexes.INDEX_FIRST_RECIPE);

        String expectedMessage = String.format(DeleteMealPlanCommand.MESSAGE_DELETE_MEALPLAN_SUCCESS, mealPlanToDelete);

        Model expectedModel = new ModelManager(model.getMealPlanBook(), new UserPrefs());
        expectedModel.deleteMealPlan(mealPlanToDelete);
        showNoMealPlan(expectedModel);

        CommandTestUtil.assertCommandSuccess(deleteMealPlanCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showMealPlanAtIndex(model, TypicalIndexes.INDEX_FIRST_RECIPE);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_RECIPE;
        // ensures that outOfBoundIndex is still in bounds of MealPlanBook list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMealPlanBook().getMealPlanList().size());

        DeleteMealPlanCommand deleteMealPlanCommand = new DeleteMealPlanCommand(outOfBoundIndex);

        CommandTestUtil.assertMealPlanCommandFailure(deleteMealPlanCommand, model,
                Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteMealPlanCommand deleteFirstCommand = new DeleteMealPlanCommand(TypicalIndexes.INDEX_FIRST_RECIPE);
        DeleteMealPlanCommand deleteSecondCommand = new DeleteMealPlanCommand(TypicalIndexes.INDEX_SECOND_RECIPE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteMealPlanCommand deleteFirstCommandCopy = new DeleteMealPlanCommand(TypicalIndexes.INDEX_FIRST_RECIPE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different mealPlan -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoMealPlan(Model model) {
        model.updateFilteredMealPlanList(p -> false);

        assertTrue(model.getFilteredMealPlanList().isEmpty());
    }
}
