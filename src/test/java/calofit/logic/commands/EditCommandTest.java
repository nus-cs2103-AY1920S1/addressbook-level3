package calofit.logic.commands;

import calofit.model.meal.Meal;
import calofit.testutil.EditMealDescriptorBuilder;
import calofit.testutil.MealBuilder;
import calofit.testutil.TypicalIndexes;
import calofit.testutil.TypicalMeals;
import org.junit.jupiter.api.Test;
import calofit.commons.core.Messages;
import calofit.commons.core.index.Index;
import calofit.model.AddressBook;
import calofit.model.Model;
import calofit.model.ModelManager;
import calofit.model.UserPrefs;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(TypicalMeals.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Meal editedMeal = new MealBuilder().build();
        EditCommand.EditMealDescriptor descriptor = new EditMealDescriptorBuilder(editedMeal).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_MEAL, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEAL_SUCCESS, editedMeal);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setMeal(model.getFilteredMealList().get(0), editedMeal);

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastMeal = Index.fromOneBased(model.getFilteredMealList().size());
        Meal lastMeal = model.getFilteredMealList().get(indexLastMeal.getZeroBased());

        MealBuilder mealInList = new MealBuilder(lastMeal);
        Meal editedMeal = mealInList.withName(CommandTestUtil.VALID_NAME_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();

        EditCommand.EditMealDescriptor descriptor = new EditMealDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastMeal, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEAL_SUCCESS, editedMeal);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setMeal(lastMeal, editedMeal);

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_MEAL, new EditCommand.EditMealDescriptor());
        Meal editedMeal = model.getFilteredMealList().get(TypicalIndexes.INDEX_FIRST_MEAL.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEAL_SUCCESS, editedMeal);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        CommandTestUtil.showMealAtIndex(model, TypicalIndexes.INDEX_FIRST_MEAL);

        Meal mealInFilteredList = model.getFilteredMealList().get(TypicalIndexes.INDEX_FIRST_MEAL.getZeroBased());
        Meal editedMeal = new MealBuilder(mealInFilteredList).withName(CommandTestUtil.VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_MEAL,
                new EditMealDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEAL_SUCCESS, editedMeal);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setMeal(model.getFilteredMealList().get(0), editedMeal);

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateMealUnfilteredList_failure() {
        Meal firstMeal = model.getFilteredMealList().get(TypicalIndexes.INDEX_FIRST_MEAL.getZeroBased());
        EditCommand.EditMealDescriptor descriptor = new EditMealDescriptorBuilder(firstMeal).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_SECOND_MEAL, descriptor);

        CommandTestUtil.assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_MEAL);
    }

    @Test
    public void execute_duplicateMealFilteredList_failure() {
        CommandTestUtil.showMealAtIndex(model, TypicalIndexes.INDEX_FIRST_MEAL);

        // edit meal in filtered list into a duplicate in address book
        Meal mealInList = model.getAddressBook().getMealList().get(TypicalIndexes.INDEX_SECOND_MEAL.getZeroBased());
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_MEAL,
                new EditMealDescriptorBuilder(mealInList).build());

        CommandTestUtil.assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_MEAL);
    }

    @Test
    public void execute_invalidMealIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMealList().size() + 1);
        EditCommand.EditMealDescriptor descriptor = new EditMealDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        CommandTestUtil.assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_MEAL_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidMealIndexFilteredList_failure() {
        CommandTestUtil.showMealAtIndex(model, TypicalIndexes.INDEX_FIRST_MEAL);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_MEAL;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getMealList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditMealDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build());

        CommandTestUtil.assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_MEAL_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_MEAL, CommandTestUtil.DESC_AMY);

        // same values -> returns true
        EditCommand.EditMealDescriptor copyDescriptor = new EditCommand.EditMealDescriptor(CommandTestUtil.DESC_AMY);
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
