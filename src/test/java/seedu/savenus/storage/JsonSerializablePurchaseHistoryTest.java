package seedu.savenus.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.commons.util.JsonUtil;
import seedu.savenus.model.PurchaseHistory;
import seedu.savenus.testutil.TypicalPurchaseHistory;

public class JsonSerializablePurchaseHistoryTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializablePurchaseHistoryTest");
    private static final Path VALID_PURCHASEHISTORY_FILE = TEST_DATA_FOLDER.resolve("typicalPurchaseHistory.json");
    private static final Path INVALID_FOOD_FILE = TEST_DATA_FOLDER.resolve("invalidFood.json");
    private static final Path INVALID_TIMEOFPURCHASE_FILE = TEST_DATA_FOLDER.resolve("invalidTimeOfPurchase.json");

    @Test
    public void toModelType_invalidFood_throwsIllegalValueException() throws Exception {
        JsonSerializablePurchaseHistory dataFromFile = JsonUtil.readJsonFile(INVALID_FOOD_FILE,
                JsonSerializablePurchaseHistory.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidTimeOfPurchase_throwsIllegalValueException() throws Exception {
        JsonSerializablePurchaseHistory dataFromFile = JsonUtil.readJsonFile(INVALID_TIMEOFPURCHASE_FILE,
                JsonSerializablePurchaseHistory.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_validPurchaseHistory_success() throws Exception {
        JsonSerializablePurchaseHistory dataFromFile = JsonUtil.readJsonFile(VALID_PURCHASEHISTORY_FILE,
                JsonSerializablePurchaseHistory.class).get();
        PurchaseHistory purchaseHistoryFromFile = dataFromFile.toModelType();
        PurchaseHistory typicalPurchaseHistory = TypicalPurchaseHistory.getTypicalPurchaseHistory();
        assertEquals(purchaseHistoryFromFile, typicalPurchaseHistory);
    }

}
