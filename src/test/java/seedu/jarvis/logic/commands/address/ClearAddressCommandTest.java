package seedu.jarvis.logic.commands.address;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandInverseSuccess;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.model.AddressBook;
import seedu.jarvis.model.AddressModel;
import seedu.jarvis.model.AddressModelManager;
import seedu.jarvis.model.UserPrefs;
import seedu.jarvis.model.person.Person;
import seedu.jarvis.testutil.PersonBuilder;

public class ClearAddressCommandTest {
    private AddressModel addressModel;

    @BeforeEach
    public void setUp() {
        addressModel = new AddressModelManager();
    }

    /**
     * Verifies that checking ClearAddressCommand for the availability of inverse execution returns true.
     */
    @Test
    public void test_hasInverseExecution() {
        ClearAddressCommand clearAddressCommand = new ClearAddressCommand();
        assertTrue(clearAddressCommand.hasInverseExecution());
    }

    @Test
    public void execute_emptyAddressBook_success() {
        AddressModel expectedAddressModel = new AddressModelManager();

        assertCommandSuccess(new ClearAddressCommand(), addressModel,
                ClearAddressCommand.MESSAGE_SUCCESS, expectedAddressModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        addressModel = new AddressModelManager(getTypicalAddressBook(), new UserPrefs());
        AddressModel expectedAddressModel = new AddressModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedAddressModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearAddressCommand(), addressModel,
                ClearAddressCommand.MESSAGE_SUCCESS, expectedAddressModel);
    }

    /**
     * Ensures that the CommandResult with the appropriate message is returned from a successful inverse execution,
     * that the address book is restored to all its previous data.
     */
    @Test
    public void executeInverse_success() {
        ClearAddressCommand clearAddressCommand = new ClearAddressCommand();
        addressModel = new AddressModelManager(getTypicalAddressBook(), new UserPrefs());
        Person validPerson = new PersonBuilder().build();
        addressModel.addPerson(validPerson);
        AddressModel expectedAddressModel = new AddressModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedAddressModel.setAddressBook(new AddressBook());

        assertCommandSuccess(clearAddressCommand, addressModel,
                ClearAddressCommand.MESSAGE_SUCCESS, expectedAddressModel);

        addressModel.setAddressBook(getTypicalAddressBook());

        assertCommandInverseSuccess(clearAddressCommand, addressModel,
                ClearAddressCommand.MESSAGE_INVERSE_SUCCESS_RESTORE, expectedAddressModel);
    }

    /**
     * Tests that repeatedly executing and inversely executing command works as intended.
     */
    @Test
    public void test_repeatedExecutionAndInverseExecution() {
        ClearAddressCommand clearAddressCommand = new ClearAddressCommand();
        addressModel = new AddressModelManager(getTypicalAddressBook(), new UserPrefs());
        AddressModel expectedAddressModel = new AddressModelManager(getTypicalAddressBook(), new UserPrefs());
        Person validPerson = new PersonBuilder().build();

        int cycles = 1000;
        IntStream.range(0, cycles)
                .forEach(index -> {
                    addressModel.addPerson(validPerson);
                    expectedAddressModel.setAddressBook(new AddressBook());
                    assertCommandSuccess(clearAddressCommand, addressModel,
                            ClearAddressCommand.MESSAGE_SUCCESS, expectedAddressModel);

                    addressModel.setAddressBook(getTypicalAddressBook());
                    assertCommandInverseSuccess(clearAddressCommand, addressModel,
                            ClearAddressCommand.MESSAGE_INVERSE_SUCCESS_RESTORE, expectedAddressModel);
                });
    }
}
