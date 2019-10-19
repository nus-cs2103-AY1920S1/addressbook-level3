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
import seedu.address.logic.commands.merge.MergePolicyRejectedCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Coverage;
import seedu.address.model.policy.Description;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyName;

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
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person getPerson(Person person) {
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
            throw new AssertionError("This method should not be called.");
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
        public boolean hasPolicy(Policy policy) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPolicyWithName(PolicyName policyName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePolicy(Policy target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPolicy(Policy target, Policy editedPolicy) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Policy> getFilteredPolicyList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
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
