package dukecooks.logic.commands.profile;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandResult;
import dukecooks.model.ModelStub;
import dukecooks.model.health.components.Record;
import dukecooks.model.profile.ReadOnlyUserProfile;
import dukecooks.model.profile.UserProfile;
import dukecooks.model.profile.person.Person;
import dukecooks.testutil.Assert;
import dukecooks.testutil.profile.PersonBuilder;


public class AddProfileCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddProfileCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAndRecordAdded modelStub = new ModelStubAcceptingPersonAndRecordAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddProfileCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddProfileCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddProfileCommand addAliceCommand = new AddProfileCommand(alice);
        AddProfileCommand addBobCommand = new AddProfileCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddProfileCommand addAliceCommandCopy = new AddProfileCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A ProfileModel stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }
    }

    /**
     * A ProfileModel stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasProfile() {
            return !personsAdded.isEmpty();
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyUserProfile getUserProfile() {
            return new UserProfile();
        }
    }

    /**
     * A ProfileModel stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAndRecordAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();
        final ArrayList<Record> recordsAdded = new ArrayList<>();

        @Override
        public boolean hasRecord(Record record) {
            requireNonNull(record);
            return recordsAdded.stream().anyMatch(record::isSameRecord);
        }

        @Override
        public void addRecord(Record record) {
            requireNonNull(record);
            recordsAdded.add(record);
        }

        @Override
        public boolean hasProfile() {
            return !personsAdded.isEmpty();
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyUserProfile getUserProfile() {
            return new UserProfile();
        }
    }

}
