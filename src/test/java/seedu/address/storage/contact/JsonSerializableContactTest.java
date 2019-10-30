package seedu.address.storage.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ContactManager;
import seedu.address.testutil.contact.TypicalContacts;

public class JsonSerializableContactTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableContactTest");
    private static final Path TYPICAL_CONTACTS_FILE = TEST_DATA_FOLDER.resolve("typicalContacts.json");
    private static final Path INVALID_CONTACT_FILE = TEST_DATA_FOLDER.resolve("invalidDataContact.json");
    private static final Path DUPLICATE_CONTACT_FILE = TEST_DATA_FOLDER.resolve("duplicateDataContact.json");

    @Test
    public void toModelType_typicalContactsFile_success() throws Exception {
        JsonSerializableContact dataFromFile = JsonUtil.readJsonFile(TYPICAL_CONTACTS_FILE,
                JsonSerializableContact.class).get();
        ContactManager contactFromFile = dataFromFile.toModelType();
        ContactManager typicalContactsContact = TypicalContacts.getTypicalContactManager();
        assertEquals(contactFromFile, typicalContactsContact);
    }

    @Test
    public void toModelType_invalidContactFile_throwsIllegalValueException() throws Exception {
        JsonSerializableContact dataFromFile = JsonUtil.readJsonFile(INVALID_CONTACT_FILE,
                JsonSerializableContact.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateContacts_throwsIllegalValueException() throws Exception {
        JsonSerializableContact dataFromFile = JsonUtil.readJsonFile(DUPLICATE_CONTACT_FILE,
                JsonSerializableContact.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableContact.MESSAGE_DUPLICATE_CONTACT,
                dataFromFile::toModelType);
    }

}
