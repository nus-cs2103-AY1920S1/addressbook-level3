package seedu.exercise.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_REGIME_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_SCHEDULE_COMPARATOR;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.RATING;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;
import static seedu.exercise.testutil.typicalutil.TypicalRegime.getTypicalRegimeBook;
import static seedu.exercise.testutil.typicalutil.TypicalSchedule.getTypicalScheduleBook;

import java.nio.file.Path;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.exercise.commons.core.GuiSettings;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.property.CustomProperty;
import seedu.exercise.model.property.PropertyBook;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Schedule;
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

        JsonExerciseBookStorage jsonExerciseDatabaseBookStorage =
            new JsonExerciseBookStorage(getTempFilePath("aeb"));

        JsonScheduleBookStorage jsonScheduleBookStorage =
            new JsonScheduleBookStorage((getTempFilePath("sb")));

        JsonUserPrefsStorage userPrefsStorage =
            new JsonUserPrefsStorage(getTempFilePath("prefs"));

        JsonPropertyBookStorage propertyBookStorage =
            new JsonPropertyBookStorage(getTempFilePath("pm"));

        storageManager = new StorageBook(jsonExerciseBookStorage, jsonExerciseDatabaseBookStorage,
            jsonRegimeBookStorage, jsonScheduleBookStorage, userPrefsStorage, propertyBookStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void getFilePath() {
        //exercise book
        assertNotNull(storageManager.getExerciseBookFilePath());
        //regime book
        assertNotNull(storageManager.getRegimeBookFilePath());
        //exercise database
        assertNotNull(storageManager.getExerciseDatabaseFilePath());
        //schedule book
        assertNotNull(storageManager.getScheduleBookFilePath());
        //user prefs
        assertNotNull(storageManager.getUserPrefsFilePath());
        //property book
        assertNotNull(storageManager.getPropertyBookFilePath());
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
        assertEquals(original, new ReadOnlyResourceBook<>(retrieved, DEFAULT_EXERCISE_COMPARATOR));
    }

    @Test
    public void exerciseDatabaseReadSave() throws Exception {
        ReadOnlyResourceBook<Exercise> original = getTypicalExerciseBook();
        storageManager.saveExerciseDatabase(original);
        ReadOnlyResourceBook<Exercise> retrieved = storageManager.readExerciseDatabase().get();
        ReadOnlyResourceBook<Exercise> retrievedUsingFilePath =
            storageManager.readExerciseDatabase(storageManager.getExerciseDatabaseFilePath()).get();
        assertEquals(original, new ReadOnlyResourceBook<>(retrieved, DEFAULT_EXERCISE_COMPARATOR));
        assertEquals(original, new ReadOnlyResourceBook<>(retrievedUsingFilePath, DEFAULT_EXERCISE_COMPARATOR));
    }

    @Test
    public void regimeBookReadSave() throws Exception {
        ReadOnlyResourceBook<Regime> original = getTypicalRegimeBook();
        storageManager.saveRegimeBook(original);
        ReadOnlyResourceBook<Regime> retrieved = storageManager.readRegimeBook().get();
        assertEquals(original, new ReadOnlyResourceBook<>(retrieved, DEFAULT_REGIME_COMPARATOR));
    }

    @Test
    public void scheduleBookReadSave() throws Exception {
        ReadOnlyResourceBook<Schedule> original = getTypicalScheduleBook();
        storageManager.saveScheduleBook(original);
        ReadOnlyResourceBook<Schedule> retrieved = storageManager.readScheduleBook().get();
        assertEquals(original, new ReadOnlyResourceBook<>(retrieved, DEFAULT_SCHEDULE_COMPARATOR));
    }

    @Test
    public void propertyBookReadSave() throws Exception {
        PropertyBook propertyBook = PropertyBook.getInstance();
        propertyBook.clearCustomProperties();
        Set<CustomProperty> originalSet = Set.of(RATING);
        propertyBook.addCustomProperties(originalSet);
        storageManager.savePropertyBook();
        propertyBook.clearCustomProperties();
        storageManager.readPropertyBook();
        Set<CustomProperty> retrievedSet = propertyBook.getCustomProperties();
        assertEquals(originalSet, retrievedSet);
    }

}
