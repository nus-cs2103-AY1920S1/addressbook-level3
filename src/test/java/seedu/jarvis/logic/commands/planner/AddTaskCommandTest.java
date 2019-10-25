package seedu.jarvis.logic.commands.planner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;



class AddTaskCommandTest {

    @Test
    void hasInverseExecution_success() {
        Task t = new Todo("borrow book");
        AddTaskCommand command = new AddTaskCommand(t);
        assertTrue(command.hasInverseExecution());
    }

    @Test
    void execute_validInput_success() {
        Model planner = new ModelManager();
        Task t = new Todo("borrow book");
        AddTaskCommand command = new AddTaskCommand(t);
        assertDoesNotThrow(() -> command.execute(planner));
    }

    @Test
    void execute_duplicateInput_throwsException() {
        Model planner = new ModelManager();
        Task t = new Todo("borrow book");
        planner.addTask(t);
        AddTaskCommand command = new AddTaskCommand(t);
        assertThrows(CommandException.class, () -> command.execute(planner));
    }

    @Test
    void executeInverse() throws CommandException {
        Model planner = new ModelManager();
        Task t = new Todo("borrow and read book");
        AddTaskCommand command = new AddTaskCommand(t);
        command.execute(planner);
        CommandResult actualMessage = command.executeInverse(planner);
        CommandResult expectedMessage = new CommandResult(String.format(
                                                AddTaskCommand.MESSAGE_INVERSE_SUCCESS_DELETE, t));
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void getCommandWord() {
        Task t = new Todo("borrow book");
        AddTaskCommand command = new AddTaskCommand(t);
        String actualCommand = command.getCommandWord();

        assertEquals("add-task", actualCommand);

    }
}
