package seedu.jarvis.logic.commands.planner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandInverseSuccess;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.address.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.financetracker.FinanceTracker;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;
import seedu.jarvis.model.userprefs.UserPrefs;

class DeleteTaskCommandTest {

    @Test
    void getCommandWord() throws ParseException {
        DeleteTaskCommand command = new DeleteTaskCommand(ParserUtil.parseIndex("2"));
        String actualCommand = command.getCommandWord();

        assertEquals("deleteTask", actualCommand);
    }

    @Test
    void getTargetIndex() throws ParseException {
        DeleteTaskCommand command = new DeleteTaskCommand(ParserUtil.parseIndex("2"));
        assertEquals(ParserUtil.parseIndex("2"), command.getTargetIndex());

    }

    @Test
    void hasInverseExecution() throws ParseException {
        DeleteTaskCommand command = new DeleteTaskCommand(ParserUtil.parseIndex("2"));
        assertTrue(command.hasInverseExecution());
    }

    @Test
    void execute_validIndex_success() throws ParseException {
        Model planner = new ModelManager();
        Task t = new Todo("borrow book");
        planner.addTask(t);
        DeleteTaskCommand command = new DeleteTaskCommand(ParserUtil.parseIndex(("1")));
        assertDoesNotThrow(() -> command.execute(planner));
    }

    @Test
    void execute_invalidIndex_success() throws ParseException {
        Model planner = new ModelManager();
        DeleteTaskCommand command = new DeleteTaskCommand(ParserUtil.parseIndex(("1")));
        assertThrows(CommandException.class, () -> command.execute(planner));
    }

    @Test
    void executeInverse_success() throws ParseException {
        Model model = new ModelManager(new CcaTracker(), new HistoryManager(), new FinanceTracker(), getTypicalAddressBook(),
                new UserPrefs(), new Planner(), new CoursePlanner());
        DeleteTaskCommand command = new DeleteTaskCommand(ParserUtil.parseIndex("1"));
        Task toDelete = new Todo("borrow book");

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, toDelete);

        Model expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(),
                model.getFinanceTracker(), model.getAddressBook(), new UserPrefs(),
                model.getPlanner(), model.getCoursePlanner());
        expectedModel.deleteTask(toDelete);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        String inverseExpectedMessage = String.format(DeleteTaskCommand.MESSAGE_INVERSE_SUCCESS_ADD, toDelete);

        expectedModel.addTask(toDelete);
        assertCommandInverseSuccess(command, model, inverseExpectedMessage, expectedModel);
    }

    @Test
    void testEquals_success() throws ParseException {
        DeleteTaskCommand commandOne = new DeleteTaskCommand(ParserUtil.parseIndex("1"));
        DeleteTaskCommand commandTwo = new DeleteTaskCommand(ParserUtil.parseIndex("1"));

        assertEquals(commandOne, commandTwo);


    }
}
