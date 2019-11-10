package seedu.address.storage.cap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.cap.CapLog;
import seedu.address.testutil.TypicalModule;

public class JsonSerializableCapLogTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableCapLogTest");
    private static final Path TYPICAL_MODULES_FILE = TEST_DATA_FOLDER.resolve("typicalModulesCapLog.json");
    private static final Path INVALID_MODULE_FILE = TEST_DATA_FOLDER.resolve("invalidModuleCapLog.json");
    private static final Path DUPLICATE_MODULE_FILE = TEST_DATA_FOLDER.resolve("duplicateModuleCapLog.json");

    @Test
    public void toModelType_typicalModulesFile_success() throws Exception {
        JsonSerializableCapLog dataFromFile = JsonUtil.readJsonFile(TYPICAL_MODULES_FILE,
                JsonSerializableCapLog.class).get();
        CapLog addressBookFromFile = dataFromFile.toModelType();
        CapLog typicalModulesCapLog = TypicalModule.getTypicalCapLog();
        assertEquals(addressBookFromFile, typicalModulesCapLog);
    }

    @Test
    public void toModelType_invalidModuleFile_throwsIllegalValueException() throws Exception {
        JsonSerializableCapLog dataFromFile = JsonUtil.readJsonFile(INVALID_MODULE_FILE,
                JsonSerializableCapLog.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateModules_throwsIllegalValueException() throws Exception {
        JsonSerializableCapLog dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MODULE_FILE,
                JsonSerializableCapLog.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableCapLog.MESSAGE_DUPLICATE_MODULE,
                dataFromFile::toModelType);
    }

}
