package seedu.jarvis.logic.commands.cca;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.finance.FinanceTracker;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.userprefs.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCcaCommand.
 */
public class ListCcaCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new CcaTracker(), new HistoryManager(), new FinanceTracker(),
                new UserPrefs(), new Planner(), new CoursePlanner());
        expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(),
                model.getFinanceTracker(), new UserPrefs(), model.getPlanner(), model.getCoursePlanner());
    }

    /**
     * Verifies that checking {@code ListCcaCommand} for the availability of inverse execution returns false.
     */
    @Test
    public void hasInverseExecution() {
        ListCcaCommand listCcaCommand = new ListCcaCommand();
        assertFalse(listCcaCommand.hasInverseExecution());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCcaCommand(), model,
                ListCcaCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Verifies that calling inverse execution of {@code ListCcaCommand} will always throw a
     * {@code CommandException} with the correct message.
     */
    @Test
    public void inverseExecute_throwsCommandException() {
        ListCcaCommand listCcaCommand = new ListCcaCommand();
        assertThrows(CommandException.class,
                ListCcaCommand.MESSAGE_NO_INVERSE, () -> listCcaCommand.executeInverse(model));
    }
}
