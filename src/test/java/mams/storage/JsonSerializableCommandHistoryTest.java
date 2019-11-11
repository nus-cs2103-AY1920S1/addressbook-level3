package mams.storage;

import static mams.testutil.Assert.assertThrows;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import mams.commons.exceptions.IllegalValueException;
import mams.commons.util.JsonUtil;
import mams.logic.history.CommandHistory;
import mams.testutil.TypicalCommandHistory;

public class JsonSerializableCommandHistoryTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test",
            "data", "JsonSerializableCommandHistoryTest");
    private static final Path TYPICAL_COMMAND_HISTORY_FILE =
            TEST_DATA_FOLDER.resolve("typicalCommandHistory.json");
    private static final Path INVALID_COMMAND_HISTORY_FILE =
            TEST_DATA_FOLDER.resolve("invalidCommandHistory.json");

    @Test
    public void toLogicType_typicalCommandHistoryFile_success() throws Exception {
        JsonSerializableCommandHistory dataFromFile =
                JsonUtil.readJsonFile(TYPICAL_COMMAND_HISTORY_FILE,
                        JsonSerializableCommandHistory.class).get();
        CommandHistory commandHistoryFromFile = dataFromFile.toLogicType();
        CommandHistory typicalCommandHistory = TypicalCommandHistory.getTypicalCommandHistory();
        assertEquals(commandHistoryFromFile, typicalCommandHistory);
    }

    @Test
    public void toLogicType_invalidCommandHistoryFile_throwsIllegalValueException() throws Exception {
        JsonSerializableCommandHistory dataFromFile = JsonUtil.readJsonFile(INVALID_COMMAND_HISTORY_FILE,
                JsonSerializableCommandHistory.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toLogicType);
    }
}
