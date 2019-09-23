package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalExpenses;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_EXPENSES_FILE = TEST_DATA_FOLDER.resolve("typicalExpensesAddressBook.json");
    private static final Path INVALID_EXPENSE_FILE = TEST_DATA_FOLDER.resolve("invalidExpenseAddressBook.json");
    private static final Path DUPLICATE_EXPENSE_FILE = TEST_DATA_FOLDER.resolve("duplicateExpenseAddressBook.json");

    @Test
    public void toModelType_typicalExpensesFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_EXPENSES_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalExpensesAddressBook = TypicalExpenses.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalExpensesAddressBook);
    }

    @Test
    public void toModelType_invalidExpenseFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_EXPENSE_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateExpenses_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_EXPENSE_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_EXPENSE,
                dataFromFile::toModelType);
    }

}
