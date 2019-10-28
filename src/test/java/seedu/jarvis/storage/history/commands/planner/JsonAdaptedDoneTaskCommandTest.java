package seedu.jarvis.storage.history.commands.planner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.jarvis.testutil.planner.TypicalTasks.TODO;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.planner.DoneTaskCommand;

/**
 * Tests the behaviour of {@code JsonAdaptedDoneTaskCommand}.
 */
public class JsonAdaptedDoneTaskCommandTest {

    @Test
    public void toModelType_doTaskNonNullTask_returnsDoneTaskCommand() throws Exception {
        DoneTaskCommand doneTaskCommand = new DoneTaskCommand(INDEX_FIRST_TASK, TODO);
        JsonAdaptedDoneTaskCommand jsonAdaptedDoneTaskCommand = new JsonAdaptedDoneTaskCommand(doneTaskCommand);
        assertEquals(doneTaskCommand, jsonAdaptedDoneTaskCommand.toModelType());
    }
}
