package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.PersonBuilder;
import seedu.address.logic.commands.merge.MergePersonCommand;
import seedu.address.logic.commands.merge.MergePersonConfirmedCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

public class MergePersonConfirmedCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullCommand_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MergePersonConfirmedCommand(null));
    }

    @Test
    public void execute_mergeConfirmedWithOneMergeLeft_mergeSuccessful() throws Exception {
        Person originalPerson = new PersonBuilder(model.getFilteredPersonList().get(0)).build();
        Person inputPerson = new PersonBuilder(originalPerson).withPhone(VALID_PHONE_BOB).build();
        MergePersonCommandStub mergeCommandStub = new MergePersonCommandStub(inputPerson, originalPerson);
        MergePersonConfirmedCommand mergePersonConfirmedCommand = new MergePersonConfirmedCommand(mergeCommandStub);
        String expectedMessage = String.format(MergePersonConfirmedCommand.MESSAGE_MERGE_FIELD_SUCCESS, Phone.DATA_TYPE)
                + "\n" + String.format(mergeCommandStub.MESSAGE_SUCCESS,
                inputPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(originalPerson, inputPerson);
        expectedModel.saveAddressBookState();
        assertCommandSuccess(mergePersonConfirmedCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_mergeConfirmedWithMoreThanOneMergeLeft_mergeSuccessful() throws Exception {
        Person originalPerson = new PersonBuilder(model.getFilteredPersonList().get(0)).build();
        Person inputPerson = new PersonBuilder(originalPerson).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .build();
        Person expectedPerson = new PersonBuilder(originalPerson).withPhone(VALID_PHONE_BOB).build();
        MergePersonCommandStubWithMultipleMerges mergeCommandStub =
                new MergePersonCommandStubWithMultipleMerges(inputPerson, originalPerson);
        MergePersonConfirmedCommand mergePersonConfirmedCommand = new MergePersonConfirmedCommand(mergeCommandStub);
        String expectedMessage = String.format(MergePersonConfirmedCommand.MESSAGE_MERGE_FIELD_SUCCESS, Phone.DATA_TYPE)
                + "\n" + mergeCommandStub.getNextMergePrompt();
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(originalPerson, expectedPerson);
        expectedModel.saveAddressBookState();
        assertCommandSuccess(mergePersonConfirmedCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        MergePersonCommandStub commandWithAlice = new MergePersonCommandStub(alice, person);
        MergePersonCommandStub commandWithBob = new MergePersonCommandStub(bob, person);
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
        private Person originalPerson;

        public MergePersonCommandStub(Person inputPerson, Person originalPerson) {
            super(inputPerson);
            this.originalPerson = originalPerson;
        }

        public void updateOriginalPerson(Person editedPerson) {
            this.originalPerson = editedPerson;
        }

        public String getNextMergePrompt() {
            StringBuilder mergePrompt = new StringBuilder();
            mergePrompt.append(String.format(MERGE_COMMAND_PROMPT, Phone.DATA_TYPE) + "\n")
                .append(MERGE_ORIGINAL_HEADER + originalPerson.getPhone().value + "\n")
                .append(MERGE_INPUT_HEADER + super.getInputPerson().getPhone().value)
                .append(MERGE_INSTRUCTIONS);
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
        private Person originalPerson;
        private ArrayList<String> dataTypes = new ArrayList<>();

        public MergePersonCommandStubWithMultipleMerges(Person inputPerson, Person originalPerson) {
            super(inputPerson);
            dataTypes.add(Phone.DATA_TYPE);
            dataTypes.add(Address.DATA_TYPE);
            this.originalPerson = originalPerson;
        }

        public void updateOriginalPerson(Person editedPerson) {
            this.originalPerson = editedPerson;
        }

        public String getNextMergePrompt() {
            StringBuilder mergePrompt = new StringBuilder();
            mergePrompt.append(String.format(MERGE_COMMAND_PROMPT, Address.DATA_TYPE) + "\n")
                .append(MERGE_ORIGINAL_HEADER + originalPerson.getAddress().value + "\n")
                .append(MERGE_INPUT_HEADER + super.getInputPerson().getAddress().value)
                .append(MERGE_INSTRUCTIONS);
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

}
