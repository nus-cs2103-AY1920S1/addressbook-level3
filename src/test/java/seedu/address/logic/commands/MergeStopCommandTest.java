package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_FIRE_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.PersonBuilder;
import seedu.address.commons.util.PolicyBuilder;
import seedu.address.logic.commands.merge.MergeCommand;
import seedu.address.logic.commands.merge.MergePersonCommand;
import seedu.address.logic.commands.merge.MergePolicyCommand;
import seedu.address.logic.commands.merge.MergeStopCommand;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.policy.Policy;
import seedu.address.testutil.TestUtil.ModelStub;

public class MergeStopCommandTest {

    @Test
    public void constructor_nullCommand_throwsNullPointerException() {
        String validDataType = Phone.DATA_TYPE;
        assertThrows(NullPointerException.class, () -> new MergeStopCommand(null, validDataType));
    }

    @Test
    public void constructor_nullFieldType_throwsNullPointerException() {
        MergeCommand validMergeCommand = new MergePersonCommand(new PersonBuilder().build());
        assertThrows(NullPointerException.class, () -> new MergeStopCommand(validMergeCommand, (String) null));
    }

    @Test
    public void execute_mergePersonStop_stopSuccessful() throws Exception {
        Person personUnderMerge = new PersonBuilder().build();
        Person originalPerson = new PersonBuilder().withName(VALID_NAME_AMY).build();
        MergeCommand validMergePersonCommand = new MergePersonCommand(personUnderMerge);
        ModelStubWithPerson modelStubWithPerson = new ModelStubWithPerson(originalPerson);
        validMergePersonCommand.execute(modelStubWithPerson);
        MergeStopCommand mergeStopCommand = new MergeStopCommand(validMergePersonCommand,
                AddressBookParser.MERGE_PERSON);
        ModelStub modelStub = new ModelStub();
        CommandResult commandResult = mergeStopCommand.execute(modelStub);
        assertEquals(String.format(MergeStopCommand.MESSAGE_MERGE_STOPPED, originalPerson),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_mergePolicyStop_stopSuccessful() throws Exception {
        Policy policyUnderMerge = new PolicyBuilder().build();
        Policy originalPolicy = new PolicyBuilder().withDescription(VALID_DESCRIPTION_FIRE_INSURANCE).build();
        MergeCommand validMergePolicyCommand = new MergePolicyCommand(policyUnderMerge);
        ModelStubWithPolicy modelStubWithPolicy = new ModelStubWithPolicy(originalPolicy);
        validMergePolicyCommand.execute(modelStubWithPolicy);
        MergeStopCommand mergeStopCommand = new MergeStopCommand(validMergePolicyCommand,
                AddressBookParser.MERGE_POLICY);
        ModelStub modelStub = new ModelStub();
        CommandResult commandResult = mergeStopCommand.execute(modelStub);
        assertEquals(String.format(MergeStopCommand.MESSAGE_MERGE_STOPPED, originalPolicy),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Policy policy = new PolicyBuilder().build();
        Person person = new PersonBuilder().build();
        MergeCommand policyMerge = new MergePolicyCommand(policy);
        MergeCommand personMerge = new MergePersonCommand(person);
        MergeStopCommand mergePolicyStopCommand = new MergeStopCommand(policyMerge, AddressBookParser.MERGE_POLICY);
        MergeStopCommand mergePersonStopCommand = new MergeStopCommand(personMerge, AddressBookParser.MERGE_PERSON);

        // same object -> returns true
        assertTrue(mergePolicyStopCommand.equals(mergePolicyStopCommand));

        // same values -> returns true
        MergeStopCommand mergePolicyStopCommandCopy = new MergeStopCommand(policyMerge, AddressBookParser.MERGE_POLICY);
        assertTrue(mergePolicyStopCommand.equals(mergePolicyStopCommandCopy));

        // different types -> returns false
        assertFalse(mergePolicyStopCommand.equals(1));

        // null -> returns false
        assertFalse(mergePolicyStopCommand.equals(null));

        // different person -> returns false
        assertFalse(mergePolicyStopCommand.equals(mergePersonStopCommand));
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
     * A Model stub that contains a single policy.
     */
    private class ModelStubWithPolicy extends ModelStub {
        private Policy policy;

        ModelStubWithPolicy(Policy policy) {
            requireNonNull(policy);
            this.policy = policy;
        }

        @Override
        public boolean hasPolicy(Policy policy) {
            requireNonNull(policy);
            return this.policy.isSamePolicy(policy);
        }

        public Policy getPolicy(Policy input) {
            return this.policy;
        }

        @Override
        public void setPolicy(Policy target, Policy editedPolicy) {
            this.policy = editedPolicy;
        }
    }

}
