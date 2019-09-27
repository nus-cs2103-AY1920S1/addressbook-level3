package seedu.mark.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.mark.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.commons.util.JsonUtil;
import seedu.mark.model.BookmarkManager;
import seedu.mark.testutil.TypicalPersons;

public class JsonSerializableBookmarkManagerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableBookmarkManagerTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableBookmarkManager dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableBookmarkManager.class).get();
        BookmarkManager addressBookFromFile = dataFromFile.toModelType();
        BookmarkManager typicalPersonsAddressBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableBookmarkManager dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableBookmarkManager.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableBookmarkManager dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableBookmarkManager.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableBookmarkManager.MESSAGE_DUPLICATE_BOOKMARK,
                dataFromFile::toModelType);
    }

}
