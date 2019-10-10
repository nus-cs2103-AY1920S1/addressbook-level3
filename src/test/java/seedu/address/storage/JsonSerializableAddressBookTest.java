package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalSpendings;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_SPENDINGS_FILE = TEST_DATA_FOLDER.resolve("typicalSpendingsAddressBook.json");
    private static final Path INVALID_SPENDING_FILE = TEST_DATA_FOLDER.resolve("invalidSpendingAddressBook.json");
    private static final Path DUPLICATE_SPENDING_FILE = TEST_DATA_FOLDER.resolve("duplicateSpendingAddressBook.json");

    @Test
    public void toModelType_typicalSpendingsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_SPENDINGS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalSpendingsAddressBook = TypicalSpendings.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalSpendingsAddressBook);
    }

    @Test
    public void toModelType_invalidSpendingFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_SPENDING_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void petoModelType_duplicateSpendings_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_SPENDING_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_SPENDING,
                dataFromFile::toModelType);
    }

}
