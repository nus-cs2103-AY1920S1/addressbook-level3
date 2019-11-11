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
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Description;
import seedu.address.model.policy.Policy;
import seedu.address.testutil.TestUtil.ModelStub;

public class MergePolicyCommandTest {

    @Test
    public void constructor_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MergePolicyCommand(null));
    }

    @Test
    public void execute_mergeWithOneDifference_mergeSuccessful() throws Exception {
        Policy validPolicy = new PolicyBuilder().build();
        Policy inputPolicy = new PolicyBuilder().withDescription(VALID_DESCRIPTION_FIRE_INSURANCE).build();
        ModelStubWithPolicy modelStub = new ModelStubWithPolicy(validPolicy);
        CommandResult commandResult = new MergePolicyCommand(inputPolicy).execute(modelStub);
        assertEquals(String.format(MergePolicyCommand.MERGE_COMMAND_PROMPT, Description.DATA_TYPE)
            + "\n" + MergePolicyCommand.MERGE_ORIGINAL_HEADER + validPolicy.getDescription().description + "\n"
            + MergePolicyCommand.MERGE_INPUT_HEADER + VALID_DESCRIPTION_FIRE_INSURANCE
            + MergePolicyCommand.MERGE_INSTRUCTIONS,
            commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_mergeWithMoreThanOneDifference_mergeSuccessful() throws Exception {
        Policy validPolicy = new PolicyBuilder().build();
        Policy inputPolicy = new PolicyBuilder().withDescription(VALID_DESCRIPTION_FIRE_INSURANCE)
            .withCoverage(VALID_COVERAGE_FIRE_INSURANCE).build();
        ModelStubWithPolicy modelStub = new ModelStubWithPolicy(validPolicy);
        CommandResult commandResult = new MergePolicyCommand(inputPolicy).execute(modelStub);
        assertEquals(String.format(MergePolicyCommand.MERGE_COMMAND_PROMPT, Description.DATA_TYPE)
            + "\n" + MergePolicyCommand.MERGE_ORIGINAL_HEADER + validPolicy.getDescription().description + "\n"
            + MergePolicyCommand.MERGE_INPUT_HEADER + VALID_DESCRIPTION_FIRE_INSURANCE
            + MergePolicyCommand.MERGE_INSTRUCTIONS,
            commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Policy health = new PolicyBuilder().withName("Health Insurance").build();
        Policy fire = new PolicyBuilder().withName("Fire Insurance").build();
        MergePolicyCommand commandWithHealth = new MergePolicyCommand(health);
        MergePolicyCommand commandWithFire = new MergePolicyCommand(fire);

        // same object -> returns true
        assertTrue(commandWithHealth.equals(commandWithHealth));

        // same values -> returns true
        MergePolicyCommand commandWithHealthCopy = new MergePolicyCommand(health);
        assertTrue(commandWithHealth.equals(commandWithHealthCopy));

        // different types -> returns false
        assertFalse(commandWithHealth.equals(1));

        // null -> returns false
        assertFalse(commandWithHealth.equals(null));

        // different Policy -> returns false
        assertFalse(commandWithHealth.equals(commandWithFire));
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

        public Policy getPolicy(Policy input) {
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
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
