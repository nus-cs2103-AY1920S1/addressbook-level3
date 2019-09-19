package seedu.jarvis.logic.commands;

import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.jarvis.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.address.ListAddressCommand;
import seedu.jarvis.model.AddressModel;
import seedu.jarvis.model.AddressModelManager;
import seedu.jarvis.model.UserPrefs;

/**
 * Contains integration tests (interaction with the AddressModel) and unit tests for ListAddressCommand.
 */
public class ListAddressCommandTest {

    private AddressModel addressModel;
    private AddressModel expectedAddressModel;

    @BeforeEach
    public void setUp() {
        addressModel = new AddressModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedAddressModel = new AddressModelManager(addressModel.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListAddressCommand(), addressModel, ListAddressCommand.MESSAGE_SUCCESS, expectedAddressModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(addressModel, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListAddressCommand(), addressModel, ListAddressCommand.MESSAGE_SUCCESS, expectedAddressModel);
    }
}
