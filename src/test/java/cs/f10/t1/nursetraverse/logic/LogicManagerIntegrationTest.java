package cs.f10.t1.nursetraverse.logic;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import cs.f10.t1.nursetraverse.model.ModelManager;
import cs.f10.t1.nursetraverse.model.UserPrefs;
import cs.f10.t1.nursetraverse.storage.JsonAppointmentBookStorage;
import cs.f10.t1.nursetraverse.storage.JsonPatientBookStorage;
import cs.f10.t1.nursetraverse.storage.JsonUserPrefsStorage;
import cs.f10.t1.nursetraverse.storage.StorageManager;
import cs.f10.t1.nursetraverse.testutil.TypicalAppointments;
import cs.f10.t1.nursetraverse.testutil.TypicalPatients;

/**
 * Contains integration tests (interaction with parsers, commands) for {@code LogicManager}.
 */
public class LogicManagerIntegrationTest {

    @TempDir
    public Path temporaryFolder;

    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonPatientBookStorage patientBookStorage =
                new JsonPatientBookStorage(temporaryFolder.resolve("patientBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonAppointmentBookStorage appointmentBookStorage =
                new JsonAppointmentBookStorage(temporaryFolder.resolve("appointmentBook.json"));
        StorageManager storage = new StorageManager(patientBookStorage, userPrefsStorage, appointmentBookStorage);
        logic = new LogicManager(new ModelManager(TypicalPatients.getTypicalPatientBook(), new UserPrefs(),
                                                  TypicalAppointments.getTypicalAppointmentBook()), storage);
    }

    @Test
    public void execute_undoRedoWithoutVisitCommands_success() {
        // Undo redo without calling visit commands
        assertCommandSuccess("pat-list");
        assertCommandSuccess("pat-clear");
        assertCommandSuccess("app-undo");
        assertCommandSuccess("pat-delete 1");
        assertCommandSuccess("app-undo");
        assertCommandSuccess("app-redo");
        assertCommandSuccess("pat-list");
    }

    @Test
    public void execute_undoRedoWithVisitCommands_success() {
        /*
         * Test for bug in https://github.com/AY1920S1-CS2103-F10-1/main/issues/162 which was an interaction between
         * visit-end and undo/redo/history.
         */

        // Undo redo after calling visit commands
        assertCommandSuccess("visit-start 1");
        assertCommandSuccess("visit-end");
        assertCommandSuccess("pat-list");
        assertCommandSuccess("app-undo");
        assertCommandSuccess("app-redo");
    }

    public void assertCommandSuccess(String inputCommand) {
        assertDoesNotThrow(() -> logic.execute(inputCommand));
    }
}
