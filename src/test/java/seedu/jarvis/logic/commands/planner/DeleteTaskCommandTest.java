package seedu.jarvis.logic.commands.planner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;

class DeleteTaskCommandTest {

    @Test
    void getCommandWord() throws ParseException {
        DeleteTaskCommand command = new DeleteTaskCommand(parseIndex("2"));
        String actualCommand = command.getCommandWord();

        assertEquals("delete-task", actualCommand);
    }

    @Test
    void getTargetIndex() throws ParseException {
        DeleteTaskCommand command = new DeleteTaskCommand(parseIndex("2"));
        assertEquals(parseIndex("2"), command.getTargetIndex());

    }

    @Test
    void hasInverseExecution() throws ParseException {
        DeleteTaskCommand command = new DeleteTaskCommand(parseIndex("2"));
        assertTrue(command.hasInverseExecution());
    }

    @Test
    void execute_validIndex_success() throws ParseException {
        Model planner = new ModelManager();
        Task t = new Todo("borrow book");
        planner.addTask(t);
        DeleteTaskCommand command = new DeleteTaskCommand(parseIndex(("1")));
        assertDoesNotThrow(() -> command.execute(planner));
    }

    @Test
    void execute_invalidIndex_throwsException() throws ParseException {
        Model planner = new ModelManager();
        DeleteTaskCommand command = new DeleteTaskCommand(parseIndex(("1")));
        assertThrows(CommandException.class, () -> command.execute(planner));
    }

    @Test
    void executeInverse_success() throws CommandException, ParseException {
        Model model = new ModelManager();
        Model expected = new ModelManager();
        Task toDelete = new Todo("borrow book");

        model.addTask(toDelete);
        expected.addTask(toDelete);

        DeleteTaskCommand command = new DeleteTaskCommand(parseIndex("1"), toDelete);
        command.execute(model);

        command.executeInverse(model);

        assertEquals(expected, model);

    }

    @Test
    void testEquals_success() throws ParseException {
        DeleteTaskCommand commandOne = new DeleteTaskCommand(parseIndex("1"));
        DeleteTaskCommand commandTwo = new DeleteTaskCommand(parseIndex("1"));

        assertEquals(commandOne, commandTwo);


    }

    private Index parseIndex(String index) throws ParseException {
        return ParserUtil.parseIndex(index);
    }
}
