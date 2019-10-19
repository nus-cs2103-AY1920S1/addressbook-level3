package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.AddressBookModel;
import seedu.address.model.AddressBookModelManager;
import seedu.main.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        AddressBookModel addressBookModel = new AddressBookModelManager();
        AddressBookModel expectedAddressBookModel = new AddressBookModelManager();

        assertCommandSuccess(new ClearCommand(), addressBookModel, ClearCommand.MESSAGE_SUCCESS,
                expectedAddressBookModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        AddressBookModel addressBookModel = new AddressBookModelManager(getTypicalAddressBook(), new UserPrefs());
        AddressBookModel expectedAddressBookModel = new AddressBookModelManager(getTypicalAddressBook(),
                new UserPrefs());
        expectedAddressBookModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), addressBookModel, ClearCommand.MESSAGE_SUCCESS,
                expectedAddressBookModel);
    }

}
