package seedu.exercise.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.CLAP;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.SLAP;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.WALK;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.exercise.commons.exceptions.DataConversionException;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.storage.bookstorage.JsonExerciseBookStorage;

public class JsonExerciseBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonExerciseBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readExerciseBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readExerciseBook(null));
    }

    private java.util.Optional<ReadOnlyResourceBook<Exercise>> readExerciseBook(String filePath) throws Exception {
        return new JsonExerciseBookStorage(Paths.get(filePath))
            .readResourceBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
            : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readExerciseBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readExerciseBook("notJsonFormatExerciseBook.json"));
    }

    @Test
    public void readExerciseBook_invalidExerciseBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readExerciseBook("invalidExerciseBook.json"));
    }

    @Test
    public void readExerciseBook_invalidAndValidExerciseBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readExerciseBook("invalidAndValidExerciseBook.json"));
    }

    @Test
    public void readAndSaveExerciseBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempExerciseBook.json");
        ReadOnlyResourceBook<Exercise> original = getTypicalExerciseBook();
        JsonExerciseBookStorage jsonExerciseBookStorage = new JsonExerciseBookStorage(filePath);

        // Save in new file and read back
        jsonExerciseBookStorage.saveResourceBook(original, filePath);
        ReadOnlyResourceBook<Exercise> readBack = jsonExerciseBookStorage.readResourceBook(filePath).get();
        assertEquals(original, new ReadOnlyResourceBook<>(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addResource(CLAP);
        original.removeResource(WALK);
        jsonExerciseBookStorage.saveResourceBook(original, filePath);
        readBack = jsonExerciseBookStorage.readResourceBook(filePath).get();
        assertEquals(original, new ReadOnlyResourceBook<>(readBack));

        // Save and read without specifying file path
        original.addResource(SLAP);
        jsonExerciseBookStorage.saveResourceBook(original); // file path not specified
        readBack = jsonExerciseBookStorage.readResourceBook().get(); // file path not specified
        assertEquals(original, new ReadOnlyResourceBook<>(readBack));

    }

    @Test
    public void saveExerciseBook_nullExerciseBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveExerciseBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code exerciseBook} at the specified {@code filePath}.
     */
    private void saveExerciseBook(ReadOnlyResourceBook<Exercise> exerciseBook, String filePath) {
        try {
            new JsonExerciseBookStorage(Paths.get(filePath))
                .saveResourceBook(exerciseBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveExerciseBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveExerciseBook(new ReadOnlyResourceBook<>(), null));
    }
}
