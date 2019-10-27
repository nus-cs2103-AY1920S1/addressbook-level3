package seedu.jarvis.storage.cca;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.util.JsonUtil;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.testutil.cca.TypicalCcas;

/**
 * Tests the behaviour of {@code JsonSerializableCcaTracker}.
 */
public class JsonSerializableCcaTrackerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableCcaTrackerTest");
    private static final Path TYPICAL_CCAS_FILE = TEST_DATA_FOLDER.resolve("typicalCcaTracker.json");

    @Test
    public void toModelType_typicalCcasFile_success() throws Exception {
        JsonSerializableCcaTracker dataFromFile = JsonUtil.readJsonFile(TYPICAL_CCAS_FILE,
                JsonSerializableCcaTracker.class).get();
        CcaTracker ccaTrackerFromFile = dataFromFile.toModelType();
        CcaTracker typicalCcaTracker = TypicalCcas.getTypicalCcaTracker();
        assertEquals(ccaTrackerFromFile, typicalCcaTracker);
    }

}
