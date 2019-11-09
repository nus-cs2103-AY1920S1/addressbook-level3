package seedu.savenus.storage.wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.commons.util.JsonUtil;
import seedu.savenus.model.wallet.Wallet;
import seedu.savenus.testutil.TypicalWallet;

//@@author raikonen
public class JsonSerializableWalletTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableWalletTest");
    private static final Path VALID_WALLET_FILE = TEST_DATA_FOLDER.resolve("typicalWallet.json");
    private static final Path INVALID_REMAININGBUDGET_FILE = TEST_DATA_FOLDER.resolve("invalidRemainingBudget.json");
    private static final Path INVALID_DAYSTOEXPIRE_FILE = TEST_DATA_FOLDER.resolve("invalidDaysToExpire.json");

    @Test
    public void toModelType_invalidRemainingBudget_throwsIllegalValueException() throws Exception {
        JsonSerializableWallet dataFromFile = JsonUtil.readJsonFile(INVALID_REMAININGBUDGET_FILE,
                JsonSerializableWallet.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidDaysToExpire_throwsIllegalValueException() throws Exception {
        JsonSerializableWallet dataFromFile = JsonUtil.readJsonFile(INVALID_DAYSTOEXPIRE_FILE,
                JsonSerializableWallet.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_validWallet_success() throws Exception {
        JsonSerializableWallet dataFromFile = JsonUtil.readJsonFile(VALID_WALLET_FILE,
                JsonSerializableWallet.class).get();
        Wallet walletFromFile = dataFromFile.toModelType();
        Wallet typicalWallet = TypicalWallet.getTypicalWallet();
        assertEquals(walletFromFile, typicalWallet);
    }

}
