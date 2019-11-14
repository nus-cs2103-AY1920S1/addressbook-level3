package cs.f10.t1.nursetraverse.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.commons.util.CopyUtil;
import cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.model.patient.exceptions.DuplicatePatientException;
import cs.f10.t1.nursetraverse.testutil.Assert;
import cs.f10.t1.nursetraverse.testutil.PatientBuilder;
import cs.f10.t1.nursetraverse.testutil.TypicalPatients;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

public class PatientBookTest {

    private final PatientBook patientBook = new PatientBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), patientBook.getPatientList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> patientBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyPatientBook_replacesData() {
        PatientBook newData = TypicalPatients.getTypicalPatientBook();
        patientBook.resetData(newData);
        assertEquals(newData, patientBook);
    }

    @Test
    public void resetData_withDuplicatePatients_throwsDuplicatePatientException() {
        // Two patients with the same identity fields
        Patient editedAlice = new PatientBuilder(TypicalPatients.ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        List<Patient> newPatients = Arrays.asList(TypicalPatients.ALICE, editedAlice);
        PatientBookStub newData = new PatientBookStub(newPatients);

        Assert.assertThrows(DuplicatePatientException.class, () -> patientBook.resetData(newData));
    }

    @Test
    public void hasPatient_nullPatient_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> patientBook.hasPatient(null));
    }

    @Test
    public void hasPatient_patientNotInPatientBook_returnsFalse() {
        assertFalse(patientBook.hasPatient(TypicalPatients.ALICE));
    }

    @Test
    public void hasPatient_patientInPatientBook_returnsTrue() {
        patientBook.addPatient(TypicalPatients.ALICE);
        assertTrue(patientBook.hasPatient(TypicalPatients.ALICE));
    }

    @Test
    public void hasPatient_patientWithSameIdentityFieldsInPatientBook_returnsTrue() {
        patientBook.addPatient(TypicalPatients.ALICE);
        Patient editedAlice = new PatientBuilder(TypicalPatients.ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        assertTrue(patientBook.hasPatient(editedAlice));
    }

    @Test
    public void getPatientList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> patientBook.getPatientList().remove(0));
    }

    @Test
    public void deepCopy() {
        PatientBook copy = patientBook.deepCopy();

        assertNotSame(copy, patientBook);
        assertEquals(copy, patientBook);
    }

    @Test
    public void deepCopy_changes_areIndependent() {
        PatientBook copy = patientBook.deepCopy();
        copy.addPatient(TypicalPatients.ALICE);

        assertNotEquals(copy, patientBook);
        assertTrue(copy.hasPatient(TypicalPatients.ALICE));
        assertFalse(patientBook.hasPatient(TypicalPatients.ALICE));
    }

    @Test
    public void hashCode_noError() {
        assertDoesNotThrow(patientBook::hashCode);

        PatientBook typicalPatientBook = TypicalPatients.getTypicalPatientBook();
        assertDoesNotThrow(typicalPatientBook::hashCode);
    }

    /**
     * A stub ReadOnlyPatientBook whose patients list can violate interface constraints.
     */
    private static class PatientBookStub implements ReadOnlyPatientBook {
        private final ObservableList<Patient> patients = FXCollections.observableArrayList();

        PatientBookStub(Collection<Patient> patients) {
            this.patients.setAll(patients);
        }

        @Override
        public ObservableList<Patient> getPatientList() {
            return patients;
        }

        @Override
        public Pair<Integer, Integer> getIndexPairOfOngoingPatientAndVisit() {
            return new Pair<>(-1, -1);
        }

        @Override
        public PatientBookStub deepCopy() {
            return new PatientBookStub(CopyUtil.deepCopyOfObservableList(patients));
        }
    }

}
