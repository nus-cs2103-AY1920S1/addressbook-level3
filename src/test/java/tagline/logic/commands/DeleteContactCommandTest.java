package tagline.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.logic.commands.CommandTestUtil.CONTACT_ID_ONE;
import static tagline.logic.commands.CommandTestUtil.CONTACT_ID_TWO;
import static tagline.logic.commands.CommandTestUtil.NON_EXISTING_ID;
import static tagline.logic.commands.CommandTestUtil.assertCommandFailure;
import static tagline.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tagline.model.contact.ContactModel.PREDICATE_SHOW_ALL_CONTACTS;
import static tagline.testutil.TypicalContacts.getTypicalAddressBook;
import static tagline.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.CommandResult.ViewType;
import tagline.logic.commands.contact.DeleteContactCommand;
import tagline.model.Model;
import tagline.model.ModelManager;
import tagline.model.UserPrefs;
import tagline.model.contact.Contact;
import tagline.model.contact.ContactId;
import tagline.model.group.GroupBook;
import tagline.model.note.NoteBook;
import tagline.model.tag.TagBook;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteContactCommand}.
 */
public class DeleteContactCommandTest {

    private static final ViewType DELETE_CONTACT_COMMAND_VIEW_TYPE = ViewType.CONTACT;
    private Model model = new ModelManager(getTypicalAddressBook(), new NoteBook(),
        new GroupBook(), new TagBook(), new UserPrefs());

    @Test
    public void execute_validContactId_success() {
        Contact contactToDelete = model.getAddressBook().getContactList().get(INDEX_FIRST.getZeroBased());
        DeleteContactCommand deleteContactCommand = new DeleteContactCommand(contactToDelete.getContactId());

        String expectedMessage = String.format(DeleteContactCommand.MESSAGE_DELETE_CONTACT_SUCCESS, contactToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new NoteBook(),
            new GroupBook(), new TagBook(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);

        assertCommandSuccess(deleteContactCommand, model, expectedMessage,
                DELETE_CONTACT_COMMAND_VIEW_TYPE, expectedModel);
    }

    @Test
    public void execute_validContactIdButUnfiltered_success() {
        Contact contactToDelete = model.getAddressBook().getContactList().get(INDEX_FIRST.getZeroBased());
        DeleteContactCommand deleteContactCommand = new DeleteContactCommand(contactToDelete.getContactId());

        String expectedMessage = String.format(DeleteContactCommand.MESSAGE_DELETE_CONTACT_SUCCESS, contactToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new NoteBook(),
             new GroupBook(), new TagBook(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);

        showNoContact(model);
        showNoContact(expectedModel);

        // added this in, so delete contact will force contact to show all
        expectedModel.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
        assertCommandSuccess(deleteContactCommand, model, expectedMessage,
                DELETE_CONTACT_COMMAND_VIEW_TYPE, expectedModel);
    }

    @Test
    public void execute_nonExistingContactId_throwsCommandException() {
        ContactId nonExistingId = NON_EXISTING_ID;
        DeleteContactCommand deleteContactCommand = new DeleteContactCommand(nonExistingId);

        assertCommandFailure(deleteContactCommand, model, DeleteContactCommand.MESSAGE_NON_EXISTING_ID);
    }

    @Test
    public void equals() {
        DeleteContactCommand deleteFirstCommand = new DeleteContactCommand(CONTACT_ID_ONE);
        DeleteContactCommand deleteSecondCommand = new DeleteContactCommand(CONTACT_ID_TWO);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteContactCommand deleteFirstCommandCopy = new DeleteContactCommand(CONTACT_ID_ONE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different contact -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoContact(Model model) {
        model.updateFilteredContactList(p -> false);

        assertTrue(model.getFilteredContactList().isEmpty());
    }
}
