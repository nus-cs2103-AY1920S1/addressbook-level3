package seedu.jarvis.logic.commands.address;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.jarvis.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.financetracker.FinanceTracker;
import seedu.jarvis.model.userprefs.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListAddressCommand.
 */
public class ListAddressCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new HistoryManager(), new FinanceTracker(), getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getHistoryManager(), model.getFinanceTracker(), model.getAddressBook(),
                new UserPrefs());
    }

    /**
     * Verifies that checking {@code ListAddressCommand} for the availability of inverse execution returns false.
     */
    @Test
    public void hasInverseExecution() {
        ListAddressCommand listAddressCommand = new ListAddressCommand();
        assertFalse(listAddressCommand.hasInverseExecution());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListAddressCommand(), model,
                ListAddressCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListAddressCommand(), model,
                ListAddressCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Verifies that calling inverse execution of {@code ListAddressCommand} will always throw a
     * {@code CommandException} with the correct message.
     */
    @Test
    public void inverseExecute_throwsCommandException() {
        ListAddressCommand listAddressCommand = new ListAddressCommand();
        assertThrows(CommandException.class,
                ListAddressCommand.MESSAGE_NO_INVERSE, () -> listAddressCommand.executeInverse(model));
    }
}
