package seedu.jarvis.storage.history;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.util.JsonUtil;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.testutil.history.TypicalCommands;

public class JsonSerializableHistoryManagerTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableHistoryManagerStorageTest");
    private static final Path TYPICAL_HISTORY_FILE = TEST_DATA_FOLDER.resolve("typicalHistoryManager.json");

    @Test
    public void toModelType_typicalHistoryManagerFile_success() throws Exception {
        JsonSerializableHistoryManager dataFromFile = JsonUtil.readJsonFile(TYPICAL_HISTORY_FILE,
                JsonSerializableHistoryManager.class).get();
        HistoryManager historyManagerFromFile = dataFromFile.toModelType();
        HistoryManager typicalHistoryManager = TypicalCommands.getTypicalHistoryManager();
        assertEquals(historyManagerFromFile, typicalHistoryManager);
    }
}