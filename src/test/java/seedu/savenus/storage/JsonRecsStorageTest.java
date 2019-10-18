package seedu.savenus.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalRecs.ADDED_ANOTHER_CATEGORY_SET;
import static seedu.savenus.testutil.TypicalRecs.ADDED_ANOTHER_LOCATION_SET;
import static seedu.savenus.testutil.TypicalRecs.ADDED_ANOTHER_TAG_SET;
import static seedu.savenus.testutil.TypicalRecs.ADDED_CATEGORY_SET;
import static seedu.savenus.testutil.TypicalRecs.ADDED_LOCATION_SET;
import static seedu.savenus.testutil.TypicalRecs.ADDED_TAG_SET;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.savenus.commons.exceptions.DataConversionException;
import seedu.savenus.model.recommend.UserRecommendations;

public class JsonRecsStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonRecsStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readRecs_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readRecs(null));
    }

    private java.util.Optional<UserRecommendations> readRecs(String filePath) throws Exception {
        return new JsonRecsStorage(Paths.get(filePath)).readRecs(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readRecs("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readRecs("notJsonFormatRecs.json"));
    }

    @Test
    public void readMenu_invalidAndValidRecs_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readRecs("invalidAndValidRecs.json"));
    }

    public UserRecommendations getTypicalRecs() {
        return new UserRecommendations();
    }

    @Test
    public void readAndSaveMenu_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempMenu.json");
        UserRecommendations original = getTypicalRecs();
        JsonRecsStorage jsonRecsStorage = new JsonRecsStorage(filePath);

        // Save in new file and read back
        jsonRecsStorage.saveRecs(original, filePath);
        UserRecommendations readBack = jsonRecsStorage.readRecs(filePath).get();
        assertEquals(original, readBack);

        // Modify data, overwrite exiting file, and read back
        original.addLikes(ADDED_CATEGORY_SET, ADDED_TAG_SET, ADDED_LOCATION_SET);
        jsonRecsStorage.saveRecs(original, filePath);
        readBack = jsonRecsStorage.readRecs(filePath).get();
        assertEquals(original, readBack);

        // Save and read without specifying file path
        original.addDislikes(ADDED_ANOTHER_CATEGORY_SET, ADDED_ANOTHER_TAG_SET, ADDED_ANOTHER_LOCATION_SET);
        jsonRecsStorage.saveRecs(original); // file path not specified
        readBack = jsonRecsStorage.readRecs().get(); // file path not specified
        assertEquals(original, readBack);
    }

    @Test
    public void saveMenu_nullRecs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveRecs(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveRecs(UserRecommendations recs, String filePath) {
        try {
            new JsonRecsStorage(Paths.get(filePath))
                    .saveRecs(recs, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveRecs_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveRecs(new UserRecommendations(), null));
    }

}
