package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.HOON;
import static seedu.address.testutil.TypicalContacts.IDA;
import static seedu.address.testutil.TypicalContacts.getTypicalItinerary;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Itinerary;
import seedu.address.model.ReadOnlyItinerary;

public class JsonItineraryStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonItineraryStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readItinerary_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readItinerary(null));
    }

    private java.util.Optional<ReadOnlyItinerary> readItinerary(String filePath) throws Exception {
        return new JsonItineraryStorage(Paths.get(filePath)).readItinerary(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readItinerary("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readItinerary("notJsonFormatItinerary.json"));
    }

    @Test
    public void readItinerary_invalidContactItinerary_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readItinerary("invalidContactItinerary.json"));
    }

    @Test
    public void readItinerary_invalidAndValidContactItinerary_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readItinerary("invalidAndValidContactItinerary.json"));
    }

    @Test
    public void readAndSaveItinerary_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempItinerary.json");
        Itinerary original = getTypicalItinerary();
        JsonItineraryStorage jsonItineraryStorage = new JsonItineraryStorage(filePath);

        // Save in new file and read back
        jsonItineraryStorage.saveItinerary(original, filePath);
        ReadOnlyItinerary readBack = jsonItineraryStorage.readItinerary(filePath).get();
        assertEquals(original, new Itinerary(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addContact(HOON);
        original.removeContact(ALICE);
        jsonItineraryStorage.saveItinerary(original, filePath);
        readBack = jsonItineraryStorage.readItinerary(filePath).get();
        assertEquals(original, new Itinerary(readBack));

        // Save and read without specifying file path
        original.addContact(IDA);
        jsonItineraryStorage.saveItinerary(original); // file path not specified
        readBack = jsonItineraryStorage.readItinerary().get(); // file path not specified
        assertEquals(original, new Itinerary(readBack));

    }

    @Test
    public void saveItinerary_nullItinerary_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveItinerary(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveItinerary(ReadOnlyItinerary itinerary, String filePath) {
        try {
            new JsonItineraryStorage(Paths.get(filePath))
                    .saveItinerary(itinerary, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveItinerary_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveItinerary(new Itinerary(), null));
    }
}
