package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
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

public class MergeRejectedCommandTest {

    @Test
    public void constructor_nullCommand_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MergeRejectedCommand(null));
    }

    @Test
    public void execute_mergeRejectedWithOneMergeLeft_mergeSuccessful() throws Exception {
        Person validPerson = new PersonBuilder().build();
        Person inputPerson = new PersonBuilder().withPhone(VALID_PHONE_BOB).build();
        MergeCommandStub mergeCommandStub = new MergeCommandStub(inputPerson);
        ModelStubWithPerson modelStub = new ModelStubWithPerson(validPerson);
        CommandResult commandResult = new MergeRejectedCommand(mergeCommandStub).execute(modelStub);
        assertEquals(String.format(MergeRejectedCommand.MESSAGE_MERGE_FIELD_NOT_EXECUTED, Phone.DATA_TYPE)
                + "\n" + String.format(mergeCommandStub.MESSAGE_SUCCESS,
                mergeCommandStub.getOriginalPerson()), commandResult.getFeedbackToUser());
        assertEquals(modelStub.getPerson(), new PersonBuilder().build());
    }

    @Test
    public void execute_mergeConfirmedWithMoreThanOneMergeLeft_mergeSuccessful() throws Exception {
        Person validPerson = new PersonBuilder().build();
        Person inputPerson = new PersonBuilder().withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB).build();
        MergeCommandStubWithMultipleMerges mergeCommandStub = new MergeCommandStubWithMultipleMerges(inputPerson);
        ModelStubWithPerson modelStub = new ModelStubWithPerson(validPerson);
        CommandResult commandResult = new MergeRejectedCommand(mergeCommandStub).execute(modelStub);
        assertEquals(String.format(MergeRejectedCommand.MESSAGE_MERGE_FIELD_NOT_EXECUTED, Phone.DATA_TYPE)
                + "\n" + mergeCommandStub.getNextMergePrompt(), commandResult.getFeedbackToUser());
        assertEquals(modelStub.getPerson(), new PersonBuilder().build());
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        MergeCommandStub commandWithAlice = new MergeCommandStub(alice);
        MergeCommandStub commandWithBob = new MergeCommandStub(bob);
        MergeRejectedCommand mergeAliceCommand = new MergeRejectedCommand(commandWithAlice);
        MergeRejectedCommand mergeBobCommand = new MergeRejectedCommand(commandWithBob);

        // same object -> returns true
        assertTrue(mergeAliceCommand.equals(mergeAliceCommand));

        // same values -> returns true
        MergeRejectedCommand mergeAliceCommandCopy = new MergeRejectedCommand(commandWithAlice);
        assertTrue(mergeAliceCommand.equals(mergeAliceCommandCopy));

        // different types -> returns false
        assertFalse(mergeAliceCommand.equals(1));

        // null -> returns false
        assertFalse(mergeAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(mergeAliceCommand.equals(mergeBobCommand));
    }

    private class MergeCommandStub extends MergeCommand {
        /**
         * Creates an Merge Command to update the original {@code Person} to the new {@code Person}
         *
         * @param inputPerson
         */
        private Person originalPerson = new PersonBuilder().build();

        public MergeCommandStub(Person inputPerson) {
            super(inputPerson);
        }

        public void updateOriginalPerson(Person editedPerson) {
            this.originalPerson = editedPerson;
        }

        public String getNextMergePrompt() {
            StringBuilder mergePrompt = new StringBuilder();
            mergePrompt.append(String.format(MERGE_COMMAND_PROMPT, Phone.DATA_TYPE) + "\n")
                    .append(ORIGINAL_HEADER + originalPerson.getPhone().value + "\n")
                    .append(INPUT_HEADER + super.getInputPerson().getPhone().value);
            return mergePrompt.toString();
        }

        public void removeFirstDifferentField() {
        }

        public String getNextMergeFieldType() {
            return Phone.DATA_TYPE;
        }

        public Person getInputPerson() {
            return super.getInputPerson();
        }

        public Person getOriginalPerson() {
            return this.originalPerson;
        }

        public boolean onlyOneMergeLeft() {
            return true;
        }

    }

    private class MergeCommandStubWithMultipleMerges extends MergeCommand {
        /**
         * Creates an Merge Command to update the original {@code Person} to the new {@code Person}
         *
         * @param inputPerson
         */
        private Person originalPerson = new PersonBuilder().build();
        private ArrayList<String> dataTypes = new ArrayList<>();

        public MergeCommandStubWithMultipleMerges(Person inputPerson) {
            super(inputPerson);
            dataTypes.add(Phone.DATA_TYPE);
            dataTypes.add(Address.DATA_TYPE);
        }

        public void updateOriginalPerson(Person editedPerson) {
            this.originalPerson = editedPerson;
        }

        public String getNextMergePrompt() {
            StringBuilder mergePrompt = new StringBuilder();
            mergePrompt.append(String.format(MERGE_COMMAND_PROMPT, Address.DATA_TYPE) + "\n")
                    .append(ORIGINAL_HEADER + originalPerson.getAddress().value + "\n")
                    .append(INPUT_HEADER + super.getInputPerson().getAddress().value);
            return mergePrompt.toString();
        }

        public void removeFirstDifferentField() {
            dataTypes.remove(0);
        }

        public String getNextMergeFieldType() {
            return dataTypes.get(0);
        }

        public Person getInputPerson() {
            return super.getInputPerson();
        }

        public Person getOriginalPerson() {
            return this.originalPerson;
        }

        public ArrayList<String> getDataTypes() {
            return this.dataTypes;
        }

        public boolean onlyOneMergeLeft() {
            return false;
        }
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

        public Person getPerson() {
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
