package organice.logic.commands;

import static java.util.Objects.requireNonNull;
import static javafx.collections.FXCollections.observableArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.logic.commands.CommandTestUtil.COMPATIBLE_TISSUE_TYPE_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_IRENE;

import static organice.model.person.BloodType.BLOODTYPE_AB_MINUS;
import static organice.model.person.BloodType.BLOODTYPE_AB_PLUS;
import static organice.model.person.BloodType.BLOODTYPE_A_MINUS;
import static organice.model.person.BloodType.BLOODTYPE_A_PLUS;
import static organice.model.person.BloodType.BLOODTYPE_B_MINUS;
import static organice.model.person.BloodType.BLOODTYPE_B_PLUS;
import static organice.model.person.BloodType.BLOODTYPE_O_MINUS;
import static organice.model.person.BloodType.BLOODTYPE_O_PLUS;
import static organice.testutil.Assert.assertThrows;
import static organice.testutil.TypicalPersons.DONOR_IRENE_DONOR;
import static organice.testutil.TypicalPersons.PATIENT_BOB;
import static organice.testutil.TypicalPersons.PATIENT_IRENE;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import organice.model.AddressBook;
import organice.model.ModelStub;
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
    //Donors of positive blood types
    static final Donor DONOR_A_PLUS = new DonorBuilder(DONOR_IRENE_DONOR).withBloodType(BLOODTYPE_A_PLUS.toString())
            .build();
    static final Donor DONOR_B_PLUS = new DonorBuilder(DONOR_IRENE_DONOR).withBloodType(BLOODTYPE_B_PLUS.toString())
            .build();
    static final Donor DONOR_AB_PLUS = new DonorBuilder(DONOR_IRENE_DONOR).withBloodType(BLOODTYPE_AB_PLUS.toString())
            .build();
    static final Donor DONOR_O_PLUS = new DonorBuilder(DONOR_IRENE_DONOR).withBloodType(BLOODTYPE_O_PLUS.toString())
            .build();
    static final Donor DONOR_SIMILAR_TISSUE = new DonorBuilder(DONOR_IRENE_DONOR)
            .withTissueType(COMPATIBLE_TISSUE_TYPE_IRENE).build();

    //Donors of all types
    static final Donor DONOR_A_MINUS = new DonorBuilder(DONOR_IRENE_DONOR).withBloodType(BLOODTYPE_A_MINUS.toString())
            .build();
    static final Donor DONOR_B_MINUS = new DonorBuilder(DONOR_IRENE_DONOR).withBloodType(BLOODTYPE_B_MINUS.toString())
            .build();
    static final Donor DONOR_AB_MINUS = new DonorBuilder(DONOR_IRENE_DONOR).withBloodType(BLOODTYPE_AB_MINUS.toString())
            .build();
    static final Donor DONOR_O_MINUS = new DonorBuilder(DONOR_IRENE_DONOR).withBloodType(BLOODTYPE_O_MINUS.toString())
            .build();

    //Patient of all positive blood types
    static final Patient PATIENT_A_PLUS = new PatientBuilder(PATIENT_IRENE).withBloodType(BLOODTYPE_A_PLUS.toString())
            .build();
    static final Patient PATIENT_B_PLUS = new PatientBuilder(PATIENT_IRENE).withBloodType(BLOODTYPE_B_PLUS.toString())
            .build();
    static final Patient PATIENT_AB_PLUS = new PatientBuilder(PATIENT_IRENE).withBloodType(BLOODTYPE_AB_PLUS.toString())
            .build();
    static final Patient PATIENT_O_PLUS = new PatientBuilder(PATIENT_IRENE).withBloodType(BLOODTYPE_O_PLUS.toString())
            .build();

    //Patient of all negative blood types
    static final Patient PATIENT_A_MINUS = new PatientBuilder(PATIENT_IRENE).withBloodType(BLOODTYPE_A_MINUS.toString())
            .build();
    static final Patient PATIENT_B_MINUS = new PatientBuilder(PATIENT_IRENE).withBloodType(BLOODTYPE_B_MINUS.toString())
            .build();
    static final Patient PATIENT_AB_MINUS = new PatientBuilder(PATIENT_IRENE)
            .withBloodType(BLOODTYPE_AB_MINUS.toString()).build();
    static final Patient PATIENT_O_MINUS = new PatientBuilder(PATIENT_IRENE).withBloodType(BLOODTYPE_O_MINUS.toString())
            .build();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MatchCommand(null));
    }

    @Test
    public void execute_patientExists_haveMatch() throws Exception {
        ModelStubWithDonor modelStub = new ModelStubWithDonor(PATIENT_IRENE, DONOR_IRENE_DONOR);
        String validNric = VALID_NRIC_PATIENT_IRENE;

        CommandResult commandResult = new MatchCommand(validNric).execute(modelStub);
        ObservableList<Person> listOfDonors = modelStub.getDisplayedPersonList();

        String expectedMessage = String.format(MatchCommand.MESSAGE_SUCCESS_MATCH_PATIENT, listOfDonors.size(),
                VALID_NAME_PATIENT_IRENE, VALID_NRIC_PATIENT_IRENE);
        assertEquals(String.format(expectedMessage, validNric), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(DONOR_IRENE_DONOR), listOfDonors);
    }

    @Test
    public void execute_patientExists_noMatch() throws Exception {
        ModelStubWithoutDonor modelStub = new ModelStubWithoutDonor(PATIENT_IRENE);
        String validNric = VALID_NRIC_PATIENT_IRENE;

        CommandResult commandResult = new MatchCommand(validNric).execute(modelStub);
        ObservableList<Person> listOfDonors = modelStub.getDisplayedPersonList();

        String expectedMessage = String.format(MatchCommand.MESSAGE_NO_MATCHES, VALID_NAME_PATIENT_IRENE,
                VALID_NRIC_PATIENT_IRENE);
        assertEquals(String.format(expectedMessage, validNric), commandResult.getFeedbackToUser());
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
        //100% match and same blood type
        boolean matchResult = MatchCommand.match(DONOR_IRENE_DONOR, PATIENT_IRENE);
        assertTrue(matchResult);

        //donor with 4/6 matching tissues and same blood type.
        matchResult = MatchCommand.match(DONOR_SIMILAR_TISSUE, PATIENT_IRENE);
        assertTrue(matchResult);

        //donor with blood type A+ can donate to patients of blood types A+ and AB+
        assertTrue(MatchCommand.match(DONOR_A_PLUS, PATIENT_A_PLUS));
        assertTrue(MatchCommand.match(DONOR_A_MINUS, PATIENT_AB_PLUS));

        //donor with blood type B matches patient with blood type B and AB
        assertTrue(MatchCommand.match(DONOR_B_MINUS, PATIENT_B_MINUS));
        assertTrue(MatchCommand.match(DONOR_B_MINUS, PATIENT_AB_PLUS));

        //donor with blood type AB matches patient with blood type AB
        assertTrue(MatchCommand.match(DONOR_AB_MINUS, PATIENT_AB_PLUS));

        //donor with blood type O matches patient with blood type A, B, AB and O
        assertTrue(MatchCommand.match(DONOR_O_MINUS, PATIENT_A_PLUS));
        assertTrue(MatchCommand.match(DONOR_O_MINUS, PATIENT_B_MINUS));
        assertTrue(MatchCommand.match(DONOR_O_PLUS, PATIENT_AB_PLUS));
        assertTrue(MatchCommand.match(DONOR_O_PLUS, PATIENT_O_PLUS));
    }

    @Test
    public void match_isNotSuccessfulMatch() throws Exception {
        MatchCommand matchCommand = new MatchCommand(VALID_NRIC_PATIENT_IRENE);

        Donor differentBloodType = new DonorBuilder(DONOR_IRENE_DONOR).withBloodType("B+").build();
        boolean matchResult = matchCommand.match(differentBloodType, PATIENT_IRENE);
        assertFalse(matchResult); //100% tissue match and different blood type

        //donor with 4/6 matching tissues and different blood type.
        //Hardcoded because there is no way to change tissue types in a more elegant manner
        Donor someMatchingTissues = new DonorBuilder(DONOR_IRENE_DONOR).withTissueType(COMPATIBLE_TISSUE_TYPE_IRENE)
                .withBloodType("B+").build();
        matchResult = matchCommand.match(someMatchingTissues, PATIENT_IRENE);
        assertFalse(matchResult);

        //donor with blood type A do not match patients with blood type B and O
        assertFalse(matchCommand.match(DONOR_A_PLUS, PATIENT_B_MINUS));
        assertFalse(matchCommand.match(DONOR_A_PLUS, PATIENT_O_MINUS));
        assertFalse(matchCommand.match(DONOR_A_PLUS, PATIENT_A_MINUS));

        //donor with blood type B do not match patients with blood type A and O
        assertFalse(matchCommand.match(DONOR_B_MINUS, PATIENT_A_MINUS));
        assertFalse(matchCommand.match(DONOR_B_MINUS, PATIENT_O_MINUS));

        //donor with blood type AB do not match patients with blood type A, B and O
        assertFalse(matchCommand.match(DONOR_AB_PLUS, PATIENT_A_MINUS));
        assertFalse(matchCommand.match(DONOR_AB_MINUS, PATIENT_B_MINUS));
        assertFalse(matchCommand.match(DONOR_AB_MINUS, PATIENT_O_MINUS));
        assertFalse(matchCommand.match(DONOR_AB_PLUS, PATIENT_AB_MINUS));
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
     * A Model stub that contains a matching donor.
     */
    private class ModelStubWithDonor extends ModelStub {
        private FilteredList<Person> filteredPersons;
        private ObservableList<Person> displayedPersons;
        private ObservableList<Person> listOfMatches;

        ModelStubWithDonor(Patient patient, Donor donor) {
            AddressBook addressBook = new AddressBookBuilder().withPerson(patient).withPerson(donor).build();
            filteredPersons = new FilteredList<>(addressBook.getPersonList());
            setDisplayedPersonList(Arrays.asList(filteredPersons.toArray(Person[]::new)));
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
        public ObservableList<Person> getDisplayedPersonList() {
            return displayedPersons;
        }

        @Override
        public void setDisplayedPersonList(List<Person> personList) {
            displayedPersons = FXCollections.observableList(personList);
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            filteredPersons.setPredicate(predicate);
            setDisplayedPersonList(Arrays.asList(filteredPersons.toArray(Person[]::new)));
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return filteredPersons;
        }

        private void addMatchedDonor(MatchedDonor matchedDonor) {
            listOfMatches.add(matchedDonor);
            setDisplayedPersonList(Arrays.asList(listOfMatches.toArray(Person[]::new)));
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
            setDisplayedPersonList(Arrays.asList(listOfMatches.toArray(Person[]::new)));
        }

        @Override
        public void removeMatches() {
            listOfMatches = observableArrayList();
        }

        @Override
        public int numberOfMatches() {
            return listOfMatches.size();
        }
    }

    /**

     */
    private class ModelStubWithoutDonor extends ModelStub {
        private FilteredList<Person> filteredPersons;
        private ObservableList<Person> displayedPersons;
        private ObservableList<Person> listOfMatches;

        public ModelStubWithoutDonor(Patient patient) {
            AddressBook addressBook = new AddressBookBuilder().withPerson(patient).build();
            filteredPersons = new FilteredList<>(addressBook.getPersonList());
            setDisplayedPersonList(Arrays.asList(filteredPersons.toArray(Person[]::new)));
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
        public ObservableList<Person> getDisplayedPersonList() {
            return displayedPersons;
        }

        @Override
        public void setDisplayedPersonList(List<Person> personList) {
            displayedPersons = FXCollections.observableList(personList);
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            filteredPersons.setPredicate(predicate);
            setDisplayedPersonList(Arrays.asList(filteredPersons.toArray(Person[]::new)));
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return filteredPersons;
        }

        private void addMatchedDonor(MatchedDonor matchedDonor) {
            listOfMatches.add(matchedDonor);
            setDisplayedPersonList(Arrays.asList(listOfMatches.toArray(Person[]::new)));
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
            setDisplayedPersonList(Arrays.asList(listOfMatches.toArray(Person[]::new)));
        }

        @Override
        public void removeMatches() {
            listOfMatches = observableArrayList();
            setDisplayedPersonList(Arrays.asList(listOfMatches.toArray(Person[]::new)));
        }

        @Override
        public int numberOfMatches() {
            return listOfMatches.size();
        }
    }
}
