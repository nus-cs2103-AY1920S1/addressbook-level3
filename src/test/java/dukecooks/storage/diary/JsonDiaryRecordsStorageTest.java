package dukecooks.storage.diary;

import static dukecooks.testutil.diary.TypicalDiaries.ALL_MEAT;
import static dukecooks.testutil.diary.TypicalDiaries.HEALTHY_LIVING;
import static dukecooks.testutil.diary.TypicalDiaries.ICE_CREAM_GALORE;
import static dukecooks.testutil.diary.TypicalDiaries.getTypicalDiaryRecords;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import dukecooks.commons.exceptions.DataConversionException;
import dukecooks.model.diary.DiaryRecords;
import dukecooks.model.diary.ReadOnlyDiary;
import dukecooks.testutil.Assert;

public class JsonDiaryRecordsStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonDiaryRecordsStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readDiary_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readDiary(null));
    }

    private java.util.Optional<ReadOnlyDiary> readDiary(String filePath) throws Exception {
        return new JsonDiaryStorage(Paths.get(filePath)).readDiary(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readDiary("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataConversionException.class, () -> readDiary("notJsonFormatDiaryRecords.json"));
    }

    @Test
    public void readDiary_invalidDiaryDukeCooks_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readDiary("invalidDiaryRecords.json"));
    }

    @Test
    public void readDiary_invalidAndValidDiaryDukeCooks_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readDiary("invalidAndValidDiaryRecords.json"));
    }

    @Test
    public void readAndSaveDiary_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempDukeCooks.json");
        DiaryRecords original = getTypicalDiaryRecords();
        JsonDiaryStorage jsonDiaryStorage = new JsonDiaryStorage(filePath);

        // Save in new file and read back
        jsonDiaryStorage.saveDiary(original, filePath);
        ReadOnlyDiary readBack = jsonDiaryStorage.readDiary(filePath).get();
        assertEquals(original, new DiaryRecords(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addDiary(HEALTHY_LIVING);
        original.removeDiary(ALL_MEAT);
        jsonDiaryStorage.saveDiary(original, filePath);
        readBack = jsonDiaryStorage.readDiary(filePath).get();
        assertEquals(original, new DiaryRecords(readBack));

        // Save and read without specifying file path
        original.addDiary(ICE_CREAM_GALORE);
        jsonDiaryStorage.saveDiary(original); // file path not specified
        readBack = jsonDiaryStorage.readDiary().get(); // file path not specified
        assertEquals(original, new DiaryRecords(readBack));

    }

    @Test
    public void saveDiary_nullDiary_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveDiary(null, "SomeFile.json"));
    }

    /**
     * Saves {@code diaries} at the specified {@code filePath}.
     */
    private void saveDiary(ReadOnlyDiary diary, String filePath) {
        try {
            new JsonDiaryStorage(Paths.get(filePath))
                    .saveDiary(diary, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveDiary_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveDiary(new DiaryRecords(), null));
    }
}
