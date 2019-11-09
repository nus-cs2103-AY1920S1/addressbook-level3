package seedu.ifridge.storage.wastelist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ifridge.testutil.Assert.assertThrows;
import static seedu.ifridge.testutil.TypicalWasteArchive.getTypicalWasteArchive;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.ifridge.commons.exceptions.DataConversionException;
import seedu.ifridge.commons.exceptions.IllegalValueException;
import seedu.ifridge.commons.util.JsonUtil;
import seedu.ifridge.model.WasteList;
import seedu.ifridge.model.waste.WasteMonth;

public class JsonSerializableWasteArchiveTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableWasteArchiveTest");
    private static final Path TYPICAL_WASTE_ARCHIVE_FILE = TEST_DATA_FOLDER
            .resolve("typicalWasteArchive.json");
    private static final Path INVALID_WASTE_MONTH_FILE = TEST_DATA_FOLDER
            .resolve("invalidWasteMonth.json");
    private static final Path DUPLICATE_WASTE_MONTH_FILE = TEST_DATA_FOLDER
            .resolve("duplicateWasteMonth.json");

    /**
     * Sets up the valid file. This is necessary due to the temporal aspect of the typical waste archive.
     */
    @BeforeEach
    public void setUp() throws IOException {
        TreeMap<WasteMonth, WasteList> original = getTypicalWasteArchive();
        JsonWasteListStorage jsonWasteListStorage = new JsonWasteListStorage(TYPICAL_WASTE_ARCHIVE_FILE);
        jsonWasteListStorage.saveWasteList(original);
    }

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableWasteArchive dataFromFile = JsonUtil.readJsonFile(TYPICAL_WASTE_ARCHIVE_FILE,
                JsonSerializableWasteArchive.class).get();
        TreeMap<WasteMonth, WasteList> wasteArchiveFromFile = dataFromFile.toModelType();
        TreeMap<WasteMonth, WasteList> typicalWasteArchive = getTypicalWasteArchive();
        assertEquals(wasteArchiveFromFile, typicalWasteArchive);
    }

    @Test
    public void toModelType_invalidWasteMonth_throwsIllegalValueException() throws DataConversionException {
        JsonSerializableWasteArchive dataFromFile = JsonUtil.readJsonFile(INVALID_WASTE_MONTH_FILE,
                JsonSerializableWasteArchive.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateWasteMonth_throwsIllegalValueException() throws DataConversionException {
        JsonSerializableWasteArchive dataFromFile = JsonUtil.readJsonFile(DUPLICATE_WASTE_MONTH_FILE,
                JsonSerializableWasteArchive.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }
}
