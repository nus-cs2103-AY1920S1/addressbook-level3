package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.UserState;
import seedu.address.testutil.TypicalBudgets;
import seedu.address.testutil.TypicalTransactions;

public class JsonSerializableUserStateTest {

    private static final Path TEST_DATA_FOLDER = Paths
        .get("src", "test", "data", "JsonSerializableUserStateTest");
    private static final Path TYPICAL_TRANSACTIONS_FILE = TEST_DATA_FOLDER
        .resolve("typicalTransactionsUserState.json");
    private static final Path INVALID_TRANSACTION_FILE = TEST_DATA_FOLDER
        .resolve("invalidTransactionUserState.json");
    private static final Path DUPLICATE_TRANSACTION_FILE = TEST_DATA_FOLDER
        .resolve("duplicateTransactionUserState.json");

    private static final Path TYPICAL_BUDGETS_FILE = TEST_DATA_FOLDER
        .resolve("typicalBudgetsUserState.json");
    private static final Path INVALID_BUDGET_FILE = TEST_DATA_FOLDER
        .resolve("invalidBudgetUserState.json");

    @Test
    public void toModelType_typicalTransactionsFile_success() throws Exception {
        JsonSerializableUserState dataFromFile = JsonUtil.readJsonFile(TYPICAL_TRANSACTIONS_FILE,
            JsonSerializableUserState.class).get();
        UserState userStateFromFile = dataFromFile.toModelType();
        UserState typicalTransactionsBankAccount = TypicalTransactions.getTypicalUserState();
        assertEquals(userStateFromFile, typicalTransactionsBankAccount);
    }

    @Test
    public void toModelType_invalidTransactionFile_throwsIllegalValueException() throws Exception {
        JsonSerializableUserState dataFromFile = JsonUtil.readJsonFile(INVALID_TRANSACTION_FILE,
            JsonSerializableUserState.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_typicalBudgetsFile_success() throws Exception {
        JsonSerializableUserState dataFromFile = JsonUtil.readJsonFile(TYPICAL_BUDGETS_FILE,
            JsonSerializableUserState.class).get();
        UserState userStateFromFile = dataFromFile.toModelType();
        UserState typicalTransactionsBankAccount = TypicalBudgets.getTypicalUserState();
        assertEquals(userStateFromFile, typicalTransactionsBankAccount);
    }

    @Test
    public void toModelType_invalidBudgetFile_throwsIllegalValueException() throws Exception {
        JsonSerializableUserState dataFromFile = JsonUtil.readJsonFile(INVALID_BUDGET_FILE,
            JsonSerializableUserState.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }
}
