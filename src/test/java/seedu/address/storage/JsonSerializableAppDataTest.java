package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AppData;
import seedu.address.testutil.TypicalAppData;

public class JsonSerializableAppDataTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAppDataTest");
    private static final Path TYPICAL_APPDATA_FILE = TEST_DATA_FOLDER.resolve("typicalAppData.json");
    private static final Path INVALID_APPDATA_FILE = TEST_DATA_FOLDER.resolve("invalidAppData.json");
    private static final Path DUPLICATE_APPDATA_FILE = TEST_DATA_FOLDER.resolve("duplicateAppData.json");

    @Test
    public void toModelType_typicalAppDataFile_success() throws Exception {
        JsonSerializableAppData dataFromFile = JsonUtil.readJsonFile(TYPICAL_APPDATA_FILE,
                JsonSerializableAppData.class).get();
        AppData appDataFromFile = dataFromFile.toModelType();
        AppData typicalAppData = TypicalAppData.getTypicalAppData();
        assertEquals(appDataFromFile, typicalAppData);
    }

    @Test
    public void toModelType_invalidAppDataFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAppData dataFromFile = JsonUtil.readJsonFile(INVALID_APPDATA_FILE,
                JsonSerializableAppData.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateAppData_throwsIllegalValueException() throws Exception {
        JsonSerializableAppData dataFromFile = JsonUtil.readJsonFile(DUPLICATE_APPDATA_FILE,
                JsonSerializableAppData.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAppData.MESSAGE_DUPLICATE_TITLE,
                dataFromFile::toModelType);
    }

}
