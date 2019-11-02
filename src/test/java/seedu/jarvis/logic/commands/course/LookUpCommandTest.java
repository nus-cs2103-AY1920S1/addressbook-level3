package seedu.jarvis.logic.commands.course;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.logic.commands.course.LookUpCommand.MESSAGE_SUCCESS;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.course.TypicalCourses.MA1521;

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

public class LookUpCommandTest {

    private Model model;
    private Model expectedModel;
    private LookUpCommand luc;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new CcaTracker(), new HistoryManager(), new FinanceTracker(), new UserPrefs(),
                new Planner(), new CoursePlanner());
        expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(), model.getFinanceTracker(),
                new UserPrefs(), model.getPlanner(), model.getCoursePlanner());
        luc = new LookUpCommand(MA1521);
    }

    /**
     * Verifies that checking {@code LookUpCommand} for the availability of inverse execution returns false.
     */
    @Test
    public void hasInverseExecution() {
        assertFalse(luc.hasInverseExecution());
    }

    /**
     * Verifies that calling inverse execution of {@code LookUpCommand} will always throw a
     * {@code CommandException} with the correct message.
     */
    @Test
    public void inverseExecute_throwsCommandException() {
        assertThrows(CommandException.class,
            LookUpCommand.MESSAGE_NO_INVERSE, () -> luc.executeInverse(model));
    }

    @Test
    public void execute_success() {
        expectedModel.lookUpCourse(MA1521);
        String expectedMessage = String.format(MESSAGE_SUCCESS, MA1521);
        assertCommandSuccess(luc, model, expectedMessage, expectedModel);
    }
}
