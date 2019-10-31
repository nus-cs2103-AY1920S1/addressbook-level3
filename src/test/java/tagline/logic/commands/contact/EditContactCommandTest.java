package tagline.logic.commands.contact;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.logic.commands.CommandTestUtil.DESC_AMY;
import static tagline.logic.commands.CommandTestUtil.DESC_BOB;
import static tagline.logic.commands.CommandTestUtil.NON_EXISTING_ID;
import static tagline.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tagline.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tagline.logic.commands.CommandTestUtil.assertCommandFailure;
import static tagline.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tagline.testutil.TypicalIndexes.INDEX_FIRST;
import static tagline.testutil.TypicalIndexes.INDEX_SECOND;
import static tagline.testutil.contact.TypicalContacts.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.CommandResult.ViewType;
import tagline.logic.commands.contact.EditContactCommand.EditContactDescriptor;
import tagline.model.Model;
import tagline.model.ModelManager;
import tagline.model.UserPrefs;
import tagline.model.contact.AddressBook;
import tagline.model.contact.Contact;
import tagline.model.contact.ContactBuilder;
import tagline.model.contact.ContactId;
import tagline.model.group.GroupBook;
import tagline.model.note.NoteBook;
import tagline.model.tag.TagBook;
import tagline.testutil.contact.EditContactDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and
 * unit tests for EditContactCommand.
 */
public class EditContactCommandTest {

    private static final ViewType EDIT_CONTACT_COMMAND_VIEW_TYPE = ViewType.CONTACT;
    private Model model = new ModelManager(getTypicalAddressBook(), new NoteBook(),
        new GroupBook(), new TagBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() {
        Contact originalContact = model.getAddressBook().getContactList().get(INDEX_FIRST.getZeroBased());
        Contact editedContact = new ContactBuilder().withId(originalContact.getContactId().value).build();

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(editedContact).build();
        EditContactCommand editContactCommand = new EditContactCommand(originalContact.getContactId(), descriptor);

        String expectedMessage = String.format(EditContactCommand.MESSAGE_EDIT_CONTACT_SUCCESS, editedContact);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new NoteBook(), new GroupBook(), new TagBook(), new UserPrefs());
        expectedModel.setContact(model.getFilteredContactList().get(0), editedContact);

        assertCommandSuccess(editContactCommand, model, expectedMessage, EDIT_CONTACT_COMMAND_VIEW_TYPE, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        var contactList = model.getAddressBook().getContactList();
        int contactListSize = contactList.size();
        Contact lastContact = contactList.get(contactListSize - 1);

        ContactBuilder contactInList = new ContactBuilder(lastContact);
        Contact editedContact = contactInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .build();

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).build();
        EditContactCommand editContactCommand = new EditContactCommand(lastContact.getContactId(), descriptor);

        String expectedMessage = String.format(EditContactCommand.MESSAGE_EDIT_CONTACT_SUCCESS, editedContact);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new NoteBook(), new GroupBook(), new TagBook(), new UserPrefs());
        expectedModel.setContact(lastContact, editedContact);

        assertCommandSuccess(editContactCommand, model, expectedMessage, EDIT_CONTACT_COMMAND_VIEW_TYPE, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() {
        Contact editedContact = model.getFilteredContactList().get(INDEX_FIRST.getZeroBased());
        EditContactCommand editContactCommand =
            new EditContactCommand(editedContact.getContactId(), new EditContactDescriptor());

        String expectedMessage = String.format(EditContactCommand.MESSAGE_EDIT_CONTACT_SUCCESS, editedContact);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new NoteBook(),
            new GroupBook(), new TagBook(), new UserPrefs());

        assertCommandSuccess(editContactCommand, model, expectedMessage, EDIT_CONTACT_COMMAND_VIEW_TYPE, expectedModel);
    }

    @Test
    public void execute_duplicateContact_failure() {
        var contactList = model.getAddressBook().getContactList();
        Contact firstContact = contactList.get(INDEX_FIRST.getZeroBased());
        Contact secondContact = contactList.get(INDEX_SECOND.getZeroBased());

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(firstContact).build();
        EditContactCommand editContactCommand = new EditContactCommand(secondContact.getContactId(), descriptor);

        assertCommandFailure(editContactCommand, model, EditContactCommand.MESSAGE_DUPLICATE_CONTACT);
    }

    @Test
    public void execute_invalidContactIdUnfilteredList_failure() {
        ContactId nonExistingId = NON_EXISTING_ID;

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditContactCommand editContactCommand = new EditContactCommand(nonExistingId, descriptor);

        assertCommandFailure(editContactCommand, model, EditContactCommand.MESSAGE_NON_EXISTING_ID);
    }

    @Test
    public void equals() {
        final EditContactCommand standardCommand = new EditContactCommand(new ContactId(1), DESC_AMY);

        // same values -> returns true
        EditContactDescriptor copyDescriptor = new EditContactDescriptor(DESC_AMY);
        EditContactCommand commandWithSameValues = new EditContactCommand(new ContactId(1), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearContactCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditContactCommand(new ContactId(2), DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditContactCommand(new ContactId(1), DESC_BOB)));
    }
}
