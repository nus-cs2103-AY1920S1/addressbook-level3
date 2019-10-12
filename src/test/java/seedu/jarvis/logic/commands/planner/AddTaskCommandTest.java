package seedu.jarvis.logic.commands.planner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.planner.Task;
import seedu.jarvis.model.planner.Todo;



class AddTaskCommandTest {

    @Test
    void hasInverseExecution_success() {
        Task t = new Todo("borrow book");
        AddTaskCommand command = new AddTaskCommand(t);
        assertFalse(command.hasInverseExecution());
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
    void executeInverse() {
        Model planner = new ModelManager();
        Task t = new Todo("borrow book");
        AddTaskCommand command = new AddTaskCommand(t);
        assertDoesNotThrow(() -> command.executeInverse(planner));
    }
}
