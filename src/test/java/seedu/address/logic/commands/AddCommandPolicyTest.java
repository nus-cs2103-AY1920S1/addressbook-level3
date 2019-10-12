package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PolicyBuilder;

public class AddCommandPolicyTest {

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
    public void execute_duplicatePolicy_throwsCommandException() {
        Policy validPolicy = new PolicyBuilder().build();
        AddPolicyCommand addPolicyCommand = new AddPolicyCommand(validPolicy);
        ModelStub modelStub = new ModelStubWithPolicy(validPolicy);

        assertThrows(CommandException.class, addPolicyCommand.MESSAGE_DUPLICATE_POLICY + "\n"
                + validPolicy.toString() + "\n" + addPolicyCommand.DUPLICATE_POLICY_MERGE_PROMPT, ()
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

        // different person -> returns false
        assertFalse(addHealthCommand.equals(addFitnessCommand));
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
            return person;
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
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
