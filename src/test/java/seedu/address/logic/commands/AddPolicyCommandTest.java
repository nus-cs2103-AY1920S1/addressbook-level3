package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_FIRE_INSURANCE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.PolicyBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.policy.Policy;
import seedu.address.testutil.TestUtil.ModelStub;

public class AddPolicyCommandTest {

    @Test
    public void constructor_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPolicyCommand(null));
    }

    @Test
    public void execute_policyAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPolicyAdded modelStub = new ModelStubAcceptingPolicyAdded();
        Policy validPolicy = new PolicyBuilder().build();

        CommandResult commandResult = new AddPolicyCommand(validPolicy).execute(modelStub);

        assertEquals(String.format(AddPolicyCommand.MESSAGE_SUCCESS, validPolicy), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPolicy), modelStub.policiesAdded);
    }

    @Test
    public void execute_duplicatePolicyWithSameFields_throwsCommandException() {
        Policy validPolicy = new PolicyBuilder().build();
        AddPolicyCommand addPolicyCommand = new AddPolicyCommand(validPolicy);
        ModelStub modelStub = new ModelStubWithPolicy(validPolicy);

        assertThrows(CommandException.class, addPolicyCommand.generateExceptionMessageWithoutMergePrompt(validPolicy), (
        ) -> addPolicyCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicatePolicyWithDifferentFields_throwsCommandException() {
        Policy validPolicy = new PolicyBuilder().build();
        Policy duplicatePolicyWithDifferentDescription = new PolicyBuilder()
            .withDescription(VALID_DESCRIPTION_FIRE_INSURANCE).build();
        AddPolicyCommand addPolicyCommand = new AddPolicyCommand(duplicatePolicyWithDifferentDescription);
        ModelStub modelStub = new ModelStubWithPolicy(validPolicy);
        assertThrows(CommandException.class,
            addPolicyCommand.generateExceptionMessageWithMergePrompt(validPolicy), ()
                -> addPolicyCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Policy health = new PolicyBuilder().withName("Health").build();
        Policy fitness = new PolicyBuilder().withName("Fitness").build();
        AddPolicyCommand addHealthCommand = new AddPolicyCommand(health);
        AddPolicyCommand addFitnessCommand = new AddPolicyCommand(fitness);

        // same object -> returns true
        assertTrue(addHealthCommand.equals(addHealthCommand));

        // same values -> returns true
        AddPolicyCommand addHealthCommandCopy = new AddPolicyCommand(health);
        assertTrue(addHealthCommand.equals(addHealthCommandCopy));

        // different types -> returns false
        assertFalse(addHealthCommand.equals(1));

        // null -> returns false
        assertFalse(addHealthCommand.equals(null));

        // different policy -> returns false
        assertFalse(addHealthCommand.equals(addFitnessCommand));
    }

    /**
     * A Model stub that contains a single policy.
     */
    private class ModelStubWithPolicy extends ModelStub {
        private final Policy policy;

        ModelStubWithPolicy(Policy policy) {
            requireNonNull(policy);
            this.policy = policy;
        }

        @Override
        public boolean hasPolicy(Policy policy) {
            requireNonNull(policy);
            return this.policy.isSamePolicy(policy);
        }

        @Override
        public Policy getPolicy(Policy policy) {
            requireNonNull(policy);
            return this.policy;
        }
    }

    /**
     * A Model stub that always accept the person being added.
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
        public void saveAddressBookState() {
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
