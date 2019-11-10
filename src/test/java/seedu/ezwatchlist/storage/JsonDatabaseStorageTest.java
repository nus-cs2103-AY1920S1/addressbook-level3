package seedu.ezwatchlist.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;
import static seedu.ezwatchlist.testutil.TypicalShows.FIGHTCLUB;
import static seedu.ezwatchlist.testutil.TypicalShows.GODFATHER2;
import static seedu.ezwatchlist.testutil.TypicalShows.getTypicalWatchList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.ezwatchlist.commons.exceptions.DataConversionException;
import seedu.ezwatchlist.model.ReadOnlyWatchList;
import seedu.ezwatchlist.model.WatchList;

public class JsonDatabaseStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonDatabaseStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readWatchList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readWatchList(null));
    }

    private java.util.Optional<ReadOnlyWatchList> readWatchList(String filePath) throws Exception {
        return new JsonDatabaseStorage(Paths.get(filePath)).readDatabase(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readWatchList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readWatchList("notJsonFormatDatabase.json"));
    }

    @Test
    public void readWatchList_invalidShowWatchList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readWatchList("invalidShowDatabase.json"));
    }

    @Test
    public void readWatchList_invalidAndValidShowWatchList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readWatchList("invalidAndValidShowDatabase.json"));
    }


    @Test
    public void readAndSaveWatchList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempWatchList.json");
        WatchList original = getTypicalWatchList();
        JsonDatabaseStorage jsonDatabaseStorage = new JsonDatabaseStorage(filePath);

        // Save in new file and read back
        jsonDatabaseStorage.saveDatabase(original, filePath);
        ReadOnlyWatchList readBack = jsonDatabaseStorage.readDatabase(filePath).get();
        assertEquals(original, new WatchList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addShow(GODFATHER2);
        original.removeShow(FIGHTCLUB);
        jsonDatabaseStorage.saveDatabase(original, filePath);
        readBack = jsonDatabaseStorage.readDatabase(filePath).get();
        assertEquals(original, new WatchList(readBack));

        // Save and read without specifying file path
        original.addShow(FIGHTCLUB);
        jsonDatabaseStorage.saveDatabase(original); // file path not specified
        readBack = jsonDatabaseStorage.readDatabase().get(); // file path not specified
        assertEquals(original, new WatchList(readBack));

    }

    @Test
    public void saveWatchList_nullWatchList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveWatchList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code watchList} at the specified {@code filePath}.
     */
    private void saveWatchList(ReadOnlyWatchList watchList, String filePath) {
        try {
            new JsonDatabaseStorage(Paths.get(filePath))
                    .saveDatabase(watchList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveWatchList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveWatchList(new WatchList(), null));
    }

}
