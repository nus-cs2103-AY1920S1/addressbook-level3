package seedu.jarvis.logic.commands.address;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandInverseFailure;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandInverseSuccess;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.jarvis.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.model.AddressModel;
import seedu.jarvis.model.AddressModelManager;
import seedu.jarvis.model.UserPrefs;
import seedu.jarvis.model.person.Person;
import seedu.jarvis.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the AddressModel, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteAddressCommand}.
 */
public class DeleteAddressCommandTest {

    private AddressModel addressModel;

    @BeforeEach
    public void setUp() {
        addressModel = new AddressModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    /**
     * Verifies that checking DeleteAddressCommand for the availability of inverse execution returns true.
     */
    @Test
    public void test_hasInverseExecution() {
        DeleteAddressCommand deleteAddressCommand = new DeleteAddressCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteAddressCommand.hasInverseExecution());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = addressModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteAddressCommand deleteAddressCommand = new DeleteAddressCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteAddressCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        AddressModelManager expectedModel = new AddressModelManager(addressModel.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteAddressCommand, addressModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(addressModel.getFilteredPersonList().size() + 1);
        DeleteAddressCommand deleteAddressCommand = new DeleteAddressCommand(outOfBoundIndex);

        assertCommandFailure(deleteAddressCommand, addressModel, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(addressModel, INDEX_FIRST_PERSON);

        Person personToDelete = addressModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteAddressCommand deleteAddressCommand = new DeleteAddressCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteAddressCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        AddressModel expectedAddressModel = new AddressModelManager(addressModel.getAddressBook(), new UserPrefs());
        expectedAddressModel.deletePerson(personToDelete);
        showNoPerson(expectedAddressModel);

        assertCommandSuccess(deleteAddressCommand, addressModel, expectedMessage, expectedAddressModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(addressModel, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < addressModel.getAddressBook().getPersonList().size());

        DeleteAddressCommand deleteAddressCommand = new DeleteAddressCommand(outOfBoundIndex);

        assertCommandFailure(deleteAddressCommand, addressModel, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Ensures that CommandException is thrown if re-adding the person that was deleted will be in conflict with
     * existing person in the address book.
     */
    @Test
    public void executeInverse_personToAddAlreadyExist_exceptionThrown() {
        showPersonAtIndex(addressModel, INDEX_FIRST_PERSON);

        Person personToDelete = addressModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteAddressCommand deleteAddressCommand = new DeleteAddressCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteAddressCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        AddressModel expectedAddressModel = new AddressModelManager(addressModel.getAddressBook(), new UserPrefs());
        expectedAddressModel.deletePerson(personToDelete);
        showNoPerson(expectedAddressModel);

        assertCommandSuccess(deleteAddressCommand, addressModel, expectedMessage, expectedAddressModel);

        String inverseExpectedMessage = String.format(
                DeleteAddressCommand.MESSAGE_INVERSE_PERSON_TO_ADD_ALREADY_EXIST, personToDelete);

        addressModel.addPerson(personToDelete);
        assertCommandInverseFailure(deleteAddressCommand, addressModel, inverseExpectedMessage);
    }

    /**
     * Ensures that the CommandResult with the appropriate message is returned from a successful inverse execution,
     * that the deleted person was added back to the address book.
     */
    @Test
    public void executeInverse_success() {
        showPersonAtIndex(addressModel, INDEX_FIRST_PERSON);

        Person personToDelete = addressModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteAddressCommand deleteAddressCommand = new DeleteAddressCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteAddressCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        AddressModel expectedAddressModel = new AddressModelManager(addressModel.getAddressBook(), new UserPrefs());
        expectedAddressModel.deletePerson(personToDelete);
        showNoPerson(expectedAddressModel);
        assertCommandSuccess(deleteAddressCommand, addressModel, expectedMessage, expectedAddressModel);

        String inverseExpectedMessage = String.format(
                DeleteAddressCommand.MESSAGE_INVERSE_SUCCESS_ADD, personToDelete);

        expectedAddressModel.addPerson(personToDelete);
        assertCommandInverseSuccess(deleteAddressCommand, addressModel, inverseExpectedMessage, expectedAddressModel);
    }

    /**
     * Tests that repeatedly executing and inversely executing command works as intended.
     */
    @Test
    public void test_repeatedExecutionAndInverseExecution() {
        showPersonAtIndex(addressModel, INDEX_FIRST_PERSON);

        DeleteAddressCommand deleteAddressCommand = new DeleteAddressCommand(INDEX_FIRST_PERSON);

        addressModel = new AddressModelManager();
        addressModel.addPerson(new PersonBuilder().build());
        AddressModel expectedAddressModel = new AddressModelManager(addressModel.getAddressBook(), new UserPrefs());

        int cycles = 1000;
        IntStream.range(0, cycles)
                .forEach(index -> {
                    Person personToDelete = addressModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
                    String expectedMessage = String.format(DeleteAddressCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                            personToDelete);
                    String inverseExpectedMessage = String.format(
                            DeleteAddressCommand.MESSAGE_INVERSE_SUCCESS_ADD, personToDelete);

                    expectedAddressModel.deletePerson(personToDelete);
                    showNoPerson(expectedAddressModel);
                    assertCommandSuccess(deleteAddressCommand, addressModel, expectedMessage, expectedAddressModel);

                    expectedAddressModel.addPerson(personToDelete);
                    assertCommandInverseSuccess(deleteAddressCommand, addressModel, inverseExpectedMessage,
                            expectedAddressModel);
                });
    }

    @Test
    public void equals() {
        DeleteAddressCommand deleteFirstCommand = new DeleteAddressCommand(INDEX_FIRST_PERSON);
        DeleteAddressCommand deleteSecondCommand = new DeleteAddressCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteAddressCommand deleteFirstCommandCopy = new DeleteAddressCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code addressModel}'s filtered list to show no one.
     */
    private void showNoPerson(AddressModel addressModel) {
        addressModel.updateFilteredPersonList(p -> false);

        assertTrue(addressModel.getFilteredPersonList().isEmpty());
    }
}
