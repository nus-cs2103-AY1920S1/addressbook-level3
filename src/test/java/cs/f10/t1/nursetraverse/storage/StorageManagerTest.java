package cs.f10.t1.nursetraverse.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import cs.f10.t1.nursetraverse.commons.core.GuiSettings;
import cs.f10.t1.nursetraverse.model.PatientBook;
import cs.f10.t1.nursetraverse.model.ReadOnlyPatientBook;
import cs.f10.t1.nursetraverse.model.UserPrefs;
import cs.f10.t1.nursetraverse.testutil.TypicalPatients;


public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonPatientBookStorage patientBookStorage = new JsonPatientBookStorage(getTempFilePath("pb"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonAppointmentBookStorage appointmentBookStorage = new JsonAppointmentBookStorage(getTempFilePath("ab"));
        storageManager = new StorageManager(patientBookStorage, userPrefsStorage, appointmentBookStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
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
    public void patientBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonPatientBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonPatientBookStorageTest} class.
         */
        PatientBook original = TypicalPatients.getTypicalPatientBook();
        storageManager.savePatientBook(original);
        ReadOnlyPatientBook retrieved = storageManager.readPatientBook().get();
        assertEquals(original, new PatientBook(retrieved));
    }

    @Test
    public void getPatientBookFilePath() {
        assertNotNull(storageManager.getPatientBookFilePath());
    }

}
