package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.travezy.address.logic.commands.ClearCommand;
import seedu.travezy.address.logic.commands.RemarkCommand;
import seedu.travezy.commons.core.Messages;
import seedu.travezy.commons.core.index.Index;
import seedu.travezy.address.model.AddressBook;
import seedu.travezy.address.model.AddressBookModel;
import seedu.travezy.address.model.AddressBookModelManager;
import seedu.travezy.address.model.person.Person;
import seedu.travezy.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;
import seedu.travezy.model.UserPrefs;

/**
 * Contains integration tests (interaction with the AddressBookModel) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {

    private static final String REMARK_STUB = "Some remark";

    private AddressBookModel addressBookModel = new AddressBookModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRemarkUnfilteredList_success() {
        Person firstPerson = addressBookModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRemark(REMARK_STUB).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(editedPerson.getRemark().value));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        AddressBookModel expectedAddressBookModel = new AddressBookModelManager(
                new AddressBook(addressBookModel.getAddressBook()), new UserPrefs());
        expectedAddressBookModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, addressBookModel, expectedMessage, expectedAddressBookModel);
    }

    @Test
    public void execute_deleteRemarkUnfilteredList_success() {
        Person firstPerson = addressBookModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRemark("").build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new Remark(editedPerson.getRemark().toString()));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedPerson);

        AddressBookModel expectedAddressBookModel = new AddressBookModelManager(
                new AddressBook(addressBookModel.getAddressBook()), new UserPrefs());
        expectedAddressBookModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, addressBookModel, expectedMessage, expectedAddressBookModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(addressBookModel, INDEX_FIRST_PERSON);

        Person firstPerson = addressBookModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(
                addressBookModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                                .withRemark(REMARK_STUB).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(editedPerson.getRemark().value));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        AddressBookModel expectedAddressBookModel = new AddressBookModelManager(
                new AddressBook(addressBookModel.getAddressBook()), new UserPrefs());
        expectedAddressBookModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, addressBookModel, expectedMessage, expectedAddressBookModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(addressBookModel.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, new Remark(VALID_REMARK_BOB));

        assertCommandFailure(remarkCommand, addressBookModel, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(addressBookModel, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < addressBookModel.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, new Remark(VALID_REMARK_BOB));

        assertCommandFailure(remarkCommand, addressBookModel, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new Remark(VALID_REMARK_AMY));

        // same values -> returns true
        RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON,
                new Remark(VALID_REMARK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON,
                new Remark(VALID_REMARK_AMY))));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON,
                new Remark(VALID_REMARK_BOB))));
    }
}
