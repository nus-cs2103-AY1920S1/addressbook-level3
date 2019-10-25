package seedu.ifridge.logic.commands.templatelist;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.ifridge.logic.commands.templatelist.template.DeleteTemplateItemCommand;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteTemplateListCommandTest {

    /**private Model model = new ModelManager(getTypicalTemplateList(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        TemplateItem itemToDelete = model.getFilteredTemplateItemList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteTemplateItemCommand deleteTemplateCommand = new DeleteTemplateItemCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteTemplateItemCommand.MESSAGE_DELETE_TEMPLATE_ITEM_SUCCESS,
            itemToDelete);

        ModelManager expectedModel = new ModelManager(model.getTemplate(), new UserPrefs());
        expectedModel.deleteTemplateItem(itemToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTemplateItemList().size() + 1);
        DeleteTemplateItemCommand deleteTemplateCommand = new DeleteTemplateItemCommand(outOfBoundIndex);

        assertCommandFailure(deleteTemplateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showTemplateItemAtIndex(model, INDEX_FIRST_PERSON);

        TemplateItem itemToDelete = model.getFilteredTemplateItemList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteTemplateItemCommand deleteTemplateCommand = new DeleteTemplateItemCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteTemplateItemCommand.MESSAGE_DELETE_TEMPLATE_ITEM_SUCCESS,
            itemToDelete);

        Model expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs());
        expectedModel.deleteTemplateItem(itemToDelete);
        showNoTemplateItem(expectedModel);

        assertCommandSuccess(deleteTemplateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTemplateItemAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTemplate().getTemplate().size());

        DeleteTemplateItemCommand deleteTemplateCommand = new DeleteTemplateItemCommand(outOfBoundIndex);

        assertCommandFailure(deleteTemplateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    **/
    @Test
    public void equals() {
        DeleteTemplateItemCommand deleteFirstCommand = new DeleteTemplateItemCommand(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON);
        DeleteTemplateItemCommand deleteSecondCommand = new DeleteTemplateItemCommand(INDEX_SECOND_PERSON,
                INDEX_FIRST_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTemplateItemCommand deleteFirstCommandCopy = new DeleteTemplateItemCommand(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    /**
    private void showNoTemplateItem(Model model) {
        model.updateFilteredTemplateItemList(p -> false);

        assertTrue(model.getFilteredTemplateItemList().isEmpty());
    }
    **/
}
