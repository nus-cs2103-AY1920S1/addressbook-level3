package seedu.jarvis.storage.history.commands.planner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.jarvis.testutil.planner.TypicalTasks.TODO;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.planner.DeleteTaskCommand;

/**
 * Tests the behaviour of {@code JsonAdaptedDeleteTaskCommand}.
 */
public class JsonAdaptedDeleteTaskCommandTest {

    @Test
    public void toModelType_validIndexNonNullTask_returnsDeleteTaskCommand() throws Exception {
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST_TASK, TODO);
        JsonAdaptedDeleteTaskCommand jsonAdaptedDeleteTaskCommand = new JsonAdaptedDeleteTaskCommand(deleteTaskCommand);
        assertEquals(deleteTaskCommand, jsonAdaptedDeleteTaskCommand.toModelType());
    }
}
