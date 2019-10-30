package seedu.jarvis.storage.history.commands.planner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.planner.TypicalTasks.TODO;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.planner.AddTaskCommand;

/**
 * Tests the behaviour of {@code JsonAdaptedAddTaskCommand}.
 */
public class JsonAdaptedAddTaskCommandTest {

    @Test
    public void toModelType_addTask_returnsAddTaskCommand() throws Exception {
        AddTaskCommand addTaskCommand = new AddTaskCommand(TODO);
        JsonAdaptedAddTaskCommand jsonAdaptedAddTaskCommand = new JsonAdaptedAddTaskCommand(addTaskCommand);
        assertEquals(addTaskCommand, jsonAdaptedAddTaskCommand.toModelType());
    }
}
