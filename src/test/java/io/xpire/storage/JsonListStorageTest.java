package io.xpire.storage;

import static io.xpire.testutil.Assert.assertThrows;
import static io.xpire.testutil.TypicalItems.KIWI;
import static io.xpire.testutil.TypicalItems.getTypicalLists;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import io.xpire.commons.exceptions.DataConversionException;
import io.xpire.model.ReadOnlyListView;
import io.xpire.model.ReplenishList;
import io.xpire.model.Xpire;
import io.xpire.model.item.Item;

public class JsonListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readBothLists_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readBothLists(null));
    }

    private Optional<ReadOnlyListView<? extends Item>>[] readBothLists(String filePath) throws Exception {
        return new JsonListStorage(Paths.get(filePath))
                .readList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readBothLists("NonExistentFile.json")[0].isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () ->
                readBothLists("notJsonFormatXpire.json"));
    }

    @Test
    public void readBothLists_invalidItemExpiryDateTracker_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
                readBothLists("invalidItemXpire.json"));
    }

    @Test
    public void readBothLists_invalidAndValidItemExpiryDateTracker_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
                readBothLists("invalidAndValidItemXpire.json"));
    }

    @Test
    public void readAndSaveExpiryDateTracker_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempXpire.json");
        ReadOnlyListView<? extends Item>[] bothLists = getTypicalLists();
        JsonListStorage jsonExpiryDateTrackerStorage = new JsonListStorage(filePath);
        Xpire original = (Xpire) bothLists[0];

        // Save in new file and read back
        jsonExpiryDateTrackerStorage.saveList(bothLists, filePath);
        ReadOnlyListView readBack = jsonExpiryDateTrackerStorage.readList(filePath)[0].get();
        assertEquals(original.getItemList(), new Xpire(readBack).getItemList());

        // Modify data, overwrite exiting file, and read back
        original.addItem(KIWI);
        jsonExpiryDateTrackerStorage.saveList(bothLists, filePath);
        readBack = jsonExpiryDateTrackerStorage.readList(filePath)[0].get();
        assertEquals(original.getItemList(), new Xpire(readBack).getItemList());

        // Save and read without specifying file path
        jsonExpiryDateTrackerStorage.saveList(bothLists); // file path not specified
        readBack = jsonExpiryDateTrackerStorage.readList()[0].get(); // file path not specified
        assertEquals(original.getItemList(), new Xpire(readBack).getItemList());

    }

    @Test
    public void saveExpiryDateTracker_nullExpiryDateTracker_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code xpire} at the specified {@code filePath}.
     */
    private void saveList(ReadOnlyListView<? extends Item>[] lists, String filePath) {
        try {
            new JsonListStorage(Paths.get(filePath))
                    .saveList(lists, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveList_nullFilePath_throwsNullPointerException() {
        ReadOnlyListView<? extends Item>[] lists = new ReadOnlyListView[]{new Xpire(), new ReplenishList()};
        assertThrows(NullPointerException.class, () -> saveList(lists, null));
    }
}
