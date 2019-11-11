package calofit.logic.commands;

//import static calofit.logic.commands.CommandTestUtil.assertCommandFailure;
//import static calofit.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static calofit.logic.commands.CommandTestUtil.showDishAtIndex;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

//import calofit.commons.core.Messages;
//import calofit.commons.core.index.Index;
import calofit.model.Model;
import calofit.model.ModelManager;
import calofit.model.UserPrefs;
//import calofit.model.meal.Meal;
//import calofit.model.meal.MealLog;
import calofit.testutil.TypicalDishes;
import calofit.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(TypicalDishes.getTypicalMealLog(),
            TypicalDishes.getTypicalDishDatabase(), new UserPrefs(), TypicalDishes.getTypicalBudget());

    //@Test
    //public void execute_validIndexUnfilteredList_success() {
    //    Meal mealToDelete = model.getMealLog().getTodayMeals().get(TypicalIndexes.INDEX_FIRST_MEAL.getZeroBased());
    //    DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_MEAL);
    //
    //    String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MEAL_SUCCESS,
    //            mealToDelete.getDish().getName());
    //
    //    ModelManager expectedModel = new ModelManager(new MealLog(model.getMealLog()),
    //            model.getDishDatabase(), new UserPrefs());
    //    expectedModel.removeMeal(mealToDelete);
    //
    //    assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    //}

    //@Test
    //public void execute_invalidIndexUnfilteredList_throwsCommandException() {
    //    Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDishList().size() + 1);
    //    DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);
    //
    //    assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_MEAL_DISPLAYED_INDEX);
    //}

    //@Test
    //public void execute_validIndexFilteredList_success() {
    //    showDishAtIndex(model, TypicalIndexes.INDEX_FIRST_MEAL);
    //
    //    Meal mealToDelete = model.getMealLog().getTodayMeals().get(TypicalIndexes.INDEX_FIRST_MEAL.getZeroBased());
    //
    //    DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_MEAL);
    //
    //    String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MEAL_SUCCESS,
    //            mealToDelete.getDish().getName());
    //
    //    Model expectedModel = new ModelManager(new MealLog(model.getMealLog()),
    //            model.getDishDatabase(), new UserPrefs());
    //    expectedModel.removeMeal(mealToDelete);
    //    showNoDish(expectedModel);
    //
    //    assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    //}

    //@Test
    //public void execute_invalidIndexFilteredList_throwsCommandException() {
    //    showDishAtIndex(model, TypicalIndexes.INDEX_FIRST_MEAL);
    //
    //    Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_MEAL;
    //    // ensures that outOfBoundIndex is still in bounds of dish database list
    //    assertTrue(outOfBoundIndex.getZeroBased() < model.getDishDatabase().getDishList().size());
    //
    //    DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);
    //
    //    assertCommandFailure(deleteCommand, model,
    //            String.format(Messages.MESSAGE_INVALID_MEAL_INDEX, outOfBoundIndex.getOneBased()));
    //}

    @Test
    public void equals() {
        ArrayList<Integer> typicalIndexFirstMeal = new ArrayList<Integer>();
        ArrayList<Integer> typicalIndexSecondMeal = new ArrayList<Integer>();
        typicalIndexFirstMeal.add(TypicalIndexes.INDEX_FIRST_MEAL.getZeroBased());
        typicalIndexSecondMeal.add(TypicalIndexes.INDEX_SECOND_MEAL.getZeroBased());
        DeleteCommand deleteFirstCommand = new DeleteCommand(typicalIndexFirstMeal);
        DeleteCommand deleteSecondCommand = new DeleteCommand(typicalIndexSecondMeal);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(typicalIndexFirstMeal);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different dish -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoDish(Model model) {
        model.setDishFilterPredicate(p -> false);

        assertTrue(model.getFilteredDishList().isEmpty());
    }
}
