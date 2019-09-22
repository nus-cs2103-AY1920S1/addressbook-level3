package seedu.jarvis.logic.commands.address;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.jarvis.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.jarvis.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.jarvis.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.jarvis.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.jarvis.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandInverseFailure;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandInverseSuccess;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.jarvis.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.address.EditAddressCommand.EditPersonDescriptor;
import seedu.jarvis.model.AddressBook;
import seedu.jarvis.model.AddressModel;
import seedu.jarvis.model.AddressModelManager;
import seedu.jarvis.model.UserPrefs;
import seedu.jarvis.model.person.Person;
import seedu.jarvis.testutil.EditPersonDescriptorBuilder;
import seedu.jarvis.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the AddressModel, UndoCommand and RedoCommand)
 * and unit tests for EditAddressCommand.
 */
public class EditAddressCommandTest {

    private AddressModel addressModel;

    @BeforeEach
    public void setUp() {
        addressModel = new AddressModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    /**
     * Verifies that checking EditAddressCommand for the availability of inverse execution returns true.
     */
    @Test
    public void test_hasInverseExecution() {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditAddressCommand editAddressCommand = new EditAddressCommand(INDEX_FIRST_PERSON, descriptor);
        assertTrue(editAddressCommand.hasInverseExecution());
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditAddressCommand editAddressCommand = new EditAddressCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditAddressCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        AddressModel expectedAddressModel = new AddressModelManager(new AddressBook(addressModel.getAddressBook()),
                new UserPrefs());
        expectedAddressModel.setPerson(addressModel.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editAddressCommand, addressModel, expectedMessage, expectedAddressModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(addressModel.getFilteredPersonList().size());
        Person lastPerson = addressModel.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditAddressCommand editAddressCommand = new EditAddressCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditAddressCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        AddressModel expectedAddressModel = new AddressModelManager(new AddressBook(addressModel.getAddressBook()),
                new UserPrefs());
        expectedAddressModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(editAddressCommand, addressModel, expectedMessage, expectedAddressModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditAddressCommand editAddressCommand = new EditAddressCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
        Person editedPerson = addressModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditAddressCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        AddressModel expectedAddressModel = new AddressModelManager(new AddressBook(addressModel.getAddressBook()),
                new UserPrefs());

        assertCommandSuccess(editAddressCommand, addressModel, expectedMessage, expectedAddressModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(addressModel, INDEX_FIRST_PERSON);

        Person personInFilteredList = addressModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditAddressCommand editAddressCommand = new EditAddressCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditAddressCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        AddressModel expectedAddressModel = new AddressModelManager(new AddressBook(addressModel.getAddressBook()),
                new UserPrefs());
        expectedAddressModel.setPerson(addressModel.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editAddressCommand, addressModel, expectedMessage, expectedAddressModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = addressModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditAddressCommand editAddressCommand = new EditAddressCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editAddressCommand, addressModel, EditAddressCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(addressModel, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Person personInList = addressModel.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditAddressCommand editAddressCommand = new EditAddressCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(personInList).build());

        assertCommandFailure(editAddressCommand, addressModel, EditAddressCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(addressModel.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditAddressCommand editAddressCommand = new EditAddressCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editAddressCommand, addressModel, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(addressModel, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < addressModel.getAddressBook().getPersonList().size());

        EditAddressCommand editAddressCommand = new EditAddressCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editAddressCommand, addressModel, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Ensures that CommandException is thrown with the correct message if the edited person to be reverted is not
     * in address book when inverse execution is invoked.
     */
    @Test
    public void executeInverse_editedPersonNotFound_exceptionThrown() {
        showPersonAtIndex(addressModel, INDEX_FIRST_PERSON);

        Person personInFilteredList = addressModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditAddressCommand editAddressCommand = new EditAddressCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String executionExpectedMessage = String.format(EditAddressCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        AddressModel expectedAddressModel = new AddressModelManager(new AddressBook(addressModel.getAddressBook()),
                new UserPrefs());
        expectedAddressModel.setPerson(addressModel.getFilteredPersonList().get(0), editedPerson);
        assertCommandSuccess(editAddressCommand, addressModel, executionExpectedMessage, expectedAddressModel);

        String inverseExecutionExpectedMessage = EditAddressCommand.MESSAGE_INVERSE_PERSON_NOT_FOUND;

        addressModel.deletePerson(editedPerson);
        assertCommandInverseFailure(editAddressCommand, addressModel, inverseExecutionExpectedMessage);
    }

    /**
     * Ensures that CommandException is thrown if reverting the edited person will conflict with an existing
     * person in the address book.
     */
    @Test
    public void executeInverse_originalPersonAlreadyInAddressBook_exceptionThrown() {
        showPersonAtIndex(addressModel, INDEX_FIRST_PERSON);

        Person personInFilteredList = addressModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditAddressCommand editAddressCommand = new EditAddressCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String executionExpectedMessage = String.format(EditAddressCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        AddressModel expectedAddressModel = new AddressModelManager(new AddressBook(addressModel.getAddressBook()),
                new UserPrefs());
        expectedAddressModel.setPerson(addressModel.getFilteredPersonList().get(0), editedPerson);
        assertCommandSuccess(editAddressCommand, addressModel, executionExpectedMessage, expectedAddressModel);

        String inverseExecutionExpectedMessage = EditAddressCommand.MESSAGE_INVERSE_CONFLICT_WITH_EXISTING_PERSON;

        addressModel.addPerson(personInFilteredList);
        assertCommandInverseFailure(editAddressCommand, addressModel, inverseExecutionExpectedMessage);
    }

    /**
     * Ensures that the CommandResult with the appropriate message is returned from a successful inverse execution,
     * that edits made to person was reverted.
     */
    @Test
    public void executeInverse_success() {
        showPersonAtIndex(addressModel, INDEX_FIRST_PERSON);

        Person personInFilteredList = addressModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditAddressCommand editAddressCommand = new EditAddressCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String executionExpectedMessage = String.format(EditAddressCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        AddressModel expectedAddressModel = new AddressModelManager(new AddressBook(addressModel.getAddressBook()),
                new UserPrefs());
        expectedAddressModel.setPerson(addressModel.getFilteredPersonList().get(0), editedPerson);
        assertCommandSuccess(editAddressCommand, addressModel, executionExpectedMessage, expectedAddressModel);

        String inverseExecutionExpectedMessage = EditAddressCommand.MESSAGE_INVERSE_SUCCESS_EDIT;

        expectedAddressModel.setPerson(addressModel.getFilteredPersonList().get(0), personInFilteredList);
        assertCommandInverseSuccess(editAddressCommand, addressModel, inverseExecutionExpectedMessage,
                expectedAddressModel);
    }

    /**
     * Tests that repeatedly executing and inversely executing command works as intended.
     */
    @Test
    public void test_repeatedExecutionAndInverseExecution() {
        showPersonAtIndex(addressModel, INDEX_FIRST_PERSON);

        Person personInFilteredList = addressModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();

        EditAddressCommand editAddressCommand = new EditAddressCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());
        AddressModel expectedAddressModel = new AddressModelManager(new AddressBook(addressModel.getAddressBook()),
                new UserPrefs());

        String executionExpectedMessage = String.format(EditAddressCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                editedPerson);
        String inverseExecutionExpectedMessage = EditAddressCommand.MESSAGE_INVERSE_SUCCESS_EDIT;

        int cycles = 1000;

        for (int i = 0; i < cycles; ++i) {
            expectedAddressModel.setPerson(addressModel.getFilteredPersonList().get(0), editedPerson);
            assertCommandSuccess(editAddressCommand, addressModel, executionExpectedMessage, expectedAddressModel);

            expectedAddressModel.setPerson(addressModel.getFilteredPersonList().get(0), personInFilteredList);
            assertCommandInverseSuccess(editAddressCommand, addressModel, inverseExecutionExpectedMessage,
                    expectedAddressModel);
        };
    }

    @Test
    public void equals() {
        final EditAddressCommand standardCommand = new EditAddressCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditAddressCommand commandWithSameValues = new EditAddressCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearAddressCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditAddressCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditAddressCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

}
