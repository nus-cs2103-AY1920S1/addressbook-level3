package seedu.exercise.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.exercise.commons.core.GuiSettings;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.storage.bookstorage.JsonExerciseBookStorage;
import seedu.exercise.storage.bookstorage.JsonRegimeBookStorage;
import seedu.exercise.storage.bookstorage.JsonScheduleBookStorage;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageBook storageManager;

    @BeforeEach
    public void setUp() {
        JsonExerciseBookStorage jsonExerciseBookStorage =
            new JsonExerciseBookStorage(getTempFilePath("eb"));

        JsonRegimeBookStorage jsonRegimeBookStorage =
            new JsonRegimeBookStorage(getTempFilePath("rb"));

        JsonExerciseBookStorage allJsonExerciseBookStorage =
            new JsonExerciseBookStorage(getTempFilePath("aeb"));

        JsonScheduleBookStorage jsonScheduleBookStorage =
            new JsonScheduleBookStorage((getTempFilePath("sb")));

        JsonUserPrefsStorage userPrefsStorage =
            new JsonUserPrefsStorage(getTempFilePath("prefs"));

        JsonPropertyBookStorage propertyBookStorage =
            new JsonPropertyBookStorage(getTempFilePath("pm"));

        storageManager = new StorageBook(jsonExerciseBookStorage, allJsonExerciseBookStorage,
            jsonRegimeBookStorage, jsonScheduleBookStorage, userPrefsStorage, propertyBookStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageBook is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void exerciseBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageBook is properly wired to the
         * {@link JsonExerciseBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonExerciseBookStorageTest} class.
         */
        ReadOnlyResourceBook<Exercise> original = getTypicalExerciseBook();
        storageManager.saveExerciseBook(original);
        ReadOnlyResourceBook<Exercise> retrieved = storageManager.readExerciseBook().get();
        assertEquals(original, new ReadOnlyResourceBook<>(retrieved));
    }

    @Test
    public void getExerciseBookFilePath() {
        assertNotNull(storageManager.getExerciseBookFilePath());
    }

}
