package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalEateries;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_OPEN_EATERIES_FILE = TEST_DATA_FOLDER
            .resolve("typicalOpenEateriesAddressBook.json");
    private static final Path TYPICAL_CLOSE_EATERIES_FILE = TEST_DATA_FOLDER
            .resolve("typicalCloseEateriesAddressBook.json");
    private static final Path INVALID_EATERY_FILE = TEST_DATA_FOLDER.resolve("invalidEateryAddressBook.json");
    private static final Path DUPLICATE_EATERY_FILE = TEST_DATA_FOLDER.resolve("duplicateEateryAddressBook.json");

    @Test
    public void toModelType_typicalOpenEateriesFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_OPEN_EATERIES_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalOpenEateriesAddressBook = TypicalEateries.getTypicalOpenAddressBook();
        assertEquals(addressBookFromFile, typicalOpenEateriesAddressBook);
    }

    @Test
    public void toModelType_typicalCloseEateriesFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_CLOSE_EATERIES_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalCloseEateriesAddressBook = TypicalEateries.getTypicalCloseAddressBook();
        assertEquals(addressBookFromFile, typicalCloseEateriesAddressBook);
    }

}
