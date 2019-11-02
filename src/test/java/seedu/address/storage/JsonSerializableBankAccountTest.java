package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.UserState;
import seedu.address.testutil.TypicalTransactions;

public class JsonSerializableBankAccountTest {

    private static final Path TEST_DATA_FOLDER = Paths
        .get("src", "test", "data", "JsonSerializableUserStateTest");
    private static final Path TYPICAL_TRANSACTIONS_FILE = TEST_DATA_FOLDER
        .resolve("typicalTransactionsUserState.json");
    private static final Path INVALID_TRANSACTION_FILE = TEST_DATA_FOLDER
        .resolve("invalidTransactionUserState.json");
    private static final Path DUPLICATE_TRANSACTION_FILE = TEST_DATA_FOLDER
        .resolve("duplicateTransactionUserState.json");

    @Test
    public void toModelType_typicalTransactionsFile_success() throws Exception {
        JsonSerializableBankAccount dataFromFile = JsonUtil.readJsonFile(TYPICAL_TRANSACTIONS_FILE,
            JsonSerializableBankAccount.class).get();
        UserState bankAccountFromFile = dataFromFile.toModelType();
        UserState typicalTransactionsBankAccount = TypicalTransactions.getTypicalUserState();
        assertEquals(bankAccountFromFile, typicalTransactionsBankAccount);
    }

    @Test
    public void toModelType_invalidTransactionFile_throwsIllegalValueException() throws Exception {
        JsonSerializableBankAccount dataFromFile = JsonUtil.readJsonFile(INVALID_TRANSACTION_FILE,
            JsonSerializableBankAccount.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateTransactions_throwsIllegalValueException() throws Exception {
        JsonSerializableBankAccount dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TRANSACTION_FILE,
            JsonSerializableBankAccount.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableBankAccount.MESSAGE_DUPLICATE_TRANSACTION,
            dataFromFile::toModelType);
    }

}
