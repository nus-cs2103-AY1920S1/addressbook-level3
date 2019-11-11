package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSchedules.SCHEDULE_TEST;
import static seedu.address.testutil.TypicalSchedules.SCHEDULE_TEST2;
import static seedu.address.testutil.TypicalSchedules.MONDAY_SCHEDULE;
import static seedu.address.testutil.TypicalSchedules.getTypicalScheduleBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.DataBook;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.schedule.Schedule;

public class JsonScheduleBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonScheduleBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readScheduleBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readScheduleBook(null));
    }

    private java.util.Optional<ReadOnlyDataBook<Schedule>> readScheduleBook(String filePath) throws Exception {
        return new JsonScheduleBookStorage(Paths.get(filePath)).readScheduleBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readScheduleBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readScheduleBook("notJsonFormatScheduleBook.json"));
    }

    @Test
    public void readScheduleBook_invalidScheduleScheduleBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readScheduleBook("invalidScheduleScheduleBook.json"));
    }

    @Test
    public void readScheduleBook_invalidAndValidScheduleScheduleBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readScheduleBook("invalidAndValidScheduleScheduleBook.json"));
    }

    @Test
    public void readAndSaveScheduleBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempScheduleBook.json");
        DataBook<Schedule> original = getTypicalScheduleBook();
        JsonScheduleBookStorage jsonScheduleBookStorage = new JsonScheduleBookStorage(filePath);

        // Save in new file and read back
        jsonScheduleBookStorage.saveScheduleBook(original, filePath);
        ReadOnlyDataBook readBack = jsonScheduleBookStorage.readScheduleBook(filePath).get();
        assertEquals(original, new DataBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.add(SCHEDULE_TEST);
        original.remove(MONDAY_SCHEDULE);
        jsonScheduleBookStorage.saveScheduleBook(original, filePath);
        readBack = jsonScheduleBookStorage.readScheduleBook(filePath).get();
        assertEquals(original, new DataBook<Schedule>(readBack));

        // Save and read without specifying file path
        original.add(SCHEDULE_TEST2);
        jsonScheduleBookStorage.saveScheduleBook(original); // file path not specified
        readBack = jsonScheduleBookStorage.readScheduleBook().get(); // file path not specified
        assertEquals(original, new DataBook(readBack));

    }

    @Test
    public void saveScheduleBook_nullScheduleBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveScheduleBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code ScheduleBook} at the specified {@code filePath}.
     */
    private void saveScheduleBook(ReadOnlyDataBook scheduleBook, String filePath) {
        try {
            new JsonScheduleBookStorage(Paths.get(filePath))
                    .saveScheduleBook(scheduleBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveScheduleBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveScheduleBook(new DataBook(), null));
    }
}