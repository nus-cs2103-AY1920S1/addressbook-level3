package organice.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_DONOR_IRENE_DONOR;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_IRENE;
import static organice.testutil.Assert.assertThrows;
import static organice.testutil.TypicalPersons.DONOR_IRENE_DONOR;
import static organice.testutil.TypicalPersons.PATIENT_IRENE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import organice.logic.parser.SortCommandParser;
import organice.model.AddressBook;
import organice.model.Model;
import organice.model.ModelManager;
import organice.model.UserPrefs;
import organice.model.comparator.CompatibilityRateComparator;
import organice.model.comparator.ExpiryDateComparator;
import organice.model.comparator.PriorityComparator;
import organice.model.person.Donor;
import organice.model.person.MatchedDonor;
import organice.model.person.MatchedPatient;
import organice.model.person.Patient;
import organice.model.person.Person;
import organice.testutil.AddressBookBuilder;
import organice.testutil.DonorBuilder;
import organice.testutil.PatientBuilder;

class SortCommandIntegrationTest {
    private static final Donor DONOR_IRENE_DONOR_FULLMATCH = new DonorBuilder(DONOR_IRENE_DONOR).build();
    private static final Donor DONOR_IRENE_DONOR_FIVE_MATCH = new DonorBuilder(DONOR_IRENE_DONOR)
            .withTissueType("1,4,7,10,11,9").withNric(VALID_NRIC_DOCTOR_AMY).build();
    private static final Donor DONOR_IRENE_DONOR_FOUR_MATCH = new DonorBuilder(DONOR_IRENE_DONOR)
            .withTissueType("1,4,7,10,8,9").withNric(VALID_NRIC_DONOR_JOHN).build();

    private static final Donor DONOR_IRENE_DONOR_LATEST = new DonorBuilder(DONOR_IRENE_DONOR)
            .withOrganExpiryDate("23-Jan-2019").build();
    private static final Donor DONOR_IRENE_DONOR_SECOND_LATEST = new DonorBuilder(DONOR_IRENE_DONOR)
            .withOrganExpiryDate("10-Jan-2018").withTissueType("1,4,7,10,11,9")
            .withNric(VALID_NRIC_DOCTOR_AMY).build();
    private static final Donor DONOR_IRENE_DONOR_EARLIEST = new DonorBuilder(DONOR_IRENE_DONOR)
            .withOrganExpiryDate("9-Feb-2017").withTissueType("1,4,7,10,8,9")
            .withNric(VALID_NRIC_DONOR_JOHN).build();

    /**
     * Checks if an ObservableList is sorted according to the comparator given.
     */
    private static boolean isListSorted(List<Person> listToCheck, Comparator comparator,
            boolean isMatchedPatient) {
        if (isMatchedPatient) {
            List<MatchedPatient> matchedPatientList = (List<MatchedPatient>) (List<?>) listToCheck;
            for (int i = 1; i < matchedPatientList.size(); i++) {
                MatchedPatient current = matchedPatientList.get(i);
                MatchedPatient previous = matchedPatientList.get(i - 1);
                if (comparator.compare(previous, current) > 0) {
                    return false;
                }
            }

        } else {
            List<MatchedDonor> matchedPatientList = (List<MatchedDonor>) (List<?>) listToCheck;
            for (int i = 1; i < matchedPatientList.size(); i++) {
                MatchedDonor current = matchedPatientList.get(i);
                MatchedDonor previous = matchedPatientList.get(i - 1);
                if (comparator.compare(previous, current) > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    void constructor_nullCategory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortCommand(null));
    }

    @Test
    void execute_sortByPriority_success() throws Exception {
        //create people to sort by
        Patient patientHighPriority = new PatientBuilder()
                .withNric(VALID_NRIC_DONOR_IRENE_DONOR).withPriority("high").build();
        Patient patientMediumPriority = new PatientBuilder()
                .withNric(VALID_NRIC_DOCTOR_AMY).withPriority("medium").build();
        Patient patientLowPriority = new PatientBuilder()
                .withNric(VALID_NRIC_DONOR_JOHN).withPriority("low").build();

        //put people inside model
        AddressBook addressBook = new AddressBookBuilder().withPerson(patientHighPriority)
                .withPerson(patientLowPriority).withPerson(patientMediumPriority).build();
        Model model = new ModelManager(addressBook, new UserPrefs());

        //match to create MatchedPatients
        MatchCommand matchCommand = new MatchCommand(VALID_NRIC_PATIENT_IRENE);
        matchCommand.execute(model);

        String input = SortCommandParser.PRIORITY;
        SortCommand command = new SortCommand(input);
        CommandResult commandResult = command.execute(model);
        CommandResult expectedCommandResult = new CommandResult(SortCommand.LIST_OF_SORTED_PATIENTS
                + SortCommand.MESSAGE_SUCCESS);

        //check if list is ordered
        assertTrue(isListSorted(model.getDisplayedPersonList(), new PriorityComparator(), true));
        assertEquals(expectedCommandResult, commandResult);
    }

    @Test
    void execute_sortByCompatibilityRate_success() throws Exception {
        Patient patientIreneCopy = new PatientBuilder(PATIENT_IRENE).build();
        List<Person> personList = new ArrayList<>(Arrays.asList(patientIreneCopy, DONOR_IRENE_DONOR_FULLMATCH,
                DONOR_IRENE_DONOR_FOUR_MATCH, DONOR_IRENE_DONOR_FIVE_MATCH));

        //put people inside model
        AddressBook addressBook = new AddressBook();
        addressBook.setPersons(personList);
        Model model = new ModelManager(addressBook, new UserPrefs());

        //match to create MatchedDonors
        MatchCommand matchCommand = new MatchCommand(VALID_NRIC_PATIENT_IRENE);
        matchCommand.execute(model);

        String input = SortCommandParser.COMPATIBILITY_RATE;
        SortCommand command = new SortCommand(input);
        CommandResult commandResult = command.execute(model);
        CommandResult expectedCommandResult = new CommandResult(SortCommand.LIST_OF_SORTED_DONORS
                + SortCommand.MESSAGE_SUCCESS);

        assertTrue(isListSorted(model.getDisplayedPersonList(), new CompatibilityRateComparator(),
                false));
        assertEquals(expectedCommandResult, commandResult);
    }

    @Test
    void execute_sortByOrganExpiryDate_success() throws Exception {
        Patient patientIreneCopy = new PatientBuilder(PATIENT_IRENE).build();
        List<Person> personList = new ArrayList<>(Arrays.asList(patientIreneCopy,
                DONOR_IRENE_DONOR_EARLIEST, DONOR_IRENE_DONOR_LATEST, DONOR_IRENE_DONOR_SECOND_LATEST));

        //put people inside model
        AddressBook addressBook = new AddressBook();
        addressBook.setPersons(personList);
        Model model = new ModelManager(addressBook, new UserPrefs());

        //match to create MatchedDonors
        MatchCommand matchCommand = new MatchCommand(VALID_NRIC_PATIENT_IRENE);
        matchCommand.execute(model);

        String input = SortCommandParser.ORGAN_EXPIRY_DATE;
        SortCommand command = new SortCommand(input);
        CommandResult commandResult = command.execute(model);
        CommandResult expectedCommandResult = new CommandResult(SortCommand.LIST_OF_SORTED_DONORS
                + SortCommand.MESSAGE_SUCCESS);

        assertTrue(isListSorted(model.getDisplayedPersonList(), new ExpiryDateComparator(), false));
        assertEquals(expectedCommandResult, commandResult);
    }
}
