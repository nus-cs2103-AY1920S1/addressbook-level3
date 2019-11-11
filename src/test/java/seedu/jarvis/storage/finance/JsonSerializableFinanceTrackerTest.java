package seedu.jarvis.storage.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.util.JsonUtil;
import seedu.jarvis.model.finance.FinanceTracker;
import seedu.jarvis.testutil.finance.TypicalFinances;

/**
 * Tests the behaviour of {@code JsonSerializableFinanceTracker}.
 */
public class JsonSerializableFinanceTrackerTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableFinanceTrackerTest");
    private static final Path TYPICAL_FINANCES_FILE = TEST_DATA_FOLDER.resolve("typicalFinanceTracker.json");

    @Test
    public void toModelType_typicalFinancesFile_success() throws Exception {
        JsonSerializableFinanceTracker dataFromFile = JsonUtil.readJsonFile(TYPICAL_FINANCES_FILE,
                JsonSerializableFinanceTracker.class).get();
        FinanceTracker financeTrackerFromFile = dataFromFile.toModelType();
        FinanceTracker typicalFinanceTracker = TypicalFinances.getTypicalFinanceTracker();
        assertEquals(financeTrackerFromFile, typicalFinanceTracker);
    }
}
