package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COVERAGE_FIRE_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_FIRE_INSURANCE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.PolicyBuilder;
import seedu.address.logic.commands.merge.MergePolicyCommand;
import seedu.address.logic.commands.merge.MergePolicyRejectedCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.policy.Coverage;
import seedu.address.model.policy.Description;
import seedu.address.model.policy.Policy;
import seedu.address.testutil.TestUtil.ModelStub;

public class MergePolicyRejectedCommandTest {

    @Test
    public void constructor_nullCommand_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MergePolicyRejectedCommand(null));
    }

    @Test
    public void execute_mergeRejectedWithOneMergeLeft_mergeSuccessful() throws Exception {
        Policy validPolicy = new PolicyBuilder().build();
        Policy inputPolicy = new PolicyBuilder().withDescription(VALID_DESCRIPTION_FIRE_INSURANCE).build();
        MergePolicyCommandStub mergeCommandStub = new MergePolicyCommandStub(inputPolicy);
        ModelStubWithPolicy modelStub = new ModelStubWithPolicy(validPolicy);
        CommandResult commandResult = new MergePolicyRejectedCommand(mergeCommandStub).execute(modelStub);
        assertEquals(String.format(MergePolicyRejectedCommand.MESSAGE_MERGE_FIELD_NOT_EXECUTED,
            Description.DATA_TYPE)
            + "\n" + String.format(mergeCommandStub.MESSAGE_SUCCESS,
            mergeCommandStub.getOriginalPolicy()), commandResult.getFeedbackToUser());
        assertEquals(modelStub.getPolicy(), new PolicyBuilder().build());
    }

    @Test
    public void execute_mergeConfirmedWithMoreThanOneMergeLeft_mergeSuccessful() throws Exception {
        Policy validPolicy = new PolicyBuilder().build();
        Policy inputPolicy = new PolicyBuilder().withDescription(VALID_DESCRIPTION_FIRE_INSURANCE)
            .withCoverage(VALID_COVERAGE_FIRE_INSURANCE).build();
        MergePolicyCommandStubWithMultipleMerges mergeCommandStub =
            new MergePolicyCommandStubWithMultipleMerges(inputPolicy);
        ModelStubWithPolicy modelStub = new ModelStubWithPolicy(validPolicy);
        CommandResult commandResult = new MergePolicyRejectedCommand(mergeCommandStub).execute(modelStub);
        assertEquals(String.format(MergePolicyRejectedCommand.MESSAGE_MERGE_FIELD_NOT_EXECUTED,
            Description.DATA_TYPE)
            + "\n" + mergeCommandStub.getNextMergePrompt(), commandResult.getFeedbackToUser());
        assertEquals(modelStub.getPolicy(), new PolicyBuilder().build());
    }

    @Test
    public void equals() {
        Policy alice = new PolicyBuilder().withName("Alice").build();
        Policy bob = new PolicyBuilder().withName("Bob").build();
        MergePolicyCommandStub commandWithAlice = new MergePolicyCommandStub(alice);
        MergePolicyCommandStub commandWithBob = new MergePolicyCommandStub(bob);
        MergePolicyRejectedCommand mergeAliceCommand = new MergePolicyRejectedCommand(commandWithAlice);
        MergePolicyRejectedCommand mergeBobCommand = new MergePolicyRejectedCommand(commandWithBob);

        // same object -> returns true
        assertTrue(mergeAliceCommand.equals(mergeAliceCommand));

        // same values -> returns true
        MergePolicyRejectedCommand mergeAliceCommandCopy = new MergePolicyRejectedCommand(commandWithAlice);
        assertTrue(mergeAliceCommand.equals(mergeAliceCommandCopy));

        // different types -> returns false
        assertFalse(mergeAliceCommand.equals(1));

        // null -> returns false
        assertFalse(mergeAliceCommand.equals(null));

        // different Policy -> returns false
        assertFalse(mergeAliceCommand.equals(mergeBobCommand));
    }

    private class MergePolicyCommandStub extends MergePolicyCommand {
        /**
         * Creates an Merge Command to update the original {@code Policy} to the new {@code Policy}
         *
         * @param inputPolicy
         */
        private Policy originalPolicy = new PolicyBuilder().build();

        public MergePolicyCommandStub(Policy inputPolicy) {
            super(inputPolicy);
        }

        public void updateOriginalPolicy(Policy editedPolicy) {
            this.originalPolicy = editedPolicy;
        }

        public String getNextMergePrompt() {
            StringBuilder mergePrompt = new StringBuilder();
            mergePrompt.append(String.format(MERGE_COMMAND_PROMPT, Description.DATA_TYPE) + "\n")
                .append(ORIGINAL_HEADER + originalPolicy.getDescription().description + "\n")
                .append(INPUT_HEADER + super.getInputPolicy().getDescription().description);
            return mergePrompt.toString();
        }

        public void removeFirstDifferentField() {
        }

        public String getNextMergeFieldType() {
            return Description.DATA_TYPE;
        }

        public Policy getInputPolicy() {
            return super.getInputPolicy();
        }

        public Policy getOriginalPolicy() {
            return this.originalPolicy;
        }

        public boolean onlyOneMergeLeft() {
            return true;
        }

    }

    private class MergePolicyCommandStubWithMultipleMerges extends MergePolicyCommand {
        /**
         * Creates an Merge Command to update the original {@code Policy} to the new {@code Policy}
         *
         * @param inputPolicy
         */
        private Policy originalPolicy = new PolicyBuilder().build();
        private ArrayList<String> dataTypes = new ArrayList<>();

        public MergePolicyCommandStubWithMultipleMerges(Policy inputPolicy) {
            super(inputPolicy);
            dataTypes.add(Description.DATA_TYPE);
            dataTypes.add(Coverage.DATA_TYPE);
        }

        public void updateOriginalPolicy(Policy editedPolicy) {
            this.originalPolicy = editedPolicy;
        }

        public String getNextMergePrompt() {
            StringBuilder mergePrompt = new StringBuilder();
            mergePrompt.append(String.format(MERGE_COMMAND_PROMPT, Coverage.DATA_TYPE) + "\n")
                .append(ORIGINAL_HEADER + originalPolicy.getCoverage().coverage + "\n")
                .append(INPUT_HEADER + super.getInputPolicy().getCoverage().coverage);
            return mergePrompt.toString();
        }

        public void removeFirstDifferentField() {
            dataTypes.remove(0);
        }

        public String getNextMergeFieldType() {
            return dataTypes.get(0);
        }

        public Policy getInputPolicy() {
            return super.getInputPolicy();
        }

        public Policy getOriginalPolicy() {
            return this.originalPolicy;
        }

        public ArrayList<String> getDataTypes() {
            return this.dataTypes;
        }

        public boolean onlyOneMergeLeft() {
            return false;
        }
    }

    /**
     * A Model stub that contains a single Policy.
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

        public Policy getPolicy() {
            return this.policy;
        }

        @Override
        public void setPolicy(Policy target, Policy editedPolicy) {
            this.policy = editedPolicy;
        }
    }

    /**
     * A Model stub that always accept the Policy being added.
     */
    private class ModelStubAcceptingPolicyAdded extends ModelStub {
        final ArrayList<Policy> policiesAdded = new ArrayList<>();

        @Override
        public boolean hasPolicy(Policy policy) {
            requireNonNull(policy);
            return policiesAdded.stream().anyMatch(policy::isSamePolicy);
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
