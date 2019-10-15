package seedu.jarvis.logic.commands.address;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandInverseSuccess;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.address.TypicalPersons.AMY;
import static seedu.jarvis.testutil.address.TypicalPersons.BENSON;
import static seedu.jarvis.testutil.address.TypicalPersons.CARL;
import static seedu.jarvis.testutil.address.TypicalPersons.DANIEL;
import static seedu.jarvis.testutil.address.TypicalPersons.ELLE;
import static seedu.jarvis.testutil.address.TypicalPersons.GEORGE;
import static seedu.jarvis.testutil.address.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.address.AddressBook;
import seedu.jarvis.model.address.person.Person;

import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.userprefs.UserPrefs;
import seedu.jarvis.testutil.address.PersonBuilder;

public class ClearAddressCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    /**
     * Verifies that checking {@code ClearAddressCommand} for the availability of inverse execution returns true.
     */
    @Test
    public void hasInverseExecution() {
        ClearAddressCommand clearAddressCommand = new ClearAddressCommand();
        assertTrue(clearAddressCommand.hasInverseExecution());
    }

    @Test
    public void execute_emptyAddressBook_success() {
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearAddressCommand(), model,
                ClearAddressCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {

        model = new ModelManager(new CcaTracker(), new HistoryManager(), getTypicalAddressBook(), new UserPrefs(),
                new Planner());
        Model expectedModel = new ModelManager(new CcaTracker(), new HistoryManager(), getTypicalAddressBook(),
                new UserPrefs(), new Planner());

        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearAddressCommand(), model,
                ClearAddressCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Ensures that the {@code CommandResult} with the appropriate message is returned from a successful inverse
     * execution, that the address book is restored to all its previous data.
     */
    @Test
    public void executeInverse_success() {
        ClearAddressCommand clearAddressCommand = new ClearAddressCommand();
        model = new ModelManager(new CcaTracker(), new HistoryManager(), getTypicalAddressBook(),
                                 new UserPrefs(), new Planner());
        Person validPerson = new PersonBuilder().build();
        model.addPerson(validPerson);
        Model expectedModel = new ModelManager(new CcaTracker(), new HistoryManager(), getTypicalAddressBook(),
                new UserPrefs(), new Planner());
        expectedModel.setAddressBook(new AddressBook());
        assertCommandSuccess(clearAddressCommand, model, ClearAddressCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.setAddressBook(getTypicalAddressBook());
        assertCommandInverseSuccess(clearAddressCommand, model, ClearAddressCommand.MESSAGE_INVERSE_SUCCESS_RESTORE,
                expectedModel);
    }

    /**
     * Tests that repeatedly executing and inversely executing command works as intended.
     */
    @Test
    public void repeatedExecutionAndInverseExecution() {
        ClearAddressCommand clearAddressCommand = new ClearAddressCommand();
        model = new ModelManager(new CcaTracker(), new HistoryManager(), getTypicalAddressBook(),
                                 new UserPrefs(), new Planner());
        Model expectedModel = new ModelManager(new CcaTracker(), new HistoryManager(), getTypicalAddressBook(),
                new UserPrefs(), new Planner());
        Person validPerson = new PersonBuilder().build();

        int cycles = 1000;
        IntStream.range(0, cycles)
                .forEach(index -> {
                    expectedModel.setAddressBook(new AddressBook());
                    assertCommandSuccess(clearAddressCommand, model, ClearAddressCommand.MESSAGE_SUCCESS,
                            expectedModel);

                    expectedModel.setAddressBook(getTypicalAddressBook());
                    assertCommandInverseSuccess(clearAddressCommand, model,
                            ClearAddressCommand.MESSAGE_INVERSE_SUCCESS_RESTORE, expectedModel);
                });
    }

    /**
     * Tests for equality for {@code ClearAddressCommand} objects.
     */
    @Test
    public void equals() {
        List<Person> list = Arrays.asList(AMY, BENSON, CARL);
        ClearAddressCommand clearAddressCommand = new ClearAddressCommand(list);

        // null -> false.
        assertFalse(clearAddressCommand.equals(null));
        // compare with same instance -> true.
        assertTrue(clearAddressCommand.equals(clearAddressCommand));

        // same list with same order -> true.
        assertTrue(clearAddressCommand.equals(new ClearAddressCommand(list)));

        // different order -> false.
        assertFalse(clearAddressCommand.equals(new ClearAddressCommand(Arrays.asList(AMY, CARL, BENSON))));

        // subset of list -> false.
        assertFalse(clearAddressCommand.equals(new ClearAddressCommand(Arrays.asList(AMY, BENSON))));

        // different list -> false.
        assertFalse(clearAddressCommand.equals(new ClearAddressCommand(Arrays.asList(DANIEL, ELLE, GEORGE))));
    }
}
