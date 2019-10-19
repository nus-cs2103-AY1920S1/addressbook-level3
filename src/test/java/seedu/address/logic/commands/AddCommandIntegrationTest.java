package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBookModel;
import seedu.address.model.AddressBookModelManager;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.main.model.UserPrefs;

/**
 * Contains integration tests (interaction with the AddressBookModel) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private AddressBookModel addressBookModel;

    @BeforeEach
    public void setUp() {
        addressBookModel = new AddressBookModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        AddressBookModel expectedAddressBookModel = new AddressBookModelManager(addressBookModel.getAddressBook(),
                new UserPrefs());
        expectedAddressBookModel.addPerson(validPerson);

        assertCommandSuccess(new AddCommand(validPerson), addressBookModel,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedAddressBookModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = addressBookModel.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), addressBookModel, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
