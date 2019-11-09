package cs.f10.t1.nursetraverse.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.commons.core.GuiSettings;
import cs.f10.t1.nursetraverse.model.patient.NameContainsKeywordsPredicate;
import cs.f10.t1.nursetraverse.testutil.AppointmentBookBuilder;
import cs.f10.t1.nursetraverse.testutil.Assert;
import cs.f10.t1.nursetraverse.testutil.DummyMutatorCommand;
import cs.f10.t1.nursetraverse.testutil.PatientBookBuilder;
import cs.f10.t1.nursetraverse.testutil.TypicalAppointments;
import cs.f10.t1.nursetraverse.testutil.TypicalPatients;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        Assertions.assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new PatientBook(), new PatientBook(modelManager.getStagedPatientBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setPatientBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setPatientBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        Assertions.assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setPatientBookFilePath_nullPath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setPatientBookFilePath(null));
    }

    @Test
    public void setPatientBookFilePath_validPath_setsPatientBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setPatientBookFilePath(path);
        assertEquals(path, modelManager.getPatientBookFilePath());
    }

    @Test
    public void hasPatient_nullPatient_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.hasPatient(null));
    }

    @Test
    public void hasPatient_patientNotInPatientBook_returnsFalse() {
        assertFalse(modelManager.hasPatient(TypicalPatients.ALICE));
    }

    @Test
    public void hasPatient_patientInPatientBook_returnsTrue() {
        modelManager.addPatient(TypicalPatients.ALICE);
        assertTrue(modelManager.hasPatient(TypicalPatients.ALICE));
    }

    @Test
    public void commit() {
        Model actualModelManager = new ModelManager();

        actualModelManager.addPatient(TypicalPatients.ALICE);
        assertTrue(actualModelManager.hasPatient(TypicalPatients.ALICE));
        actualModelManager.commit(new DummyMutatorCommand("add Alice"));
        assertTrue(actualModelManager.hasPatient(TypicalPatients.ALICE));

        actualModelManager.deletePatient(TypicalPatients.ALICE);
        assertFalse(actualModelManager.hasPatient(TypicalPatients.ALICE));
        actualModelManager.commit(new DummyMutatorCommand("delete Alice"));
        assertFalse(actualModelManager.hasPatient(TypicalPatients.ALICE));
    }

    @Test
    public void getFilteredPatientList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> modelManager
                .getFilteredPatientList().remove(0));
    }

    @Test
    public void equals() {
        PatientBook patientBook = new PatientBookBuilder().withPatient(TypicalPatients.ALICE)
                .withPatient(TypicalPatients.BENSON).build();
        PatientBook differentPatientBook = new PatientBook();
        UserPrefs userPrefs = new UserPrefs();
        AppointmentBook appointmentBook = new AppointmentBookBuilder()
                .withAppointment(TypicalAppointments.ALICE_APPT).build();
        AppointmentBook differentAppointmentBook = new AppointmentBook();

        // same values -> returns true
        modelManager = new ModelManager(patientBook, userPrefs, appointmentBook);
        ModelManager modelManagerCopy = new ModelManager(patientBook, userPrefs, appointmentBook);

        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different patientBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentPatientBook, userPrefs, appointmentBook)));

        // different appointmentBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(patientBook, userPrefs, differentAppointmentBook)));

        // different filteredList -> returns false
        String[] keywords = TypicalPatients.ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPatientList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(patientBook, userPrefs, appointmentBook)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPatientList(Model.PREDICATE_SHOW_ALL_PATIENTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setPatientBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(patientBook, differentUserPrefs, appointmentBook)));
    }
}
