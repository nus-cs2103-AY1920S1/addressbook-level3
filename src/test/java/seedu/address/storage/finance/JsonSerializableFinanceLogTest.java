package seedu.address.storage.finance;

import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.logic.finance.commands.BudgetCommand;

public class JsonSerializableFinanceLogTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableFinanceLogTest");
    private static final Path INVALID_LOG_ENTRY_FILE = TEST_DATA_FOLDER.resolve("invalidLogEntryFinanceLog.json");
    private static final Path DUPLICATE_BUDGET_FILE = TEST_DATA_FOLDER.resolve("duplicateBudgetFinanceLog.json");

    @Test
    public void toModelType_invalidLogEntryFile_throwsIllegalValueException() throws Exception {
        JsonSerializableFinanceLog dataFromFile = JsonUtil.readJsonFile(INVALID_LOG_ENTRY_FILE,
                JsonSerializableFinanceLog.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateBudgets_throwsIllegalValueException() throws Exception {
        JsonSerializableFinanceLog dataFromFile = JsonUtil.readJsonFile(DUPLICATE_BUDGET_FILE,
                JsonSerializableFinanceLog.class).get();
        assertThrows(IllegalValueException.class, BudgetCommand.MESSAGE_DUPLICATE_BUDGET,
                dataFromFile::toModelType);
    }

}
