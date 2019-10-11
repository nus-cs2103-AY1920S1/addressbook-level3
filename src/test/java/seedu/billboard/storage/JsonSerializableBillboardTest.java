package seedu.billboard.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.billboard.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.billboard.commons.exceptions.IllegalValueException;
import seedu.billboard.commons.util.JsonUtil;
import seedu.billboard.model.Billboard;
import seedu.billboard.testutil.TypicalExpenses;

public class JsonSerializableBillboardTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableBillboardTest");
    private static final Path TYPICAL_EXPENSES_FILE = TEST_DATA_FOLDER.resolve("typicalExpensesBillboard.json");
    private static final Path INVALID_EXPENSE_FILE = TEST_DATA_FOLDER.resolve("invalidExpenseBillboard.json");
    private static final Path DUPLICATE_EXPENSE_FILE = TEST_DATA_FOLDER.resolve("duplicateExpensesBillboard.json");

    @Test
    public void toModelType_typicalExpensesFile_success() throws Exception {
        JsonSerializableBillboard dataFromFile = JsonUtil.readJsonFile(TYPICAL_EXPENSES_FILE,
                JsonSerializableBillboard.class).get();
        Billboard billboardFromFile = dataFromFile.toModelType();
        Billboard typicalExpensesBillboard = TypicalExpenses.getTypicalBillboard();
        assertEquals(billboardFromFile, typicalExpensesBillboard);
    }

    @Test
    public void toModelType_invalidExpenseFile_throwsIllegalValueException() throws Exception {
        JsonSerializableBillboard dataFromFile = JsonUtil.readJsonFile(INVALID_EXPENSE_FILE,
                JsonSerializableBillboard.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateExpenses_throwsIllegalValueException() throws Exception {
        JsonSerializableBillboard dataFromFile = JsonUtil.readJsonFile(DUPLICATE_EXPENSE_FILE,
                JsonSerializableBillboard.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableBillboard.MESSAGE_DUPLICATE_EXPENSE,
                dataFromFile::toModelType);
    }

}
