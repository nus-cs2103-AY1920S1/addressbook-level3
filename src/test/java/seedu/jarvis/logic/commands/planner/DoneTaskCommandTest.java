package seedu.jarvis.logic.commands.planner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.planner.enums.Status;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;

class DoneTaskCommandTest {

    @Test
    void getCommandWord() {
        DoneTaskCommand command = new DoneTaskCommand(parseIndex("2"));
        String actual = command.getCommandWord();
        String expected = "done-task";

        assertEquals(expected, actual);
    }

    @Test
    void hasInverseExecution() {
        DoneTaskCommand command = new DoneTaskCommand(parseIndex("2"));
        assertTrue(command.hasInverseExecution());
    }

    @Test
    void execute_validIndex_success() {
        Model planner = new ModelManager();
        Task t = new Todo("borrow book");
        planner.addTask(t);

        DoneTaskCommand command = new DoneTaskCommand(parseIndex("1"));

        assertDoesNotThrow(() -> command.execute(planner));
        Task newT = planner.getTask(parseIndex("1"));
        assertEquals(newT.getStatus(), Status.DONE);

    }

    @Test
    void execute_invalidIndex_throwsException() {
        Model planner = new ModelManager();
        Task t = new Todo("borrow book");
        planner.addTask(t);

        DoneTaskCommand command = new DoneTaskCommand(parseIndex("2"));
        assertThrows(CommandException.class, () -> command.execute(planner));
    }

    @Test
    void execute_taskAlreadyDone_throwsException() {
        Model planner = new ModelManager();
        Task t = new Todo("borrow book");
        t.markAsDone();
        planner.addTask(t);

        DoneTaskCommand command = new DoneTaskCommand(parseIndex("1"));
        assertThrows(CommandException.class, () -> command.execute(planner));
    }

    @Test
    void executeInverse_success() throws CommandException {
        Model model = new ModelManager();
        Model expected = new ModelManager();
        Task toUndo = new Todo("borrow book");
        model.addTask(toUndo);
        expected.addTask(toUndo);

        DoneTaskCommand command = new DoneTaskCommand(parseIndex("1"));
        command.execute(model);

        command.executeInverse(model);

        assertEquals(expected, model);
    }

    @Test
    void equals_true() {
        DoneTaskCommand one = new DoneTaskCommand(parseIndex("1"));
        DoneTaskCommand two = new DoneTaskCommand(parseIndex("1"));

        assertEquals(one, two);
    }

    @Test
    void equals_differentIndex_false() {
        DoneTaskCommand one = new DoneTaskCommand(parseIndex("1"));
        DoneTaskCommand two = new DoneTaskCommand(parseIndex("2"));

        assertNotEquals(one, two);
    }

    /**
     * Creates a new {@code Index} to be used for test cases
     * @param index {@code String} index to be parsed into {@code Index} object
     * @return one-based {@code Index} object to be used for {@code DoneTaskCommand}
     */
    private Index parseIndex(String index) {
        Index i = null;
        try {
            i = ParserUtil.parseIndex(index);
        } catch (ParseException p) {
            p.getMessage();
        }
        return i;
    }
}
