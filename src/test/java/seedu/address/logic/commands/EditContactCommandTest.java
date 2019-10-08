package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditContactCommand.EditPersonDescriptor;
import seedu.address.model.Contact;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditContactCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        seedu.address.model.contact.Contact editedContact = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedContact).build();
        EditContactCommand editContactCommand = new EditContactCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditContactCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedContact);

        Model expectedModel = new ModelManager(new Contact(model.getFinSec()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedContact);

        assertCommandSuccess(editContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        seedu.address.model.contact.Contact lastContact = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastContact);
        seedu.address.model.contact.Contact editedContact = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditContactCommand editContactCommand = new EditContactCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditContactCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedContact);

        Model expectedModel = new ModelManager(new Contact(model.getFinSec()), new UserPrefs());
        expectedModel.setPerson(lastContact, editedContact);

        assertCommandSuccess(editContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditContactCommand editContactCommand = new EditContactCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
        seedu.address.model.contact.Contact editedContact = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditContactCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedContact);

        Model expectedModel = new ModelManager(new Contact(model.getFinSec()), new UserPrefs());

        assertCommandSuccess(editContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        seedu.address.model.contact.Contact contactInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        seedu.address.model.contact.Contact editedContact = new PersonBuilder(contactInFilteredList).withName(VALID_NAME_BOB).build();
        EditContactCommand editContactCommand = new EditContactCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditContactCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedContact);

        Model expectedModel = new ModelManager(new Contact(model.getFinSec()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedContact);

        assertCommandSuccess(editContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        seedu.address.model.contact.Contact firstContact = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstContact).build();
        EditContactCommand editContactCommand = new EditContactCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editContactCommand, model, EditContactCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit contact in filtered list into a duplicate in address book
        seedu.address.model.contact.Contact contactInList = model.getFinSec().getContactList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditContactCommand editContactCommand = new EditContactCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(contactInList).build());

        assertCommandFailure(editContactCommand, model, EditContactCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditContactCommand editContactCommand = new EditContactCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editContactCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinSec().getContactList().size());

        EditContactCommand editContactCommand = new EditContactCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editContactCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditContactCommand standardCommand = new EditContactCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditContactCommand commandWithSameValues = new EditContactCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditContactCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditContactCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

}
