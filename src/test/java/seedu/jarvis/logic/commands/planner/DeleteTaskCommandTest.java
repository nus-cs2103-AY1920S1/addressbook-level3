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

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
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
    void executeInverse_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Model expected = new ModelManager();
        Task toDelete = new Todo("borrow book");

        model.addTask(toDelete);
        expected.addTask(toDelete);

        DeleteTaskCommand command = new DeleteTaskCommand(ParserUtil.parseIndex("1"), toDelete);
        command.execute(model);

        command.executeInverse(model);

        assertEquals(expected, model);

    }

    @Test
    void testEquals_success() throws ParseException {
        DeleteTaskCommand commandOne = new DeleteTaskCommand(ParserUtil.parseIndex("1"));
        DeleteTaskCommand commandTwo = new DeleteTaskCommand(ParserUtil.parseIndex("1"));

        assertEquals(commandOne, commandTwo);


    }
}
