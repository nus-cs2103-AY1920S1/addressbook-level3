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
import seedu.address.logic.commands.merge.MergePolicyConfirmedCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.policy.Coverage;
import seedu.address.model.policy.Description;
import seedu.address.model.policy.Policy;
import seedu.address.testutil.TestUtil.ModelStub;

public class MergePolicyConfirmedCommandTest {

    @Test
    public void constructor_nullCommand_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MergePolicyConfirmedCommand(null));
    }

    @Test
    public void execute_mergeConfirmedWithOneMergeLeft_mergeSuccessful() throws Exception {
        Policy validPolicy = new PolicyBuilder().build();
        Policy inputPolicy = new PolicyBuilder().withDescription(VALID_DESCRIPTION_FIRE_INSURANCE).build();
        MergePolicyCommandStub mergeCommandStub = new MergePolicyCommandStub(inputPolicy);
        ModelStubWithPolicy modelStub = new ModelStubWithPolicy(validPolicy);
        CommandResult commandResult = new MergePolicyConfirmedCommand(mergeCommandStub).execute(modelStub);
        assertEquals(String.format(MergePolicyConfirmedCommand.MESSAGE_MERGE_FIELD_SUCCESS,
            Description.DATA_TYPE)
            + "\n" + String.format(mergeCommandStub.MESSAGE_SUCCESS,
            inputPolicy), commandResult.getFeedbackToUser());
        assertEquals(modelStub.getPolicy(), inputPolicy);
    }

    @Test
    public void execute_mergeConfirmedWithMoreThanOneMergeLeft_mergeSuccessful() throws Exception {
        Policy validPolicy = new PolicyBuilder().build();
        Policy inputPolicy = new PolicyBuilder().withDescription(VALID_DESCRIPTION_FIRE_INSURANCE)
            .withCoverage(VALID_COVERAGE_FIRE_INSURANCE).build();
        MergePolicyCommandStubWithMultipleMerges mergeCommandStub =
            new MergePolicyCommandStubWithMultipleMerges(inputPolicy);
        ModelStubWithPolicy modelStub = new ModelStubWithPolicy(validPolicy);
        CommandResult commandResult = new MergePolicyConfirmedCommand(mergeCommandStub).execute(modelStub);
        assertEquals(String.format(MergePolicyConfirmedCommand.MESSAGE_MERGE_FIELD_SUCCESS, Description.DATA_TYPE)
            + "\n" + mergeCommandStub.getNextMergePrompt(), commandResult.getFeedbackToUser());
        assertEquals(modelStub.getPolicy(), new PolicyBuilder().withDescription(VALID_DESCRIPTION_FIRE_INSURANCE)
            .build());
    }

    @Test
    public void equals() {
        Policy health = new PolicyBuilder().withName("Health Insurance").build();
        Policy fire = new PolicyBuilder().withName("Fire Insurance").build();
        MergePolicyCommandStub commandWithHealth = new MergePolicyCommandStub(health);
        MergePolicyCommandStub commandWithFire = new MergePolicyCommandStub(fire);
        MergePolicyConfirmedCommand mergeHealthCommand = new MergePolicyConfirmedCommand(commandWithHealth);
        MergePolicyConfirmedCommand mergeFireCommand = new MergePolicyConfirmedCommand(commandWithFire);

        // same object -> returns true
        assertTrue(mergeHealthCommand.equals(mergeHealthCommand));

        // same values -> returns true
        MergePolicyConfirmedCommand mergeHealthCommandCopy = new MergePolicyConfirmedCommand(commandWithHealth);
        assertTrue(mergeHealthCommand.equals(mergeHealthCommandCopy));

        // different types -> returns false
        assertFalse(mergeHealthCommand.equals(1));

        // null -> returns false
        assertFalse(mergeHealthCommand.equals(null));

        // different merge command-> returns false
        assertFalse(mergeHealthCommand.equals(mergeFireCommand));
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
        public AddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public void setPolicy(Policy target, Policy editedPolicy) {
            this.policy = editedPolicy;
        }

        @Override
        public void saveAddressBookState() {}
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
