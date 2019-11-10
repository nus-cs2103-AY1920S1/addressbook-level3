package organice.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import organice.logic.commands.exceptions.CommandException;

import organice.model.AddressBook;
import organice.model.ModelStub;
import organice.model.ReadOnlyAddressBook;
import organice.model.person.Doctor;
import organice.model.person.Donor;
import organice.model.person.Patient;
import organice.model.person.Person;
import organice.testutil.DoctorBuilder;
import organice.testutil.DonorBuilder;
import organice.testutil.PatientBuilder;
import organice.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        Person person = null;
        assertThrows(NullPointerException.class, () -> new AddCommand(person));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_doctorAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Doctor validDoctor = new DoctorBuilder().build();

        CommandResult commandResult = new AddCommand(validDoctor).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validDoctor), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validDoctor), modelStub.personsAdded);
    }

    @Test
    public void execute_donorAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Donor validDonor = new DonorBuilder().withAge("23").build();

        CommandResult commandResultDonor = new AddCommand(validDonor).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validDonor), commandResultDonor.getFeedbackToUser());
        assertEquals(Arrays.asList(validDonor), modelStub.personsAdded);
    }

    @Test
    public void execute_patientAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Patient validPatient = new PatientBuilder().withNric("S6295135D").build();

        CommandResult commandResultPatient = new AddCommand(validPatient).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPatient), commandResultPatient.getFeedbackToUser());
        assertEquals(Arrays.asList(validPatient), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateDoctor_throwsCommandException() {
        Doctor validDoctor = new DoctorBuilder().build();
        AddCommand addCommand = new AddCommand(validDoctor);
        ModelStub modelStub = new ModelStubWithPerson(validDoctor);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateDonor_throwsCommandException() {
        Donor validDonor = new DonorBuilder().build();
        AddCommand addCommand = new AddCommand(validDonor);
        ModelStub modelStub = new ModelStubWithPerson(validDonor);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicatePatient_throwsCommandException() {
        Patient validPatient = new PatientBuilder().build();
        AddCommand addCommand = new AddCommand(validPatient);
        ModelStub modelStub = new ModelStubWithPerson(validPatient);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        Patient alicePatient = new PatientBuilder().withName("Alice").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);
        AddCommand addAlicePatientCommand = new AddCommand(alicePatient);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));

        // different person type -> return false
        assertFalse(addAliceCommand.equals(addAlicePatientCommand));
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
