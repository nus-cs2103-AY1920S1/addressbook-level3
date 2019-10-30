package seedu.jarvis.logic.commands.course;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.address.AddressBook;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.finance.FinanceTracker;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.userprefs.UserPrefs;

public class ListCourseCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new CcaTracker(), new HistoryManager(), new FinanceTracker(),
            new AddressBook(), new UserPrefs(), new Planner(), new CoursePlanner());
        expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(),
            model.getFinanceTracker(), model.getAddressBook(), new UserPrefs(),
            model.getPlanner(), model.getCoursePlanner());
    }

    /**
     * Verifies that checking {@code ListAddressCommand} for the availability of inverse execution returns false.
     */
    @Test
    public void hasInverseExecution() {
        ListCourseCommand listCourseCommand = new ListCourseCommand();
        assertFalse(listCourseCommand.hasInverseExecution());
    }

    /**
     * Verifies that calling inverse execution of {@code ListCourseCommand} will always throw a
     * {@code CommandException} with the correct message.
     */
    @Test
    public void inverseExecute_throwsCommandException() {
        ListCourseCommand listCourseCommand = new ListCourseCommand();
        assertThrows(CommandException.class,
            ListCourseCommand.MESSAGE_NO_INVERSE, () -> listCourseCommand.executeInverse(model));
    }
}
