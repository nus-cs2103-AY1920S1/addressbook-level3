package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalGroceryItems.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalShoppingList.getTypicalShoppingList;
import static seedu.address.testutil.TypicalTemplateList.getTypicalTemplateList;
import static seedu.address.testutil.TypicalWasteList.getTypicalWasteList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditFoodDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.EditFoodDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalTemplateList(),
            getTypicalWasteList(), getTypicalShoppingList());

    /*@Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        GroceryItem editedFood = new GroceryItemBuilder().build();
        EditTemplateItemDescriptor descriptor = new EditFoodDescriptorBuilder(editedFood).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedFood);

        Model expectedModel = new ModelManager(new AddressBook(model.getGroceryList()), new UserPrefs());
        expectedModel.setGroceryItem(model.getFilteredGroceryItemList().get(0), editedFood);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredGroceryItemList().size());
        GroceryItem lastFood = model.getFilteredGroceryItemList().get(indexLastPerson.getZeroBased());

        GroceryItemBuilder personInList = new GroceryItemBuilder(lastFood);
        GroceryItem editedFood = personInList.withName(VALID_NAME_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditTemplateItemDescriptor descriptor = new EditFoodDescriptorBuilder().withName(VALID_NAME_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedFood);

        Model expectedModel = new ModelManager(new AddressBook(model.getGroceryList()), new UserPrefs());
        expectedModel.setGroceryItem(lastFood, editedFood);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditTemplateItemDescriptor());
        Food editedFood = model.getFilteredGroceryItemList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedFood);

        Model expectedModel = new ModelManager(new AddressBook(model.getGroceryList()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        GroceryItem foodInFilteredList = model.getFilteredGroceryItemList().get(INDEX_FIRST_PERSON.getZeroBased());
        GroceryItem editedFood = new GroceryItemBuilder(foodInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditFoodDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedFood);

        Model expectedModel = new ModelManager(new AddressBook(model.getGroceryList()), new UserPrefs());
        expectedModel.setGroceryItem(model.getFilteredGroceryItemList().get(0), editedFood);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }*/

    /*@Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Food firstFood = model.getFilteredGroceryItemList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditTemplateItemDescriptor descriptor = new EditFoodDescriptorBuilder(firstFood).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }*/

    /*@Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Food foodInList = model.getGroceryList().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditFoodDescriptorBuilder(foodInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }*/

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGroceryItemList().size() + 1);
        EditFoodDescriptor descriptor = new EditFoodDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getGroceryList().getPersonList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditFoodDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /*@Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditTemplateItemDescriptor copyDescriptor = new EditTemplateItemDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }*/

}
