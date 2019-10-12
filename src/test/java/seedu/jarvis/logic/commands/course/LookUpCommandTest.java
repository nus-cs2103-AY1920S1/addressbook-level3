package seedu.jarvis.logic.commands.course;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.jarvis.logic.commands.course.LookUpCommand;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.userprefs.UserPrefs;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.jarvis.testutil.course.CourseTestUtil.CourseStub;

public class LookUpCommandTest {

    private Model model;
    private Model expectedModel;
    private LookUpCommand luc;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new HistoryManager(), getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getHistoryManager(), model.getAddressBook(), new UserPrefs());
        luc = new LookUpCommand(new CourseStub("somecourse"));
    }

    /**
     * Verifies that checking {@code ListAddressCommand} for the availability of inverse execution returns false.
     */
    @Test
    public void hasInverseExecution() {
        LookUpCommand luc = new LookUpCommand(new CourseStub("aaa"));
        assertFalse(luc.hasInverseExecution());
    }

    /**
     * Verifies that calling inverse execution of {@code ListAddressCommand} will always throw a
     * {@code CommandException} with the correct message.
     */
    @Test
    public void inverseExecute_throwsCommandException() {
        LookUpCommand luc = new LookUpCommand(new CourseStub("aaa"));
        assertThrows(CommandException.class,
            LookUpCommand.MESSAGE_NO_INVERSE, () -> luc.executeInverse(model));
    }
}
