package tagline.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.logic.commands.CommandTestUtil.DESC_AMY;
import static tagline.logic.commands.CommandTestUtil.DESC_BOB;
import static tagline.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tagline.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tagline.logic.commands.CommandTestUtil.assertCommandFailure;
import static tagline.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tagline.logic.commands.CommandTestUtil.showContactAtIndex;
import static tagline.testutil.TypicalContacts.getTypicalAddressBook;
import static tagline.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;
import static tagline.testutil.TypicalIndexes.INDEX_SECOND_CONTACT;

import org.junit.jupiter.api.Test;

import tagline.commons.core.Messages;
import tagline.commons.core.index.Index;
import tagline.logic.commands.contact.ClearContactCommand;
import tagline.logic.commands.contact.EditContactCommand;
import tagline.logic.commands.contact.EditContactCommand.EditContactDescriptor;
import tagline.model.AddressBook;
import tagline.model.Model;
import tagline.model.ModelManager;
import tagline.model.UserPrefs;
import tagline.model.contact.Contact;
import tagline.testutil.ContactBuilder;
import tagline.testutil.EditContactDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and
 * unit tests for EditContactCommand.
 */
public class EditContactCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Contact editedContact = new ContactBuilder().build();
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(editedContact).build();
        EditContactCommand editContactCommand = new EditContactCommand(INDEX_FIRST_CONTACT, descriptor);

        String expectedMessage = String.format(EditContactCommand.MESSAGE_EDIT_CONTACT_SUCCESS, editedContact);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getFilteredContactList().get(0), editedContact);

        assertCommandSuccess(editContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastContact = Index.fromOneBased(model.getFilteredContactList().size());
        Contact lastContact = model.getFilteredContactList().get(indexLastContact.getZeroBased());

        ContactBuilder contactInList = new ContactBuilder(lastContact);
        Contact editedContact = contactInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .build();

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        EditContactCommand editContactCommand = new EditContactCommand(indexLastContact, descriptor);

        String expectedMessage = String.format(EditContactCommand.MESSAGE_EDIT_CONTACT_SUCCESS, editedContact);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(lastContact, editedContact);

        assertCommandSuccess(editContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditContactCommand editContactCommand =
                new EditContactCommand(INDEX_FIRST_CONTACT, new EditContactDescriptor());
        Contact editedContact = model.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased());

        String expectedMessage = String.format(EditContactCommand.MESSAGE_EDIT_CONTACT_SUCCESS, editedContact);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showContactAtIndex(model, INDEX_FIRST_CONTACT);

        Contact contactInFilteredList = model.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        Contact editedContact = new ContactBuilder(contactInFilteredList).withName(VALID_NAME_BOB).build();
        EditContactCommand editContactCommand = new EditContactCommand(INDEX_FIRST_CONTACT,
                new EditContactDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditContactCommand.MESSAGE_EDIT_CONTACT_SUCCESS, editedContact);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getFilteredContactList().get(0), editedContact);

        assertCommandSuccess(editContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateContactUnfilteredList_failure() {
        Contact firstContact = model.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(firstContact).build();
        EditContactCommand editContactCommand = new EditContactCommand(INDEX_SECOND_CONTACT, descriptor);

        assertCommandFailure(editContactCommand, model, EditContactCommand.MESSAGE_DUPLICATE_CONTACT);
    }

    @Test
    public void execute_duplicateContactFilteredList_failure() {
        showContactAtIndex(model, INDEX_FIRST_CONTACT);

        // edit contact in filtered list into a duplicate in address book
        Contact contactInList = model.getAddressBook().getContactList().get(INDEX_SECOND_CONTACT.getZeroBased());
        EditContactCommand editContactCommand = new EditContactCommand(INDEX_FIRST_CONTACT,
                new EditContactDescriptorBuilder(contactInList).build());

        assertCommandFailure(editContactCommand, model, EditContactCommand.MESSAGE_DUPLICATE_CONTACT);
    }

    @Test
    public void execute_invalidContactIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditContactCommand editContactCommand = new EditContactCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editContactCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidContactIndexFilteredList_failure() {
        showContactAtIndex(model, INDEX_FIRST_CONTACT);
        Index outOfBoundIndex = INDEX_SECOND_CONTACT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getContactList().size());

        EditContactCommand editContactCommand = new EditContactCommand(outOfBoundIndex,
                new EditContactDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editContactCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditContactCommand standardCommand = new EditContactCommand(INDEX_FIRST_CONTACT, DESC_AMY);

        // same values -> returns true
        EditContactDescriptor copyDescriptor = new EditContactDescriptor(DESC_AMY);
        EditContactCommand commandWithSameValues = new EditContactCommand(INDEX_FIRST_CONTACT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearContactCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditContactCommand(INDEX_SECOND_CONTACT, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditContactCommand(INDEX_FIRST_CONTACT, DESC_BOB)));
    }

}
