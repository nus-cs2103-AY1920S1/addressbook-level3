package seedu.jarvis.storage.history;

import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonSerializableHistoryManagerTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableHistoryManagerStorageTest");
    private static final Path TYPICAL_HISTORY_FILE = TEST_DATA_FOLDER.resolve("typicalHistoryManager.json");

}