package organice.logic.commands;

import static java.util.Objects.requireNonNull;
import static javafx.collections.FXCollections.observableArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.logic.commands.CommandTestUtil.COMPATIBLE_TISSUE_TYPE_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_IRENE;
import static organice.model.person.BloodType.BLOODTYPE_A;
import static organice.model.person.BloodType.BLOODTYPE_AB;
import static organice.model.person.BloodType.BLOODTYPE_B;
import static organice.model.person.BloodType.BLOODTYPE_O;
import static organice.testutil.Assert.assertThrows;
import static organice.testutil.TypicalPersons.DONOR_IRENE_DONOR;
import static organice.testutil.TypicalPersons.PATIENT_BOB;
import static organice.testutil.TypicalPersons.PATIENT_IRENE;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import organice.commons.core.GuiSettings;
import organice.model.AddressBook;
import organice.model.Model;
import organice.model.ReadOnlyAddressBook;
import organice.model.ReadOnlyUserPrefs;
import organice.model.person.Donor;
import organice.model.person.MatchedDonor;
import organice.model.person.Nric;
import organice.model.person.Patient;
import organice.model.person.Person;
import organice.model.person.exceptions.PersonNotFoundException;
import organice.testutil.AddressBookBuilder;
import organice.testutil.DonorBuilder;
import organice.testutil.PatientBuilder;

public class MatchCommandTest {
    //Donors of all types
    static final Donor DONOR_A = new DonorBuilder(DONOR_IRENE_DONOR).withBloodType(BLOODTYPE_A.toString()).build();
    static final Donor DONOR_B = new DonorBuilder(DONOR_IRENE_DONOR).withBloodType(BLOODTYPE_B.toString()).build();
    static final Donor DONOR_AB = new DonorBuilder(DONOR_IRENE_DONOR).withBloodType(BLOODTYPE_AB.toString()).build();
    static final Donor DONOR_O = new DonorBuilder(DONOR_IRENE_DONOR).withBloodType(BLOODTYPE_O.toString()).build();
    static final Donor DONOR_SIMILAR_TISSUE = new DonorBuilder(DONOR_IRENE_DONOR)
            .withTissueType(COMPATIBLE_TISSUE_TYPE_IRENE).build();

