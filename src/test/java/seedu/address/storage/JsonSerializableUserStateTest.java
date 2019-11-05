package seedu.address.storage;

import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonSerializableUserStateTest {

    private static final Path TEST_DATA_FOLDER = Paths
        .get("src", "test", "data", "JsonSerializableUserStateTest");
    private static final Path TYPICAL_TRANSACTIONS_FILE = TEST_DATA_FOLDER
        .resolve("typicalTransactionsUserState.json");
    private static final Path INVALID_TRANSACTION_FILE = TEST_DATA_FOLDER
        .resolve("invalidTransactionUserState.json");
    private static final Path DUPLICATE_TRANSACTION_FILE = TEST_DATA_FOLDER
        .resolve("duplicateTransactionUserState.json");

    //    @Test
    //    public void toModelType_typicalTransactionsFile_success() throws Exception {
    //        JsonSerializableUserState dataFromFile = JsonUtil.readJsonFile(TYPICAL_TRANSACTIONS_FILE,
    //            JsonSerializableUserState.class).get();
    //        UserState userStateFromFile = dataFromFile.toModelType();
    //        UserState typicalTransactionsBankAccount = TypicalTransactions.getTypicalUserState();
    //        assertEquals(userStateFromFile, typicalTransactionsBankAccount);
    //    }
    //
    //    @Test
    //    public void toModelType_invalidTransactionFile_throwsIllegalValueException() throws Exception {
    //        JsonSerializableUserState dataFromFile = JsonUtil.readJsonFile(INVALID_TRANSACTION_FILE,
    //            JsonSerializableUserState.class).get();
    //        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    //    }
    //
    //    @Test
    //    public void toModelType_duplicateTransactions_throwsIllegalValueException() throws Exception {
    //        JsonSerializableUserState dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TRANSACTION_FILE,
    //            JsonSerializableUserState.class).get();
    //        assertThrows(IllegalValueException.class, JsonSerializableUserState.MESSAGE_DUPLICATE_TRANSACTION,
    //            dataFromFile::toModelType);
    //    }

}
