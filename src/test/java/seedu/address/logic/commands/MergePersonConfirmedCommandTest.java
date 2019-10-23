package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.PersonBuilder;
import seedu.address.logic.commands.merge.MergePersonCommand;
import seedu.address.logic.commands.merge.MergePersonConfirmedCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.policy.Policy;
import seedu.address.testutil.TestUtil.ModelStub;

public class MergePersonConfirmedCommandTest {

    @Test
    public void constructor_nullCommand_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MergePersonConfirmedCommand(null));
    }

    @Test
    public void execute_mergeConfirmedWithOneMergeLeft_mergeSuccessful() throws Exception {
        Person validPerson = new PersonBuilder().build();
        Person inputPerson = new PersonBuilder().withPhone(VALID_PHONE_BOB).build();
        MergePersonCommandStub mergeCommandStub = new MergePersonCommandStub(inputPerson);
        ModelStubWithPerson modelStub = new ModelStubWithPerson(validPerson);
        CommandResult commandResult = new MergePersonConfirmedCommand(mergeCommandStub).execute(modelStub);
        assertEquals(String.format(MergePersonConfirmedCommand.MESSAGE_MERGE_FIELD_SUCCESS, Phone.DATA_TYPE)
            + "\n" + String.format(mergeCommandStub.MESSAGE_SUCCESS,
            inputPerson), commandResult.getFeedbackToUser());
        assertEquals(modelStub.getPerson(), inputPerson);
    }

    @Test
    public void execute_mergeConfirmedWithMoreThanOneMergeLeft_mergeSuccessful() throws Exception {
        Person validPerson = new PersonBuilder().build();
        Person inputPerson = new PersonBuilder().withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB).build();
        MergePersonCommandStubWithMultipleMerges mergeCommandStub =
            new MergePersonCommandStubWithMultipleMerges(inputPerson);
        ModelStubWithPerson modelStub = new ModelStubWithPerson(validPerson);
        CommandResult commandResult = new MergePersonConfirmedCommand(mergeCommandStub).execute(modelStub);
        assertEquals(String.format(MergePersonConfirmedCommand.MESSAGE_MERGE_FIELD_SUCCESS, Phone.DATA_TYPE)
            + "\n" + mergeCommandStub.getNextMergePrompt(), commandResult.getFeedbackToUser());
        assertEquals(modelStub.getPerson(), new PersonBuilder().withPhone(VALID_PHONE_BOB).build());
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        MergePersonCommandStub commandWithAlice = new MergePersonCommandStub(alice);
        MergePersonCommandStub commandWithBob = new MergePersonCommandStub(bob);
        MergePersonConfirmedCommand mergeAliceCommand = new MergePersonConfirmedCommand(commandWithAlice);
        MergePersonConfirmedCommand mergeBobCommand = new MergePersonConfirmedCommand(commandWithBob);

        // same object -> returns true
        assertTrue(mergeAliceCommand.equals(mergeAliceCommand));

        // same values -> returns true
        MergePersonConfirmedCommand mergeAliceCommandCopy = new MergePersonConfirmedCommand(commandWithAlice);
        assertTrue(mergeAliceCommand.equals(mergeAliceCommandCopy));

        // different types -> returns false
        assertFalse(mergeAliceCommand.equals(1));

        // null -> returns false
        assertFalse(mergeAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(mergeAliceCommand.equals(mergeBobCommand));
    }

    private class MergePersonCommandStub extends MergePersonCommand {
        /**
         * Creates an Merge Command to update the original {@code Person} to the new {@code Person}
         *
         * @param inputPerson
         */
        private Person originalPerson = new PersonBuilder().build();

        public MergePersonCommandStub(Person inputPerson) {
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

    private class MergePersonCommandStubWithMultipleMerges extends MergePersonCommand {
        /**
         * Creates an Merge Command to update the original {@code Person} to the new {@code Person}
         *
         * @param inputPerson
         */
        private Person originalPerson = new PersonBuilder().build();
        private ArrayList<String> dataTypes = new ArrayList<>();

        public MergePersonCommandStubWithMultipleMerges(Person inputPerson) {
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

        @Override
        public void saveAddressBookState() {}
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
