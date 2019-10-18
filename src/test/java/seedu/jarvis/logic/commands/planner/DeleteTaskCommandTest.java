package seedu.jarvis.logic.commands.planner;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeleteTaskCommandTest {

    //TODO
    @Test
    void getCommandWord() throws ParseException {
        DeleteTaskCommand command = new DeleteTaskCommand(ParserUtil.parseIndex("2"));
        String actual_command = command.getCommandWord();

        assertEquals("deleteTask", actual_command);
    }

    @Test
    void getTargetIndex() throws ParseException {
        DeleteTaskCommand command = new DeleteTaskCommand(ParserUtil.parseIndex("2"));
        assertEquals(ParserUtil.parseIndex("2"), command.getTargetIndex());

    }

    @Test
    void hasInverseExecution() throws ParseException {
        DeleteTaskCommand command = new DeleteTaskCommand(ParserUtil.parseIndex("2"));
        assertFalse(command.hasInverseExecution());
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
    void executeInverse() throws ParseException {
        Model planner = new ModelManager();
        DeleteTaskCommand command = new DeleteTaskCommand(ParserUtil.parseIndex("1"));
        assertDoesNotThrow(() -> command.executeInverse(planner));
    }
}