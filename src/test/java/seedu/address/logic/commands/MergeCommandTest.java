package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.policy.Policy;
import seedu.address.testutil.PersonBuilder;

public class MergeCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MergeCommand(null));
    }

    @Test
    public void execute_mergeConfirmedWithOneDifference_mergeSuccessful() throws Exception {
        Person validPerson = new PersonBuilder().build();
        Person inputPerson = new PersonBuilder().withPhone(VALID_PHONE_AMY).build();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(validPerson);
        CommandResult commandResult = new MergeCommand(inputPerson).execute(modelStub);
        assertEquals(String.format(MergeCommand.MERGE_COMMAND_PROMPT, Phone.DATA_TYPE)
                + "\n" + MergeCommand.ORIGINAL_HEADER + validPerson.getPhone().value + "\n" + MergeCommand.INPUT_HEADER
                + VALID_PHONE_AMY, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_mergeConfirmedWithMoreThanOneDifference_mergeSuccessful() throws Exception {
        Person validPerson = new PersonBuilder().build();
        Person inputPerson = new PersonBuilder().withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB).build();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(validPerson);
        CommandResult commandResult = new MergeCommand(inputPerson).execute(modelStub);
        assertEquals(String.format(MergeCommand.MERGE_COMMAND_PROMPT, Phone.DATA_TYPE)
                + "\n" + MergeCommand.ORIGINAL_HEADER + validPerson.getPhone().value + "\n" + MergeCommand.INPUT_HEADER
                + VALID_PHONE_BOB, commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        MergeCommand commandWithAlice = new MergeCommand(alice);
        MergeCommand commandWithBob = new MergeCommand(bob);

        // same object -> returns true
        assertTrue(commandWithAlice.equals(commandWithAlice));

        // same values -> returns true
        MergeCommand commandWithAliceCopy = new MergeCommand(alice);
        assertTrue(commandWithAlice.equals(commandWithAliceCopy));

        // different types -> returns false
        assertFalse(commandWithAlice.equals(1));

        // null -> returns false
        assertFalse(commandWithAlice.equals(null));

        // different person -> returns false
        assertFalse(commandWithAlice.equals(commandWithBob));
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
        public Person getPerson(Person person) {
            return person;
        }

        @Override
        public void addPolicy(Policy policy) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Policy getPolicy(Policy policy) {
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
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPolicy(Policy policy) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePolicy(Policy target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPolicy(Policy target, Policy editedPolicy) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Policy> getFilteredPolicyList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPolicyList(Predicate<Policy> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }

        public Person getPerson(Person input) {
            return this.person;
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            this.person = editedPerson;
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();
        final ArrayList<Policy> policiesAdded = new ArrayList<>();

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
        public void addPolicy(Policy policy) {
            requireNonNull(policy);
            policiesAdded.add(policy);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
