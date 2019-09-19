package seedu.jarvis.logic.commands;

import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.address.ClearAddressCommand;
import seedu.jarvis.model.AddressBook;
import seedu.jarvis.model.AddressModel;
import seedu.jarvis.model.AddressModelManager;
import seedu.jarvis.model.UserPrefs;

public class ClearAddressCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        AddressModel addressModel = new AddressModelManager();
        AddressModel expectedAddressModel = new AddressModelManager();

        assertCommandSuccess(new ClearAddressCommand(), addressModel,
                ClearAddressCommand.MESSAGE_SUCCESS, expectedAddressModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        AddressModel addressModel = new AddressModelManager(getTypicalAddressBook(), new UserPrefs());
        AddressModel expectedAddressModel = new AddressModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedAddressModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearAddressCommand(), addressModel,
                ClearAddressCommand.MESSAGE_SUCCESS, expectedAddressModel);
    }

}
