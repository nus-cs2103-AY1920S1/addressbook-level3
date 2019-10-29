package organice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_IRENE;
import static organice.testutil.Assert.assertThrows;
import static organice.testutil.TypicalPersons.DOCTOR_ALICE;
import static organice.testutil.TypicalPersons.DONOR_ELLE;
import static organice.testutil.TypicalPersons.DONOR_GEORGE;
import static organice.testutil.TypicalPersons.PATIENT_BOB;
import static organice.testutil.TypicalPersons.PATIENT_CARL;
import static organice.testutil.TypicalPersons.PATIENT_IRENE;
import static organice.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import organice.model.person.Donor;
import organice.model.person.Nric;
import organice.model.person.Patient;
import organice.model.person.Person;
import organice.model.person.exceptions.DuplicatePersonException;
import organice.model.person.exceptions.PersonNotFoundException;
import organice.testutil.DoctorBuilder;
import organice.testutil.DonorBuilder;
import organice.testutil.PatientBuilder;
import organice.testutil.PersonBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(DOCTOR_ALICE).withNric("S1532142A").build();
        List<Person> newPersons = Arrays.asList(DOCTOR_ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        Person person = null;
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(person));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(DOCTOR_ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(DOCTOR_ALICE);
        assertTrue(addressBook.hasPerson(DOCTOR_ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(DOCTOR_ALICE);
        Person editedAlice = new PersonBuilder(DOCTOR_ALICE).withNric("S1532142A").build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void hasPatient_patientInAddressBook_returnsTrue() {
        addressBook.addPerson(PATIENT_BOB);
        assertTrue(addressBook.hasPatient(PATIENT_BOB.getNric()));
    }

    @Test
    public void hasPatient_patientWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(PATIENT_CARL);
        Patient editedCarl = new PatientBuilder(PATIENT_CARL).withName(VALID_NAME_PATIENT_IRENE).build();
        assertTrue(addressBook.hasPatient(editedCarl.getNric()));
    }

    @Test
    public void hasPatient_patientNotInAddressBook_returnsFalse() {
        addressBook.addPerson(PATIENT_BOB);
        assertFalse(addressBook.hasPatient(PATIENT_CARL.getNric()));
    }

    @Test
    public void hasPatient_personWithSameNricButNotPatient_returnsFalse() {
        Donor editedElle = new DonorBuilder(DONOR_ELLE).withNric(VALID_NRIC_PATIENT_IRENE).build();
        addressBook.addPerson(editedElle);

        Nric ireneNric = new Nric(VALID_NRIC_PATIENT_IRENE);
        assertFalse(addressBook.hasPatient(ireneNric));
    }

    @Test
    public void getPatient_patientInAddressBook_returnsPatient() {
        addressBook.addPerson(PATIENT_BOB);
        addressBook.addPerson(DONOR_GEORGE);

        assertEquals(PATIENT_BOB, addressBook.getPatient(PATIENT_BOB.getNric()));
    }
    @Test
    public void getPatient_patientNotInAddressBook_throwsPersonNotFoundException() {
        addressBook.addPerson(PATIENT_BOB);
        addressBook.addPerson(DONOR_GEORGE);

        assertThrows(PersonNotFoundException.class, () -> addressBook.getPatient(PATIENT_IRENE.getNric()));
    }

    @Test
    public void hasDoctor_nullDoctor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasDoctor(null));
    }

    @Test
    public void hasDoctor_doctorNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasDoctor(DOCTOR_ALICE.getNric()));
    }

    @Test
    public void hasDoctor_doctorInAddressBook_returnsTrue() {
        addressBook.addPerson(DOCTOR_ALICE);
        assertTrue(addressBook.hasDoctor(DOCTOR_ALICE.getNric()));
    }

    @Test
    public void hasDoctor_doctorWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(DOCTOR_ALICE);
        Person editedAlice = new DoctorBuilder(DOCTOR_ALICE).withNric(DOCTOR_ALICE.getNric().toString()).build();
        assertTrue(addressBook.hasDoctor(editedAlice.getNric()));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
