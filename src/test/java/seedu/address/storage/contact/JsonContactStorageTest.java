package seedu.address.storage.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.contact.TypicalContacts.ALICE;
import static seedu.address.testutil.contact.TypicalContacts.HOON;
import static seedu.address.testutil.contact.TypicalContacts.IDA;
import static seedu.address.testutil.contact.TypicalContacts.getTypicalContactManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ContactManager;
import seedu.address.model.ReadOnlyContact;

public class JsonContactStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonContactStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readContact_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readContact(null));
    }

    private java.util.Optional<ReadOnlyContact> readContact(String filePath) throws Exception {
        return new JsonContactStorage(Paths.get(filePath)).readContact(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readContact("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readContact("notJsonFormatContact.json"));
    }

    @Test
    public void readContact_invalidContact_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readContact("invalidContact.json"));
    }

    @Test
    public void readContact_invalidAndValidDataContact_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readContact("invalidAndValidDataContact.json"));
    }

    @Test
    public void readAndSaveContact_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempContact.json");
        ContactManager original = getTypicalContactManager();
        JsonContactStorage jsonContactStorage = new JsonContactStorage(filePath);

        // Save in new file and read back
        jsonContactStorage.saveContact(original, filePath);
        ReadOnlyContact readBack = jsonContactStorage.readContact(filePath).get();
        assertEquals(original, new ContactManager(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addContact(HOON);
        original.removeContact(ALICE);
        jsonContactStorage.saveContact(original, filePath);
        readBack = jsonContactStorage.readContact(filePath).get();
        assertEquals(original, new ContactManager(readBack));

        // Save and read without specifying file path
        original.addContact(IDA);
        jsonContactStorage.saveContact(original); // file path not specified
        readBack = jsonContactStorage.readContact().get(); // file path not specified
        assertEquals(original, new ContactManager(readBack));

    }

    @Test
    public void saveContact_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveContact(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveContact(ReadOnlyContact contact, String filePath) {
        try {
            new JsonContactStorage(Paths.get(filePath))
                    .saveContact(contact, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveContact_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveContact(new ContactManager(), null));
    }
}

