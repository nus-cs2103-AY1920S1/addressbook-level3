package seedu.jarvis.logic.commands.finance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.address.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.financetracker.FinanceTracker;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.userprefs.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListFinancesCommand.
 */
public class ListFinancesCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new CcaTracker(), new HistoryManager(), new FinanceTracker(),
                getTypicalAddressBook(), new UserPrefs(), new Planner(), new CoursePlanner());
        expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(),
                model.getFinanceTracker(), model.getAddressBook(), new UserPrefs(),
                model.getPlanner(), model.getCoursePlanner());
    }

    /**
     * Verifies that checking {@code ListAddressCommand} for the availability of inverse execution returns false.
     */
    @Test
    public void hasInverseExecution() {
        ListFinancesCommand listFinancesCommand = new ListFinancesCommand();
        assertFalse(listFinancesCommand.hasInverseExecution());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListFinancesCommand(), model,
                ListFinancesCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Verifies that calling inverse execution of {@code ListAddressCommand} will always throw a
     * {@code CommandException} with the correct message.
     */
    @Test
    public void inverseExecute_throwsCommandException() {
        ListFinancesCommand listFinancesCommand = new ListFinancesCommand();
        assertThrows(CommandException.class,
                ListFinancesCommand.MESSAGE_NO_INVERSE, () -> listFinancesCommand.executeInverse(model));
    }
}
