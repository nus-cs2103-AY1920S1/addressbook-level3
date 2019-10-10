package seedu.jarvis.logic.commands.address;

import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandInverseFailure;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandInverseSuccess;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.model.HistoryManager;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.UserPrefs;
import seedu.jarvis.model.financetracker.FinanceTracker;
import seedu.jarvis.model.person.Person;
import seedu.jarvis.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddAddressCommand}.
 */
public class AddAddressCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new HistoryManager(), new FinanceTracker(), getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getHistoryManager(), model.getFinanceTracker(),
                model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddAddressCommand(validPerson), model,
                String.format(AddAddressCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddAddressCommand(personInList), model,
                AddAddressCommand.MESSAGE_DUPLICATE_PERSON);
    }

    /**
     * Ensures that {@code CommandException} is thrown if the person to delete is not found in address book.
     */
    @Test
    public void executeInverse_personNotFound_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddAddressCommand addAddressCommand = new AddAddressCommand(validPerson);

        Model expectedModel = new ModelManager(model.getHistoryManager(), model.getFinanceTracker(),
                model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(addAddressCommand, model,
                String.format(AddAddressCommand.MESSAGE_SUCCESS, validPerson), expectedModel);

        model.deletePerson(validPerson);

        assertCommandInverseFailure(addAddressCommand, model,
                String.format(AddAddressCommand.MESSAGE_INVERSE_PERSON_NOT_FOUND, validPerson));
    }

    /**
     * Ensures that the {@code CommandResult} with the appropriate message is returned from a successful inverse
     * execution, that the person to be deleted is removed.
     */
    @Test
    public void executeInverse_success() {
        Person validPerson = new PersonBuilder().build();
        AddAddressCommand addAddressCommand = new AddAddressCommand(validPerson);

        Model expectedModel = new ModelManager(model.getHistoryManager(), model.getFinanceTracker(),
                model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(addAddressCommand, model,
                String.format(AddAddressCommand.MESSAGE_SUCCESS, validPerson), expectedModel);

        expectedModel.deletePerson(validPerson);

        assertCommandInverseSuccess(addAddressCommand, model,
                String.format(AddAddressCommand.MESSAGE_INVERSE_SUCCESS_DELETE, validPerson), expectedModel);
    }

    /**
     * Tests that repeatedly executing and inversely executing command works as intended.
     */
    @Test
    public void repeatedExecutionAndInverseExecution() {
        Person validPerson = new PersonBuilder().build();
        AddAddressCommand addAddressCommand = new AddAddressCommand(validPerson);
        Model expectedModel = new ModelManager(model.getHistoryManager(), model.getFinanceTracker(),
                model.getAddressBook(), new UserPrefs());

        int cycles = 1000;
        IntStream.range(0, cycles)
                .forEach(index -> {
                    expectedModel.addPerson(validPerson);
                    assertCommandSuccess(addAddressCommand, model,
                            String.format(AddAddressCommand.MESSAGE_SUCCESS, validPerson), expectedModel);

                    expectedModel.deletePerson(validPerson);
                    assertCommandInverseSuccess(addAddressCommand, model,
                            String.format(AddAddressCommand.MESSAGE_INVERSE_SUCCESS_DELETE, validPerson),
                            expectedModel);
                });
    }

}
