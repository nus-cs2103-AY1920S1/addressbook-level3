package calofit.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import calofit.commons.core.Messages;
import calofit.commons.core.index.Index;
import calofit.model.Model;
import calofit.model.ModelManager;
import calofit.model.UserPrefs;
import calofit.model.dish.Dish;
import calofit.model.dish.DishDatabase;
import calofit.testutil.DishBuilder;
import calofit.testutil.EditDishDescriptorBuilder;
import calofit.testutil.TypicalDishes;
import calofit.testutil.TypicalIndexes;
import org.junit.jupiter.api.Test;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(TypicalDishes.getTypicalDishDatabase(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Dish editedDish = new DishBuilder().build();
        EditCommand.EditDishDescriptor descriptor = new EditDishDescriptorBuilder(editedDish).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_MEAL, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEAL_SUCCESS, editedDish);

        Model expectedModel = new ModelManager(new DishDatabase(model.getDishDatabase()), new UserPrefs());
        expectedModel.setDish(model.getFilteredDishList().get(0), editedDish);

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastDish = Index.fromOneBased(model.getFilteredDishList().size());
        Dish lastDish = model.getFilteredDishList().get(indexLastDish.getZeroBased());

        DishBuilder dishInList = new DishBuilder(lastDish);
        Dish editedDish = dishInList.withName(CommandTestUtil.VALID_NAME_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();

        EditCommand.EditDishDescriptor descriptor = new EditDishDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastDish, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEAL_SUCCESS, editedDish);

        Model expectedModel = new ModelManager(new DishDatabase(model.getDishDatabase()), new UserPrefs());
        expectedModel.setDish(lastDish, editedDish);

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_MEAL, new EditCommand.EditDishDescriptor());
        Dish editedDish = model.getFilteredDishList().get(TypicalIndexes.INDEX_FIRST_MEAL.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEAL_SUCCESS, editedDish);

        Model expectedModel = new ModelManager(new DishDatabase(model.getDishDatabase()), new UserPrefs());

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        CommandTestUtil.showDishAtIndex(model, TypicalIndexes.INDEX_FIRST_MEAL);

        Dish dishInFilteredList = model.getFilteredDishList().get(TypicalIndexes.INDEX_FIRST_MEAL.getZeroBased());
        Dish editedDish = new DishBuilder(dishInFilteredList).withName(CommandTestUtil.VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_MEAL,
                new EditDishDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEAL_SUCCESS, editedDish);

        Model expectedModel = new ModelManager(new DishDatabase(model.getDishDatabase()), new UserPrefs());
        expectedModel.setDish(model.getFilteredDishList().get(0), editedDish);

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateDishUnfilteredList_failure() {
        Dish firstDish = model.getFilteredDishList().get(TypicalIndexes.INDEX_FIRST_MEAL.getZeroBased());
        EditCommand.EditDishDescriptor descriptor = new EditDishDescriptorBuilder(firstDish).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_SECOND_MEAL, descriptor);

        CommandTestUtil.assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_MEAL);
    }

    @Test
    public void execute_duplicateDishFilteredList_failure() {
        CommandTestUtil.showDishAtIndex(model, TypicalIndexes.INDEX_FIRST_MEAL);

        // edit dish in filtered list into a duplicate in dish database
        Dish dishInList = model.getDishDatabase().getDishList().get(TypicalIndexes.INDEX_SECOND_MEAL.getZeroBased());
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_MEAL,
                new EditDishDescriptorBuilder(dishInList).build());

        CommandTestUtil.assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_MEAL);
    }

    @Test
    public void execute_invalidDishIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDishList().size() + 1);
        EditCommand.EditDishDescriptor descriptor = new EditDishDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        CommandTestUtil.assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_MEAL_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of dish database
     */
    @Test
    public void execute_invalidDishIndexFilteredList_failure() {
        CommandTestUtil.showDishAtIndex(model, TypicalIndexes.INDEX_FIRST_MEAL);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_MEAL;
        // ensures that outOfBoundIndex is still in bounds of dish database list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getDishDatabase().getDishList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditDishDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build());

        CommandTestUtil.assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_MEAL_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_MEAL, CommandTestUtil.DESC_AMY);

        // same values -> returns true
        EditCommand.EditDishDescriptor copyDescriptor = new EditCommand.EditDishDescriptor(CommandTestUtil.DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(TypicalIndexes.INDEX_FIRST_MEAL, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(TypicalIndexes.INDEX_SECOND_MEAL, CommandTestUtil.DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(TypicalIndexes.INDEX_FIRST_MEAL, CommandTestUtil.DESC_BOB)));
    }

}
