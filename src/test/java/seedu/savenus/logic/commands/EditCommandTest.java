package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.commands.CommandTestUtil.*;
import static seedu.savenus.testutil.TypicalFood.getTypicalMenu;
import static seedu.savenus.testutil.TypicalIndexes.INDEX_FIRST_FOOD;
import static seedu.savenus.testutil.TypicalIndexes.INDEX_SECOND_FOOD;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.core.Messages;
import seedu.savenus.commons.core.index.Index;
import seedu.savenus.logic.commands.EditCommand.EditFoodDescriptor;
import seedu.savenus.model.Menu;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.UserPrefs;
import seedu.savenus.model.food.Food;
import seedu.savenus.testutil.EditFoodDescriptorBuilder;
import seedu.savenus.testutil.FoodBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalMenu(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Food editedFood = new FoodBuilder().build();
        EditFoodDescriptor descriptor = new EditFoodDescriptorBuilder(editedFood).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FOOD, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FOOD_SUCCESS, editedFood);

        Model expectedModel = new ModelManager(new Menu(model.getMenu()), new UserPrefs());
        expectedModel.setFood(model.getFilteredFoodList().get(0), editedFood);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastFood = Index.fromOneBased(model.getFilteredFoodList().size());
        Food lastFood = model.getFilteredFoodList().get(indexLastFood.getZeroBased());

        FoodBuilder foodInList = new FoodBuilder(lastFood);
        Food editedFood = foodInList.withName(VALID_NAME_NASI_LEMAK).withPrice(VALID_PRICE_NASI_LEMAK)
                .withTags(VALID_TAG_HUSBAND).build();

        EditFoodDescriptor descriptor = new EditFoodDescriptorBuilder().withName(VALID_NAME_NASI_LEMAK)
                .withPrice(VALID_PRICE_NASI_LEMAK).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastFood, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FOOD_SUCCESS, editedFood);

        Model expectedModel = new ModelManager(new Menu(model.getMenu()), new UserPrefs());
        expectedModel.setFood(lastFood, editedFood);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FOOD, new EditFoodDescriptor());
        Food editedFood = model.getFilteredFoodList().get(INDEX_FIRST_FOOD.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FOOD_SUCCESS, editedFood);

        Model expectedModel = new ModelManager(new Menu(model.getMenu()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showFoodAtIndex(model, INDEX_FIRST_FOOD);

        Food foodInFilteredList = model.getFilteredFoodList().get(INDEX_FIRST_FOOD.getZeroBased());
        Food editedFood = new FoodBuilder(foodInFilteredList).withName(VALID_NAME_NASI_LEMAK).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FOOD,
                new EditFoodDescriptorBuilder().withName(VALID_NAME_NASI_LEMAK).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_FOOD_SUCCESS, editedFood);

        Model expectedModel = new ModelManager(new Menu(model.getMenu()), new UserPrefs());
        expectedModel.setFood(model.getFilteredFoodList().get(0), editedFood);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatefoodUnfilteredList_failure() {
        Food firstFood = model.getFilteredFoodList().get(INDEX_FIRST_FOOD.getZeroBased());
        EditFoodDescriptor descriptor = new EditFoodDescriptorBuilder(firstFood).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_FOOD, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_FOOD);
    }

    @Test
    public void execute_duplicatefoodFilteredList_failure() {
        showFoodAtIndex(model, INDEX_FIRST_FOOD);

        // edit food in filtered list into a duplicate in address book
        Food foodInList = model.getMenu().getFoodList().get(INDEX_SECOND_FOOD.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_FOOD,
                new EditFoodDescriptorBuilder(foodInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_FOOD);
    }

    @Test
    public void execute_invalidfoodIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFoodList().size() + 1);
        EditFoodDescriptor descriptor = new EditFoodDescriptorBuilder().withName(VALID_NAME_NASI_LEMAK).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidFoodIndexFilteredList_failure() {
        showFoodAtIndex(model, INDEX_FIRST_FOOD);
        Index outOfBoundIndex = INDEX_SECOND_FOOD;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMenu().getFoodList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditFoodDescriptorBuilder().withName(VALID_NAME_NASI_LEMAK).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_FOOD, DESC_CHICKEN_RICE);

        // same values -> returns true
        EditFoodDescriptor copyDescriptor = new EditFoodDescriptor(DESC_CHICKEN_RICE);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_FOOD, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_FOOD, DESC_CHICKEN_RICE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_FOOD, DESC_NASI_LEMAK)));
    }

}
