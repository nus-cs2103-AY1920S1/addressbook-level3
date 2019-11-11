package seedu.address.storage.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.event.TypicalEvents.EVENT1;
import static seedu.address.testutil.event.TypicalEvents.NOT_IN_TYPICAL;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventRecord;
import seedu.address.model.event.ReadOnlyEvents;
import seedu.address.testutil.event.EventBuilder;
import seedu.address.testutil.event.TypicalEvents;

public class JsonEventStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get(
            "src", "test", "data", "JsonEventStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readEventStorage_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readEventRecord(null));
    }

    private java.util.Optional<ReadOnlyEvents> readEventRecord(String filePath) throws Exception {
        return new JsonEventStorage(Paths.get(filePath)).readEvents(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readEventRecord("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readEventRecord("notJsonFormatEventRecord.json"));
    }

    @Test
    public void readEventRecord_invalidEventEventRecord_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readEventRecord("invalidEventEventRecord.json"));
    }

    @Test
    public void readEventRecord_invalidAndValidEventsEventRecord_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
                readEventRecord("invalidAndValidEventsEventRecord.json"));
    }

    @Test
    public void readAndSaveEventRecord_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempNotesRecord.json");
        EventRecord original = TypicalEvents.getTypicalEventsRecord();
        JsonEventStorage jsonEventStorage = new JsonEventStorage(filePath);

        // Save in new file and read back
        jsonEventStorage.saveEvents(original, filePath);
        ReadOnlyEvents readBack = jsonEventStorage.readEvents(filePath).get();
        assertEquals(original, new EventRecord(readBack));

        // Modify data, overwrite exiting file, and read back
        List<Event> eventList = original.getAllEvents();
        eventList.set(0, NOT_IN_TYPICAL);
        original = new EventRecord(eventList);
        jsonEventStorage.saveEvents(original, filePath);
        readBack = jsonEventStorage.readEvents(filePath).get();
        assertEquals(original, new EventRecord(readBack));

        // Save and read without specifying file path
        Event modifiedEvent = new EventBuilder(EVENT1).withEventName("different event name").build();
        List<Event> eventList2 = original.getAllEvents();
        eventList2.add(modifiedEvent);
        original = new EventRecord(eventList2);
        jsonEventStorage.saveEvents(original); // file path not specified
        readBack = jsonEventStorage.readEvents().get(); // file path not specified
        assertEquals(original, new EventRecord(readBack));

    }

    @Test
    public void saveEventRecord_nullEventRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveEventRecord(null, "SomeFile.json"));
    }

    /**
     * Saves {@code eventRecord} at the specified {@code filePath}.
     */
    private void saveEventRecord(ReadOnlyEvents eventRecord, String filePath) {
        try {
            new JsonEventStorage(Paths.get(filePath))
                    .saveEvents(eventRecord, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveNotesRecord_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveEventRecord(new EventRecord(), null));
    }
}
