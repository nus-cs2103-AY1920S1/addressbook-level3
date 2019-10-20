package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.PersonBuilder;
import seedu.address.logic.commands.merge.MergePersonCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.policy.Policy;
import seedu.address.testutil.TestUtil.ModelStub;

public class MergePersonCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MergePersonCommand(null));
    }

    @Test
    public void execute_mergeConfirmedWithOneDifference_mergeSuccessful() throws Exception {
        Person validPerson = new PersonBuilder().build();
        Person inputPerson = new PersonBuilder().withPhone(VALID_PHONE_AMY).build();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(validPerson);
        CommandResult commandResult = new MergePersonCommand(inputPerson).execute(modelStub);
        assertEquals(String.format(MergePersonCommand.MERGE_COMMAND_PROMPT, Phone.DATA_TYPE)
            + "\n" + MergePersonCommand.ORIGINAL_HEADER + validPerson.getPhone().value + "\n"
            + MergePersonCommand.INPUT_HEADER + VALID_PHONE_AMY, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_mergeConfirmedWithMoreThanOneDifference_mergeSuccessful() throws Exception {
        Person validPerson = new PersonBuilder().build();
        Person inputPerson = new PersonBuilder().withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB).build();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(validPerson);
        CommandResult commandResult = new MergePersonCommand(inputPerson).execute(modelStub);
        assertEquals(String.format(MergePersonCommand.MERGE_COMMAND_PROMPT, Phone.DATA_TYPE)
            + "\n" + MergePersonCommand.ORIGINAL_HEADER + validPerson.getPhone().value + "\n"
            + MergePersonCommand.INPUT_HEADER + VALID_PHONE_BOB, commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        MergePersonCommand commandWithAlice = new MergePersonCommand(alice);
        MergePersonCommand commandWithBob = new MergePersonCommand(bob);

        // same object -> returns true
        assertTrue(commandWithAlice.equals(commandWithAlice));

        // same values -> returns true
        MergePersonCommand commandWithAliceCopy = new MergePersonCommand(alice);
        assertTrue(commandWithAlice.equals(commandWithAliceCopy));

        // different types -> returns false
        assertFalse(commandWithAlice.equals(1));

        // null -> returns false
        assertFalse(commandWithAlice.equals(null));

        // different person -> returns false
        assertFalse(commandWithAlice.equals(commandWithBob));
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
