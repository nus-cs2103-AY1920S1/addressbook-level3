package seedu.jarvis.storage.planner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.util.JsonUtil;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.testutil.planner.TypicalTasks;

/**
 * Tests the behaviour of {@code JsonSerializablePlanner}.
 */
public class JsonSerializablePlannerTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializablePlannerTest");
    private static final Path TYPICAL_TASKS_FILE = TEST_DATA_FOLDER.resolve("typicalPlanner.json");

    @Test
    public void toModelType_typicalTasksFile_success() throws Exception {
        JsonSerializablePlanner dataFromFile = JsonUtil.readJsonFile(TYPICAL_TASKS_FILE,
                JsonSerializablePlanner.class).get();
        Planner plannerFromFile = dataFromFile.toModelType();
        Planner typicalPlanner = TypicalTasks.getTypicalPlanner();
        assertEquals(plannerFromFile, typicalPlanner);

    }
}
