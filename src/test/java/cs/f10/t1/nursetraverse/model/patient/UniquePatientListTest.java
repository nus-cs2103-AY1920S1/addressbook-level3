package cs.f10.t1.nursetraverse.model.patient;

import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static cs.f10.t1.nursetraverse.testutil.Assert.assertThrows;
import static cs.f10.t1.nursetraverse.testutil.TypicalPatients.ALICE;
import static cs.f10.t1.nursetraverse.testutil.TypicalPatients.BOB;
import static cs.f10.t1.nursetraverse.testutil.TypicalPatients.CARL;
import static cs.f10.t1.nursetraverse.testutil.TypicalPatients.DANIEL;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.model.patient.exceptions.DuplicatePatientException;
import cs.f10.t1.nursetraverse.model.patient.exceptions.PatientNotFoundException;
import cs.f10.t1.nursetraverse.testutil.PatientBuilder;

public class UniquePatientListTest {

    private final UniquePatientList uniquePatientList = new UniquePatientList();

    @Test
    public void contains_nullPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePatientList.contains(null));
    }

    @Test
    public void contains_patientNotInList_returnsFalse() {
        assertFalse(uniquePatientList.contains(ALICE));
    }

    @Test
    public void contains_patientInList_returnsTrue() {
        uniquePatientList.add(ALICE);
        assertTrue(uniquePatientList.contains(ALICE));
    }

    @Test
    public void contains_patientWithSameIdentityFieldsInList_returnsTrue() {
        uniquePatientList.add(ALICE);
        Patient editedAlice = new PatientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).withPreviousVisits()
                .build();
        assertTrue(uniquePatientList.contains(editedAlice));
    }

    @Test
    public void add_nullPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePatientList.add(null));
    }

    @Test
    public void add_duplicatePatient_throwsDuplicatePatientException() {
        uniquePatientList.add(ALICE);
        assertThrows(DuplicatePatientException.class, () -> uniquePatientList.add(ALICE));
    }

    @Test
    public void setPatient_nullTargetPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePatientList.setPatient(null, ALICE));
    }

    @Test
    public void setPatient_nullEditedPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePatientList.setPatient(ALICE, null));
    }

    @Test
    public void setPatient_targetPatientNotInList_throwsPatientNotFoundException() {
        assertThrows(PatientNotFoundException.class, () -> uniquePatientList.setPatient(ALICE, ALICE));
    }

    @Test
    public void setPatient_editedPatientIsSamePatient_success() {
        uniquePatientList.add(ALICE);
        uniquePatientList.setPatient(ALICE, ALICE);
        UniquePatientList expectedUniquePatientList = new UniquePatientList();
        expectedUniquePatientList.add(ALICE);
        assertEquals(expectedUniquePatientList, uniquePatientList);
    }

    @Test
    public void setPatient_editedPatientHasSameIdentity_success() {
        uniquePatientList.add(ALICE);
        Patient editedAlice = new PatientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniquePatientList.setPatient(ALICE, editedAlice);
        UniquePatientList expectedUniquePatientList = new UniquePatientList();
        expectedUniquePatientList.add(editedAlice);
        assertEquals(expectedUniquePatientList, uniquePatientList);
    }

    @Test
    public void setPatient_editedPatientHasDifferentIdentity_success() {
        uniquePatientList.add(ALICE);
        uniquePatientList.setPatient(ALICE, BOB);
        UniquePatientList expectedUniquePatientList = new UniquePatientList();
        expectedUniquePatientList.add(BOB);
        assertEquals(expectedUniquePatientList, uniquePatientList);
    }

    @Test
    public void setPatient_editedPatientHasNonUniqueIdentity_throwsDuplicatePatientException() {
        uniquePatientList.add(ALICE);
        uniquePatientList.add(BOB);
        assertThrows(DuplicatePatientException.class, () -> uniquePatientList.setPatient(ALICE, BOB));
    }

    @Test
    public void remove_nullPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePatientList.remove(null));
    }

    @Test
    public void remove_patientDoesNotExist_throwsPatientNotFoundException() {
        assertThrows(PatientNotFoundException.class, () -> uniquePatientList.remove(ALICE));
    }

    @Test
    public void remove_existingPatient_removesPatient() {
        uniquePatientList.add(ALICE);
        uniquePatientList.remove(ALICE);
        UniquePatientList expectedUniquePatientList = new UniquePatientList();
        assertEquals(expectedUniquePatientList, uniquePatientList);
    }

    @Test
    public void setPatients_nullUniquePatientList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePatientList.setPatients((UniquePatientList) null));
    }

    @Test
    public void setPatients_uniquePatientList_replacesOwnListWithProvidedUniquePatientList() {
        uniquePatientList.add(ALICE);
        UniquePatientList expectedUniquePatientList = new UniquePatientList();
        expectedUniquePatientList.add(BOB);
        uniquePatientList.setPatients(expectedUniquePatientList);
        assertEquals(expectedUniquePatientList, uniquePatientList);
    }

    @Test
    public void setPatients_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePatientList.setPatients((List<Patient>) null));
    }

    @Test
    public void setPatients_list_replacesOwnListWithProvidedList() {
        uniquePatientList.add(ALICE);
        List<Patient> patientList = Collections.singletonList(BOB);
        uniquePatientList.setPatients(patientList);
        UniquePatientList expectedUniquePatientList = new UniquePatientList();
        expectedUniquePatientList.add(BOB);
        assertEquals(expectedUniquePatientList, uniquePatientList);
    }

    @Test
    public void setPatients_listWithDuplicatePatients_throwsDuplicatePatientException() {
        List<Patient> listWithDuplicatePatients = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePatientException.class, () -> uniquePatientList.setPatients(listWithDuplicatePatients));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniquePatientList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void indexOf() {
        uniquePatientList.add(ALICE);
        uniquePatientList.add(BOB);
        uniquePatientList.add(CARL);
        assertThrows(NullPointerException.class, () -> uniquePatientList.indexOf(null));
        assertEquals(-1, uniquePatientList.indexOf(DANIEL));
        assertEquals(0, uniquePatientList.indexOf(ALICE));
        assertEquals(2, uniquePatientList.indexOf(CARL));
    }

    @Test
    public void getByIndex() {
        assertEquals(Optional.empty(), uniquePatientList.getByIndex(-1));
        assertEquals(Optional.empty(), uniquePatientList.getByIndex(0));
        uniquePatientList.add(ALICE);
        assertEquals(Optional.empty(), uniquePatientList.getByIndex(-1));
        assertEquals(ALICE, uniquePatientList.getByIndex(0).get());
        assertEquals(Optional.empty(), uniquePatientList.getByIndex(1));
        uniquePatientList.add(BOB);
        assertEquals(ALICE, uniquePatientList.getByIndex(0).get());
        assertEquals(BOB, uniquePatientList.getByIndex(1).get());
        uniquePatientList.add(CARL);
        assertEquals(ALICE, uniquePatientList.getByIndex(0).get());
        assertEquals(BOB, uniquePatientList.getByIndex(1).get());
        assertEquals(CARL, uniquePatientList.getByIndex(2).get());
        uniquePatientList.add(DANIEL);
        uniquePatientList.remove(ALICE);
        assertEquals(BOB, uniquePatientList.getByIndex(0).get());
        assertEquals(CARL, uniquePatientList.getByIndex(1).get());
        assertEquals(DANIEL, uniquePatientList.getByIndex(2).get());
        uniquePatientList.remove(CARL);
        assertEquals(BOB, uniquePatientList.getByIndex(0).get());
        assertEquals(DANIEL, uniquePatientList.getByIndex(1).get());
    }

    @Test
    public void iterator() {
        uniquePatientList.add(ALICE);
        uniquePatientList.add(CARL);
        uniquePatientList.add(DANIEL);
        uniquePatientList.add(BOB);
        Iterator<Patient> it = uniquePatientList.iterator();
        assertTrue(it.hasNext());
        assertEquals(it.next(), ALICE);
        assertEquals(it.next(), CARL);
        assertEquals(it.next(), DANIEL);
        assertEquals(it.next(), BOB);
        assertFalse(it.hasNext());
    }

    @Test
    public void patientNotFoundException() {
        uniquePatientList.add(ALICE);
        uniquePatientList.add(CARL);
        assertDoesNotThrow(() -> uniquePatientList.setPatient(ALICE, BOB));
        assertDoesNotThrow(() -> uniquePatientList.remove(CARL));
        assertThrows(PatientNotFoundException.class, () -> uniquePatientList.setPatient(ALICE, BOB));
        assertThrows(PatientNotFoundException.class, () -> uniquePatientList.remove(ALICE));
    }
}
