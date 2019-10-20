package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBookModel;
import seedu.address.model.AddressBookModelManager;
import seedu.main.model.UserPrefs;

/**
 * Contains integration tests (interaction with the AddressBookModel) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private AddressBookModel addressBookModel;
    private AddressBookModel expectedAddressBookModel;

    @BeforeEach
    public void setUp() {
        addressBookModel = new AddressBookModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedAddressBookModel = new AddressBookModelManager(addressBookModel.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), addressBookModel, ListCommand.MESSAGE_SUCCESS,
                expectedAddressBookModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(addressBookModel, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), addressBookModel, ListCommand.MESSAGE_SUCCESS,
                expectedAddressBookModel);
    }
}
