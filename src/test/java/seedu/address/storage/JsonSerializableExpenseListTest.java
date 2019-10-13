package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ExpenseList;
import seedu.address.testutil.TypicalExpenses;

public class JsonSerializableExpenseListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableExpenseListTest");
    private static final Path TYPICAL_EXPENSES_FILE = TEST_DATA_FOLDER.resolve("typicalExpensesExpenseList.json");
    private static final Path INVALID_EXPENSE_FILE = TEST_DATA_FOLDER.resolve("invalidExpenseExpenseList.json");
    private static final Path DUPLICATE_EXPENSE_FILE = TEST_DATA_FOLDER.resolve("duplicateExpenseExpenseList.json");

    @Test
    public void toModelType_typicalExpensesFile_success() throws Exception {
        JsonSerializableExpenseList dataFromFile = JsonUtil.readJsonFile(TYPICAL_EXPENSES_FILE,
                JsonSerializableExpenseList.class).get();
        ExpenseList expenseListFromFile = dataFromFile.toModelType();
        ExpenseList typicalExpensesExpenseList = TypicalExpenses.getTypicalExpenseList();
        assertEquals(expenseListFromFile, typicalExpensesExpenseList);
    }

    @Test
    public void toModelType_invalidExpenseFile_throwsIllegalValueException() throws Exception {
        JsonSerializableExpenseList dataFromFile = JsonUtil.readJsonFile(INVALID_EXPENSE_FILE,
                JsonSerializableExpenseList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateExpenses_throwsIllegalValueException() throws Exception {
        JsonSerializableExpenseList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_EXPENSE_FILE,
                JsonSerializableExpenseList.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableExpenseList.MESSAGE_DUPLICATE_EXPENSE,
                dataFromFile::toModelType);
    }

}
