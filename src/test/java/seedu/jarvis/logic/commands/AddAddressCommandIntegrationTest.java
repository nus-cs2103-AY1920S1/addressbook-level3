package seedu.jarvis.logic.commands;

import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.address.AddAddressCommand;
import seedu.jarvis.model.AddressModel;
import seedu.jarvis.model.AddressModelManager;
import seedu.jarvis.model.UserPrefs;
import seedu.jarvis.model.person.Person;
import seedu.jarvis.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the AddressModel) for {@code AddAddressCommand}.
 */
public class AddAddressCommandIntegrationTest {

    private AddressModel addressModel;

    @BeforeEach
    public void setUp() {
        addressModel = new AddressModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        AddressModel expectedAddressModel = new AddressModelManager(addressModel.getAddressBook(), new UserPrefs());
        expectedAddressModel.addPerson(validPerson);

        assertCommandSuccess(new AddAddressCommand(validPerson), addressModel,
                String.format(AddAddressCommand.MESSAGE_SUCCESS, validPerson), expectedAddressModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = addressModel.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddAddressCommand(personInList), addressModel,
                AddAddressCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
