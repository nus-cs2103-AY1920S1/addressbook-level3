package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_FIRE_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.PolicyBuilder;
import seedu.address.logic.commands.merge.MergePolicyCommand;
import seedu.address.logic.commands.merge.MergePolicyRejectedCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.policy.Coverage;
import seedu.address.model.policy.Description;
import seedu.address.model.policy.Policy;

public class MergePolicyRejectedCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullCommand_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MergePolicyRejectedCommand(null));
    }

    @Test
    public void execute_mergeRejectedWithOneMergeLeft_mergeSuccessful() throws Exception {
        Policy originalPolicy = new PolicyBuilder(model.getFilteredPolicyList().get(0)).build();
        Policy inputPolicy = new PolicyBuilder(originalPolicy).withDescription(VALID_DESCRIPTION_FIRE_INSURANCE)
                .build();
        MergePolicyCommandStub mergeCommandStub = new MergePolicyCommandStub(inputPolicy, originalPolicy);
        MergePolicyRejectedCommand mergePolicyRejectedCommand = new MergePolicyRejectedCommand(mergeCommandStub);
        String expectedMessage = String.format(MergePolicyRejectedCommand.MESSAGE_MERGE_FIELD_NOT_EXECUTED,
                Description.DATA_TYPE) + "\n" + String.format(mergeCommandStub.MESSAGE_SUCCESS,
                mergeCommandStub.getOriginalPolicy());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        assertCommandSuccess(mergePolicyRejectedCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_mergeConfirmedWithMoreThanOneMergeLeft_mergeSuccessful() throws Exception {
        Policy originalPolicy = new PolicyBuilder(model.getFilteredPolicyList().get(0)).build();
        Policy inputPolicy = new PolicyBuilder(originalPolicy).withDescription(VALID_DESCRIPTION_FIRE_INSURANCE)
                .build();
        MergePolicyCommandStubWithMultipleMerges mergeCommandStub =
                new MergePolicyCommandStubWithMultipleMerges(inputPolicy, originalPolicy);
        MergePolicyRejectedCommand mergePolicyRejectedCommand = new MergePolicyRejectedCommand(mergeCommandStub);
        String expectedMessage = String.format(MergePolicyRejectedCommand.MESSAGE_MERGE_FIELD_NOT_EXECUTED,
                Description.DATA_TYPE)
                + "\n" + mergeCommandStub.getNextMergePrompt();
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        assertCommandSuccess(mergePolicyRejectedCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Policy policy = new PolicyBuilder().build();
        Policy alice = new PolicyBuilder().withName("Alice").build();
        Policy bob = new PolicyBuilder().withName("Bob").build();
        MergePolicyCommandStub commandWithAlice = new MergePolicyCommandStub(alice, policy);
        MergePolicyCommandStub commandWithBob = new MergePolicyCommandStub(bob, policy);
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
        private Policy originalPolicy;

        public MergePolicyCommandStub(Policy inputPolicy, Policy originalPolicy) {
            super(inputPolicy);
            this.originalPolicy = originalPolicy;
        }

        public void updateOriginalPolicy(Policy editedPolicy) {
            this.originalPolicy = editedPolicy;
        }

        public String getNextMergePrompt() {
            StringBuilder mergePrompt = new StringBuilder();
            mergePrompt.append(String.format(MERGE_COMMAND_PROMPT, Description.DATA_TYPE) + "\n")
                .append(MERGE_ORIGINAL_HEADER + originalPolicy.getDescription().description + "\n")
                .append(MERGE_INPUT_HEADER + super.getInputPolicy().getDescription().description)
                .append(MERGE_INSTRUCTIONS);
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
        private Policy originalPolicy;
        private ArrayList<String> dataTypes = new ArrayList<>();

        public MergePolicyCommandStubWithMultipleMerges(Policy inputPolicy, Policy originalPolicy) {
            super(inputPolicy);
            dataTypes.add(Description.DATA_TYPE);
            dataTypes.add(Coverage.DATA_TYPE);
            this.originalPolicy = originalPolicy;
        }

        public void updateOriginalPolicy(Policy editedPolicy) {
            this.originalPolicy = editedPolicy;
        }

        public String getNextMergePrompt() {
            StringBuilder mergePrompt = new StringBuilder();
            mergePrompt.append(String.format(MERGE_COMMAND_PROMPT, Coverage.DATA_TYPE) + "\n")
                .append(MERGE_ORIGINAL_HEADER + originalPolicy.getCoverage().coverage + "\n")
                .append(MERGE_INPUT_HEADER + super.getInputPolicy().getCoverage().coverage)
                .append(MERGE_INSTRUCTIONS);
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

}
