package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COVERAGE_FIRE_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_FIRE_INSURANCE;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.util.PolicyBuilder;
import seedu.address.logic.commands.merge.MergePolicyCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Description;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyName;

public class MergePolicyCommandTest {

    @Test
    public void constructor_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MergePolicyCommand(null));
    }

    @Test
    public void execute_mergeConfirmedWithOneDifference_mergeSuccessful() throws Exception {
        Policy validPolicy = new PolicyBuilder().build();
        Policy inputPolicy = new PolicyBuilder().withDescription(VALID_DESCRIPTION_FIRE_INSURANCE).build();
        ModelStubWithPolicy modelStub = new ModelStubWithPolicy(validPolicy);
        CommandResult commandResult = new MergePolicyCommand(inputPolicy).execute(modelStub);
        assertEquals(String.format(MergePolicyCommand.MERGE_COMMAND_PROMPT, Description.DATA_TYPE)
                + "\n" + MergePolicyCommand.ORIGINAL_HEADER + validPolicy.getDescription().description + "\n"
                + MergePolicyCommand.INPUT_HEADER + VALID_DESCRIPTION_FIRE_INSURANCE,
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_mergeConfirmedWithMoreThanOneDifference_mergeSuccessful() throws Exception {
        Policy validPolicy = new PolicyBuilder().build();
        Policy inputPolicy = new PolicyBuilder().withDescription(VALID_DESCRIPTION_FIRE_INSURANCE)
                .withCoverage(VALID_COVERAGE_FIRE_INSURANCE).build();
        ModelStubWithPolicy modelStub = new ModelStubWithPolicy(validPolicy);
        CommandResult commandResult = new MergePolicyCommand(inputPolicy).execute(modelStub);
        assertEquals(String.format(MergePolicyCommand.MERGE_COMMAND_PROMPT, Description.DATA_TYPE)
                + "\n" + MergePolicyCommand.ORIGINAL_HEADER + validPolicy.getDescription().description + "\n"
                + MergePolicyCommand.INPUT_HEADER + VALID_DESCRIPTION_FIRE_INSURANCE,
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
        public void addPolicy(Policy policy) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Policy getPolicy(Policy policy) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Policy getPolicyWithName(PolicyName policyName) {
            return null;
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
        public Person getPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPolicy(Policy policy) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPolicyWithName(PolicyName policyName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePolicy(Policy target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPolicy(Policy target, Policy editedPolicy) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public ObservableList<Policy> getFilteredPolicyList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPolicyList(Predicate<Policy> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void saveAddressBookState() {
            throw new AssertionError("This method should not be called.");
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