    //Patient of all types
    static final Patient PATIENT_A = new PatientBuilder(PATIENT_IRENE).withBloodType(BLOODTYPE_A.toString()).build();
    static final Patient PATIENT_B = new PatientBuilder(PATIENT_IRENE).withBloodType(BLOODTYPE_B.toString()).build();
    static final Patient PATIENT_AB = new PatientBuilder(PATIENT_IRENE).withBloodType(BLOODTYPE_AB.toString()).build();
    static final Patient PATIENT_O = new PatientBuilder(PATIENT_IRENE).withBloodType(BLOODTYPE_O.toString()).build();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MatchCommand(null));
    }

    @Test
    public void execute_patientExists_haveMatch() throws Exception {
        ModelStubWithDonor modelStub = new ModelStubWithDonor(PATIENT_IRENE, DONOR_IRENE_DONOR);
        String validNric = VALID_NRIC_PATIENT_IRENE;

        CommandResult commandResult = new MatchCommand(validNric).execute(modelStub);
        ObservableList<Person> listOfDonors = modelStub.getMatchList();

        assertEquals(String.format(MatchCommand.MESSAGE_SUCCESS, validNric), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(DONOR_IRENE_DONOR), listOfDonors);
    }

    @Test
    public void execute_patientExists_noMatch() throws Exception {
        ModelStubWithoutDonor modelStub = new ModelStubWithoutDonor(PATIENT_IRENE);
        String validNric = VALID_NRIC_PATIENT_IRENE;

        CommandResult commandResult = new MatchCommand(validNric).execute(modelStub);
        ObservableList<Person> listOfDonors = modelStub.getMatchList();

        assertEquals(String.format(MatchCommand.MESSAGE_SUCCESS, validNric), commandResult.getFeedbackToUser());
        assertEquals(listOfDonors.size(), 0); //assert that there is no donor
    }

    @Test
    public void execute_patientDoesNotExist_throwsCommandException() throws Exception {
        ModelStubWithoutDonor modelStub = new ModelStubWithoutDonor(PATIENT_BOB);

        String validNric = VALID_NRIC_PATIENT_IRENE;

        CommandResult commandResult = new MatchCommand(validNric).execute(modelStub);
        assertEquals(String.format(MatchCommand.MESSAGE_PERSON_NOT_FOUND, validNric),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void match_isSuccessfulMatch() throws Exception {
        MatchCommand matchCommand = new MatchCommand(VALID_NRIC_PATIENT_IRENE);

        boolean matchResult = matchCommand.match(DONOR_IRENE_DONOR, PATIENT_IRENE);
        assertTrue(matchResult); //100% match and same blood type

        //donor with 4/6 matching tissues and same blood type.
        matchResult = matchCommand.match(DONOR_SIMILAR_TISSUE, PATIENT_IRENE);
        assertTrue(matchResult);

        //donor with blood type A matches patient with blood type A and AB
        assertEquals(matchCommand.match(DONOR_A, PATIENT_A), true);
        assertEquals(matchCommand.match(DONOR_A, PATIENT_AB), true);

        //donor with blood type B matches patient with blood type B and AB
        assertEquals(matchCommand.match(DONOR_B, PATIENT_B), true);
        assertEquals(matchCommand.match(DONOR_B, PATIENT_AB), true);

        //donor with blood type AB matches patient with blood type AB
        assertEquals(matchCommand.match(DONOR_AB, PATIENT_AB), true);

        //donor with blood type O matches patient with blood type A, B, AB and O
        assertEquals(matchCommand.match(DONOR_O, PATIENT_A), true);
        assertEquals(matchCommand.match(DONOR_O, PATIENT_B), true);
        assertEquals(matchCommand.match(DONOR_O, PATIENT_AB), true);
        assertEquals(matchCommand.match(DONOR_O, PATIENT_O), true);
    }

    @Test
    public void match_isNotSuccessfulMatch() throws Exception {
        MatchCommand matchCommand = new MatchCommand(VALID_NRIC_PATIENT_IRENE);

        Donor differentBloodType = new DonorBuilder(DONOR_IRENE_DONOR).withBloodType("B").build();
        boolean matchResult = matchCommand.match(differentBloodType, PATIENT_IRENE);
        assertFalse(matchResult); //100% tissue match and different blood type

        //donor with 4/6 matching tissues and different blood type.
        //Hardcoded because there is no way to change tissue types in a more elegant manner
        Donor someMatchingTissues = new DonorBuilder(DONOR_IRENE_DONOR).withTissueType(COMPATIBLE_TISSUE_TYPE_IRENE)
                .withBloodType("B").build();
        matchResult = matchCommand.match(someMatchingTissues, PATIENT_IRENE);
        assertFalse(matchResult);

        //donor with blood type A do not match patients with blood type B and O
        assertEquals(matchCommand.match(DONOR_A, PATIENT_B), false);
        assertEquals(matchCommand.match(DONOR_A, PATIENT_O), false);

        //donor with blood type B do not match patients with blood type A and P
        assertEquals(matchCommand.match(DONOR_B, PATIENT_A), false);
        assertEquals(matchCommand.match(DONOR_B, PATIENT_O), false);

        //donor with blood type AB do not match patients with blood type A, B and O
        assertEquals(matchCommand.match(DONOR_AB, PATIENT_A), false);
        assertEquals(matchCommand.match(DONOR_AB, PATIENT_B), false);
        assertEquals(matchCommand.match(DONOR_AB, PATIENT_O), false);
    }

    @Test
    public void equals() {
        MatchCommand matchIreneCommand = new MatchCommand(VALID_NRIC_PATIENT_IRENE);
        MatchCommand matchBobCommand = new MatchCommand(VALID_NRIC_PATIENT_BOB);

        AddCommand addIreneCommand = new AddCommand(PATIENT_IRENE);
        // same object -> returns true
        assertTrue(matchIreneCommand.equals(matchIreneCommand));

        // same values -> returns true
        MatchCommand matchIreneCommandCopy = new MatchCommand(VALID_NRIC_PATIENT_IRENE);
        assertTrue(matchIreneCommand.equals(matchIreneCommandCopy));

        // different types -> returns false
        assertFalse(matchIreneCommand.equals(1));

        // null -> returns false
        assertFalse(matchIreneCommand.equals(null));

        // different person -> returns false
        assertFalse(matchIreneCommand.equals(matchBobCommand));

        //different command type -> returns false
        assertFalse(matchIreneCommand.equals(addIreneCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDoctor(Nric doctor) {
            return true;
        }

        @Override
        public boolean hasPatient(Nric patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDonor(Nric donor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Patient getPatient(Nric patientNric) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Donor getDonor(Nric donorNric) throws PersonNotFoundException {
            return null;
        }

        @Override
        public ObservableList<Person> getMatchList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void matchDonors(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void matchAllPatients() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeMatches() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a matching donor.
     */
    private class ModelStubWithDonor extends ModelStub {
        private FilteredList<Person> filteredPersons;
        private ObservableList<Person> listOfMatches;

        ModelStubWithDonor(Patient patient, Donor donor) {
            AddressBook addressBook = new AddressBookBuilder().withPerson(patient).withPerson(donor).build();
            filteredPersons = new FilteredList<>(addressBook.getPersonList());
        }

        @Override
        public boolean hasPatient(Nric patientNric) {
            requireNonNull(patientNric);
            return filteredPersons.stream().anyMatch(patient -> patient.getNric().equals(patientNric));
        }

        @Override
        public Patient getPatient(Nric patientNric) throws PersonNotFoundException {
            requireNonNull(patientNric);

            for (Person person : filteredPersons) {
                if (person instanceof Patient && person.getNric().equals(patientNric)) {
                    return (Patient) person;
                }
            }

            throw new PersonNotFoundException();
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            filteredPersons.setPredicate(predicate);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return filteredPersons;
        }

        private void addMatchedDonor(MatchedDonor matchedDonor) {
            listOfMatches.add(matchedDonor);
        }

        @Override
        public void matchDonors(Patient patient) {
            //filter out donors.
            updateFilteredPersonList(PREDICATE_SHOW_ALL_DONORS);

            //if match, create a MatchedDonor and add to the list.
            for (Person person: filteredPersons) {
                if (person instanceof MatchedDonor) {
                    continue;
                }

                boolean isMatch = MatchCommand.match(person, patient);

                if (isMatch) {
                    MatchedDonor matchedDonor = new MatchedDonor((Donor) person);
                    addMatchedDonor(matchedDonor);
                }
            }
        }

        @Override
        public ObservableList<Person> getMatchList() {
            return listOfMatches;
        }

        @Override
        public void removeMatches() {
            listOfMatches = observableArrayList();
        }
    }

    /**

     */
    private class ModelStubWithoutDonor extends ModelStub {
        private FilteredList<Person> filteredPersons;
        private ObservableList<Person> listOfMatches;

        public ModelStubWithoutDonor(Patient patient) {
            AddressBook addressBook = new AddressBookBuilder().withPerson(patient).build();
            filteredPersons = new FilteredList<>(addressBook.getPersonList());
        }

        @Override
        public boolean hasPatient(Nric patientNric) {
            requireNonNull(patientNric);
            return filteredPersons.stream().anyMatch(patient -> patient.getNric().equals(patientNric));
        }

        @Override
        public Patient getPatient(Nric patientNric) throws PersonNotFoundException {
            requireNonNull(patientNric);

            for (Person person : filteredPersons) {
                if (person instanceof Patient && person.getNric().equals(patientNric)) {
                    return (Patient) person;
                }
            }

            throw new PersonNotFoundException();
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            filteredPersons.setPredicate(predicate);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return filteredPersons;
        }

        private void addMatchedDonor(MatchedDonor matchedDonor) {
            listOfMatches.add(matchedDonor);
        }

        @Override
        public void matchDonors(Patient patient) {
            //filter out donors.
            updateFilteredPersonList(PREDICATE_SHOW_ALL_DONORS);

            //if match, create a MatchedDonor and add to the list.
            for (Person person: filteredPersons) {
                if (person instanceof MatchedDonor) {
                    continue;
                }

                boolean isMatch = MatchCommand.match(person, patient);

                if (isMatch) {
                    MatchedDonor matchedDonor = new MatchedDonor((Donor) person);
                    addMatchedDonor(matchedDonor);
                }
            }
        }

        @Override
        public ObservableList<Person> getMatchList() {
            return listOfMatches;
        }

        @Override
        public void removeMatches() {
            listOfMatches = observableArrayList();
        }
    }
}
