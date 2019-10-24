package seedu.jarvis.logic.commands.course;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.address.TypicalPersons.getTypicalAddressBook;
import static seedu.jarvis.testutil.course.TypicalCourses.MA1521;

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

public class CheckCommandTest {

    private Model model;
    private Model expectedModel;
    private CheckCommand luc;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new CcaTracker(), new HistoryManager(), new FinanceTracker(), getTypicalAddressBook(),
            new UserPrefs(), new Planner(), new CoursePlanner());
        expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(), model.getFinanceTracker(),
            model.getAddressBook(), new UserPrefs(), model.getPlanner(), model.getCoursePlanner());
        luc = new CheckCommand(MA1521);
    }

    /**
     * Verifies that checking {@code CheckCommand} for the availability of inverse execution returns false.
     */
    @Test
    public void hasInverseExecution() {
        CheckCommand luc = new CheckCommand(MA1521);
        assertFalse(luc.hasInverseExecution());
    }

    /**
     * Verifies that calling inverse execution of {@code CheckCommand} will always throw a
     * {@code CommandException} with the correct message.
     */
    @Test
    public void inverseExecute_throwsCommandException() {
        CheckCommand luc = new CheckCommand(MA1521);
        assertThrows(CommandException.class,
            CheckCommand.MESSAGE_NO_INVERSE, () -> luc.executeInverse(model));
    }
}
