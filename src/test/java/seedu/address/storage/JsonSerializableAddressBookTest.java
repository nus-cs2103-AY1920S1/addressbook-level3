package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalMemes;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_MEMES_FILE = TEST_DATA_FOLDER.resolve("typicalMemesAddressBook.json");
    private static final Path INVALID_MEME_FILE = TEST_DATA_FOLDER.resolve("invalidMemeAddressBook.json");
    private static final Path DUPLICATE_MEME_FILE = TEST_DATA_FOLDER.resolve("duplicateMemeAddressBook.json");

    @Test
    public void toModelType_typicalMemesFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_MEMES_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalMemesAddressBook = TypicalMemes.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalMemesAddressBook);
    }

    @Test
    public void toModelType_invalidMemeFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_MEME_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateMemes_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MEME_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_MEME,
                dataFromFile::toModelType);
    }

}
