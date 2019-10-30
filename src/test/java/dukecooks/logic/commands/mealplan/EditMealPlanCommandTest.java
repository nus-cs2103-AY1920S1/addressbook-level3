package dukecooks.logic.commands.mealplan;

import static dukecooks.testutil.mealplan.TypicalMealPlans.getTypicalMealPlanBook;
import static dukecooks.testutil.recipe.TypicalRecipes.MILO;
import static dukecooks.testutil.recipe.TypicalRecipes.OMELETTE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.mealplan.MealPlanBook;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.testutil.TypicalIndexes;
import dukecooks.testutil.mealplan.EditMealPlanDescriptorBuilder;
import dukecooks.testutil.mealplan.MealPlanBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditMealPlanCommand.
 */
public class EditMealPlanCommandTest {

    private Model model = new ModelManager(getTypicalMealPlanBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        model.addRecipe(MILO);
        model.addRecipe(OMELETTE);
        MealPlan editedMealPlan = new MealPlanBuilder().build();

        String expectedMessage = String.format(EditMealPlanCommand.MESSAGE_EDIT_MEALPLAN_SUCCESS, editedMealPlan);

        Model expectedModel = new ModelManager(new MealPlanBook(model.getMealPlanBook()), new UserPrefs());
        expectedModel.addRecipe(MILO);
        expectedModel.addRecipe(OMELETTE);
        expectedModel.setMealPlan(model.getFilteredMealPlanList().get(0), editedMealPlan);

        EditMealPlanCommand.EditMealPlanDescriptor descriptor =
                new EditMealPlanDescriptorBuilder(model.getFilteredMealPlanList().get(0), editedMealPlan).build();
        EditMealPlanCommand editMealPlanCommand = new EditMealPlanCommand(TypicalIndexes.INDEX_FIRST_MEALPLAN,
                descriptor);

        CommandTestUtil.assertCommandSuccess(editMealPlanCommand, model, expectedMessage, expectedModel);
        model.deleteRecipe(MILO);
        model.deleteRecipe(OMELETTE);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditMealPlanCommand editMealPlanCommand = new EditMealPlanCommand(TypicalIndexes.INDEX_FIRST_MEALPLAN,
                new EditMealPlanCommand.EditMealPlanDescriptor());
        MealPlan editedMealPlan = model.getFilteredMealPlanList().get(TypicalIndexes.INDEX_FIRST_MEALPLAN
                .getZeroBased());

        String expectedMessage = String.format(EditMealPlanCommand.MESSAGE_EDIT_MEALPLAN_SUCCESS, editedMealPlan);

        Model expectedModel = new ModelManager(new MealPlanBook(model.getMealPlanBook()), new UserPrefs());

        CommandTestUtil.assertCommandSuccess(editMealPlanCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        CommandTestUtil.showMealPlanAtIndex(model, TypicalIndexes.INDEX_FIRST_MEALPLAN);

        MealPlan mealPlanInFilteredList = model.getFilteredMealPlanList().get(TypicalIndexes.INDEX_FIRST_MEALPLAN
                .getZeroBased());
        MealPlan editedMealPlan = new MealPlanBuilder(mealPlanInFilteredList).withName(CommandTestUtil
                .VALID_NAME_BURGER)
                .build();
        EditMealPlanCommand editMealPlanCommand = new EditMealPlanCommand(TypicalIndexes.INDEX_FIRST_MEALPLAN,
                new EditMealPlanDescriptorBuilder().withMealPlanName(CommandTestUtil.VALID_NAME_BURGER).build());

        String expectedMessage = String.format(EditMealPlanCommand.MESSAGE_EDIT_MEALPLAN_SUCCESS, editedMealPlan);

        Model expectedModel = new ModelManager(new MealPlanBook(model.getMealPlanBook()), new UserPrefs());
        expectedModel.setMealPlan(model.getFilteredMealPlanList().get(0), editedMealPlan);

        CommandTestUtil.assertCommandSuccess(editMealPlanCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateMealPlanUnfilteredList_failure() {
        MealPlan firstMealPlan = model.getFilteredMealPlanList().get(TypicalIndexes.INDEX_FIRST_MEALPLAN
                .getZeroBased());
        EditMealPlanCommand.EditMealPlanDescriptor descriptor = new EditMealPlanDescriptorBuilder(firstMealPlan)
                .build();
        EditMealPlanCommand editMealPlanCommand = new EditMealPlanCommand(TypicalIndexes.INDEX_SECOND_MEALPLAN,
                descriptor);

        CommandTestUtil.assertMealPlanCommandFailure(editMealPlanCommand, model,
                EditMealPlanCommand.MESSAGE_DUPLICATE_MEALPLAN);
    }

    @Test
    public void execute_duplicateMealPlanFilteredList_failure() {
        CommandTestUtil.showMealPlanAtIndex(model, TypicalIndexes.INDEX_FIRST_MEALPLAN);

        // edit mealPlan in filtered list into a duplicate in MealPlanBook
        MealPlan mealPlanInList = model.getMealPlanBook().getMealPlanList().get(TypicalIndexes.INDEX_SECOND_MEALPLAN
                .getZeroBased());
        EditMealPlanCommand editMealPlanCommand = new EditMealPlanCommand(TypicalIndexes.INDEX_FIRST_MEALPLAN,
                new EditMealPlanDescriptorBuilder(mealPlanInList).build());

        CommandTestUtil.assertMealPlanCommandFailure(editMealPlanCommand, model,
                EditMealPlanCommand.MESSAGE_DUPLICATE_MEALPLAN);
    }

    @Test
    public void execute_invalidMealPlanIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMealPlanList().size() + 1);
        EditMealPlanCommand.EditMealPlanDescriptor descriptor = new EditMealPlanDescriptorBuilder()
                .withMealPlanName(CommandTestUtil.VALID_NAME_BURGER).build();
        EditMealPlanCommand editMealPlanCommand = new EditMealPlanCommand(outOfBoundIndex, descriptor);

        CommandTestUtil.assertMealPlanCommandFailure(editMealPlanCommand, model,
                Messages.MESSAGE_INVALID_MEALPLAN_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of MealPlanBook
     */
    @Test
    public void execute_invalidMealPlanIndexFilteredList_failure() {
        CommandTestUtil.showMealPlanAtIndex(model, TypicalIndexes.INDEX_FIRST_MEALPLAN);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_MEALPLAN;
        // ensures that outOfBoundIndex is still in bounds of MealPlanBook list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMealPlanBook().getMealPlanList().size());

        EditMealPlanCommand editMealPlanCommand = new EditMealPlanCommand(outOfBoundIndex,
                new EditMealPlanDescriptorBuilder().withMealPlanName(CommandTestUtil.VALID_NAME_BURGER).build());

        CommandTestUtil.assertMealPlanCommandFailure(editMealPlanCommand, model,
                Messages.MESSAGE_INVALID_MEALPLAN_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditMealPlanCommand standardCommand = new EditMealPlanCommand(TypicalIndexes.INDEX_FIRST_MEALPLAN,
                CommandTestUtil.DESC_FISH_MP);

        // same values -> returns true
        EditMealPlanCommand.EditMealPlanDescriptor copyDescriptor = new EditMealPlanCommand
                .EditMealPlanDescriptor(CommandTestUtil.DESC_FISH_MP);
        EditMealPlanCommand commandWithSameValues = new EditMealPlanCommand(TypicalIndexes.INDEX_FIRST_MEALPLAN,
                copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearMealPlanCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditMealPlanCommand(TypicalIndexes.INDEX_SECOND_MEALPLAN,
                CommandTestUtil.DESC_FISH_MP)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditMealPlanCommand(TypicalIndexes.INDEX_FIRST_MEALPLAN,
                CommandTestUtil.DESC_BURGER_MP)));
    }

}
