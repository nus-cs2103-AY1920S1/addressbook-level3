package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ProjectDashboard;
import seedu.address.testutil.TypicalTasks;

public class JsonSerializableProjectDashboardTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableProjectDashboardTest");
    private static final Path TYPICAL_TASKS_FILE = TEST_DATA_FOLDER.resolve("typicalTasksProjectDashboard.json");
    private static final Path INVALID_TASK_FILE = TEST_DATA_FOLDER.resolve("invalidTaskProjectDashboard.json");
    private static final Path DUPLICATE_TASK_FILE = TEST_DATA_FOLDER.resolve("duplicateTaskProjectDashboard.json");

    @Test
    public void toModelType_typicalTasksFile_success() throws Exception {
        JsonSerializableProjectDashboard dataFromFile = JsonUtil.readJsonFile(TYPICAL_TASKS_FILE,
                JsonSerializableProjectDashboard.class).get();
        ProjectDashboard projectDashboardFromFile = dataFromFile.toModelType();
        ProjectDashboard typicalTasksProjectDashboard = TypicalTasks.getTypicalProjectDashboard();
        assertEquals(projectDashboardFromFile, typicalTasksProjectDashboard);
    }

    @Test
    public void toModelType_invalidTaskFile_throwsIllegalValueException() throws Exception {
        JsonSerializableProjectDashboard dataFromFile = JsonUtil.readJsonFile(INVALID_TASK_FILE,
                JsonSerializableProjectDashboard.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateTasks_throwsIllegalValueException() throws Exception {
        JsonSerializableProjectDashboard dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TASK_FILE,
                JsonSerializableProjectDashboard.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableProjectDashboard.MESSAGE_DUPLICATE_TASKS,
                dataFromFile::toModelType);
    }

}
