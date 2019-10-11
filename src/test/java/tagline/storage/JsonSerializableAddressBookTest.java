package tagline.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tagline.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import tagline.commons.exceptions.IllegalValueException;
import tagline.commons.util.JsonUtil;
import tagline.model.AddressBook;
import tagline.testutil.TypicalContacts;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_CONTACTS_FILE = TEST_DATA_FOLDER.resolve("typicalContactsAddressBook.json");
    private static final Path INVALID_CONTACT_FILE = TEST_DATA_FOLDER.resolve("invalidContactAddressBook.json");
    private static final Path DUPLICATE_CONTACT_FILE = TEST_DATA_FOLDER.resolve("duplicateContactAddressBook.json");

    @Test
    public void toModelType_typicalContactsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_CONTACTS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalContactsAddressBook = TypicalContacts.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalContactsAddressBook);
    }

    @Test
    public void toModelType_invalidContactFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_CONTACT_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateContacts_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_CONTACT_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_CONTACT,
                dataFromFile::toModelType);
    }

}
