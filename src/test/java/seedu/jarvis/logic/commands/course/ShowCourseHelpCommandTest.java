package seedu.jarvis.logic.commands.course;

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
import seedu.jarvis.model.finance.FinanceTracker;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.userprefs.UserPrefs;

public class ShowCourseHelpCommandTest {

    private Model model;
    private Model expectedModel;
    private ShowCourseHelpCommand showCourseHelpCommand;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new CcaTracker(), new HistoryManager(), new FinanceTracker(), getTypicalAddressBook(),
            new UserPrefs(), new Planner(), new CoursePlanner());
        expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(), model.getFinanceTracker(),
            model.getAddressBook(), new UserPrefs(), model.getPlanner(), model.getCoursePlanner());
        showCourseHelpCommand = new ShowCourseHelpCommand();
    }

    /**
     * Verifies that checking {@code CheckCommand} for the availability of inverse execution returns false.
     */
    @Test
    public void hasInverseExecution() {
        assertFalse(showCourseHelpCommand.hasInverseExecution());
    }

    /**
     * Verifies that calling inverse execution of {@code CheckCommand} will always throw a
     * {@code CommandException} with the correct message.
     */
    @Test
    public void inverseExecute_throwsCommandException() {
        assertThrows(CommandException.class,
            ShowCourseHelpCommand.MESSAGE_NO_INVERSE, () -> showCourseHelpCommand.executeInverse(model));
    }

    @Test
    public void execute_getsCorrectModel() {
        expectedModel.checkCourse(ShowCourseHelpCommand.MESSAGE_HELP);
        String expectedResult = ShowCourseHelpCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(showCourseHelpCommand, model, expectedResult, expectedModel);
    }
}
