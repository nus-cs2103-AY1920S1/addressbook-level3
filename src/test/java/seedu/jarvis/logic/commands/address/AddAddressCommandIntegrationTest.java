package seedu.jarvis.logic.commands.address;

import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandInverseFailure;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandInverseSuccess;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    /**
     * Ensures that CommandException is thrown if the person to delete is not found in address book.
     */
    @Test
    public void executeInverse_personNotFound_exceptionThrown() {
        Person validPerson = new PersonBuilder().build();
        AddAddressCommand addAddressCommand = new AddAddressCommand(validPerson);

        AddressModel expectedAddressModel = new AddressModelManager(addressModel.getAddressBook(), new UserPrefs());
        expectedAddressModel.addPerson(validPerson);

        assertCommandSuccess(addAddressCommand, addressModel,
                String.format(AddAddressCommand.MESSAGE_SUCCESS, validPerson), expectedAddressModel);

        addressModel.deletePerson(validPerson);

        assertCommandInverseFailure(addAddressCommand, addressModel,
                String.format(AddAddressCommand.MESSAGE_INVERSE_PERSON_NOT_FOUND, validPerson));
    }

    /**
     * Ensures that the CommandResult with the appropriate message is returned from a successful inverse execution,
     * that the person to be deleted is removed.
     */
    @Test
    public void test_executeInverse_success() {
        Person validPerson = new PersonBuilder().build();
        AddAddressCommand addAddressCommand = new AddAddressCommand(validPerson);

        AddressModel expectedAddressModel = new AddressModelManager(addressModel.getAddressBook(), new UserPrefs());
        expectedAddressModel.addPerson(validPerson);

        assertCommandSuccess(addAddressCommand, addressModel,
                String.format(AddAddressCommand.MESSAGE_SUCCESS, validPerson), expectedAddressModel);

        expectedAddressModel.deletePerson(validPerson);

        assertCommandInverseSuccess(addAddressCommand, addressModel,
                String.format(AddAddressCommand.MESSAGE_INVERSE_SUCCESS_DELETE, validPerson), expectedAddressModel);
    }

    /**
     * Tests that repeatedly executing and inversely executing command works as intended.
     */
    @Test
    public void test_repeatedExecutionAndInverseExecution() {
        Person validPerson = new PersonBuilder().build();
        AddAddressCommand addAddressCommand = new AddAddressCommand(validPerson);
        AddressModel expectedAddressModel = new AddressModelManager(addressModel.getAddressBook(), new UserPrefs());

        int cycles = 1000;
        IntStream.range(0, cycles)
                .forEach(index -> {
                    expectedAddressModel.addPerson(validPerson);
                    assertCommandSuccess(addAddressCommand, addressModel,
                            String.format(AddAddressCommand.MESSAGE_SUCCESS, validPerson), expectedAddressModel);

                    expectedAddressModel.deletePerson(validPerson);
                    assertCommandInverseSuccess(addAddressCommand, addressModel,
                            String.format(AddAddressCommand.MESSAGE_INVERSE_SUCCESS_DELETE, validPerson),
                            expectedAddressModel);
                });
    }

}
