package cs.f10.t1.nursetraverse.model.patient;

import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static cs.f10.t1.nursetraverse.model.datetime.EndDateTimeTest.VALID_END_DATE_TIME;
import static cs.f10.t1.nursetraverse.testutil.Assert.assertThrows;
import static cs.f10.t1.nursetraverse.testutil.TypicalAppointments.getTypicalAppointmentBook;
import static cs.f10.t1.nursetraverse.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static cs.f10.t1.nursetraverse.testutil.TypicalPatients.ALICE;
import static cs.f10.t1.nursetraverse.testutil.TypicalPatients.BOB;
import static cs.f10.t1.nursetraverse.testutil.TypicalPatients.getTypicalPatientBook;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.logic.commands.visit.BeginVisitCommand;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.ModelManager;
import cs.f10.t1.nursetraverse.model.UserPrefs;
import cs.f10.t1.nursetraverse.model.visit.Visit;
import cs.f10.t1.nursetraverse.model.visit.exceptions.VisitNotFoundException;
import cs.f10.t1.nursetraverse.testutil.PatientBuilder;

public class PatientTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Patient patient = new PatientBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> patient.getTags().remove(0));
    }

    @Test
    public void isSamePatient() {
        // same object -> returns true
        assertTrue(ALICE.isSamePatient(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePatient(null));

        // different phone and email -> returns false
        Patient editedAlice = new PatientBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSamePatient(editedAlice));

        // different name -> returns false
        editedAlice = new PatientBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePatient(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new PatientBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePatient(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new PatientBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePatient(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new PatientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePatient(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Patient aliceCopy = new PatientBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different patient -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Patient editedAlice = new PatientBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PatientBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PatientBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PatientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PatientBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void visitIntegration() {
        Model model = new ModelManager(getTypicalPatientBook(), new UserPrefs(), getTypicalAppointmentBook());
        Patient visitedPatient = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        assertEquals(Optional.empty(), visitedPatient.getVisitByIndex(-1));
        assertNotEquals(Optional.empty(), visitedPatient.getVisitByIndex(0));
        assertEquals(Optional.empty(), visitedPatient.getVisitByIndex(5));
        BeginVisitCommand beginVisitCommand = new BeginVisitCommand(INDEX_FIRST_PATIENT);
        assertDoesNotThrow(() -> beginVisitCommand.execute(model));
        assertDoesNotThrow(() -> visitedPatient.removeVisit(visitedPatient.getVisitByIndex(0).get(), model));
        Visit visit = visitedPatient.getVisitByIndex(1).get();
        Visit visitNotInData = new Visit(visit.getRemark(), visit.getStartDateTime(), VALID_END_DATE_TIME,
                visit.getVisitTasks(), visit.getPatient());
        assertDoesNotThrow(() -> visitedPatient.removeVisit(visit, model));
        assertThrows(IllegalArgumentException.class, () -> visitedPatient.removeVisit(visitNotInData, model));
        assertThrows(VisitNotFoundException.class, () -> visitedPatient.updateVisit(visitNotInData, visit));
    }
}
