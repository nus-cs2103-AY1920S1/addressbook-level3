package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.testutil.PersonBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.*;

class AssignPolicyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_filteredList_success() {

        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPolicyAtIndex(model, INDEX_SECOND_POLICY);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Policy policyInFilteredList = model.getFilteredPolicyList().get(INDEX_FIRST_POLICY.getZeroBased());
        Person assignedPerson = new PersonBuilder(personInFilteredList).withPolicies(policyInFilteredList).build();
        AssignPolicyCommand assignCommand = new AssignPolicyCommand(INDEX_FIRST_POLICY, INDEX_FIRST_PERSON);

        String expectedMessage = String.format(AssignPolicyCommand.MESSAGE_ASSIGN_POLICY_SUCCESS,
                model.getFilteredPolicyList().get(INDEX_FIRST_POLICY.getZeroBased()).getName(),
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), assignedPerson);

        assertCommandSuccess(assignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_alreadyAssignedUnfilteredList_failure() {
        AssignPolicyCommand assignCommand = new AssignPolicyCommand(INDEX_SECOND_POLICY, INDEX_SECOND_PERSON);
        assertCommandFailure(assignCommand, model, String.format(AssignPolicyCommand.MESSAGE_ALREADY_ASSIGNED,
                model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased()).getName(),
                model.getAddressBook().getPolicyList().get(INDEX_SECOND_POLICY.getZeroBased()).getName()));
    }

    @Test
    public void execute_alreadyAssignedFilteredList_failure() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        showPolicyAtIndex(model, INDEX_SECOND_POLICY);

        AssignPolicyCommand assignCommand = new AssignPolicyCommand(INDEX_FIRST_POLICY, INDEX_FIRST_PERSON);
        assertCommandFailure(assignCommand, model, String.format(AssignPolicyCommand.MESSAGE_ALREADY_ASSIGNED,
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName(),
                model.getFilteredPolicyList().get(INDEX_FIRST_POLICY.getZeroBased()).getName()));
    }

    @Test
    public void execute_invalidPolicyIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPolicyList().size() + 1);
        AssignPolicyCommand assignCommand = new AssignPolicyCommand(outOfBoundIndex, INDEX_FIRST_PERSON);

        assertCommandFailure(assignCommand, model, Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AssignPolicyCommand assignCommand = new AssignPolicyCommand(INDEX_FIRST_POLICY, outOfBoundIndex);

        assertCommandFailure(assignCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // check if given out of bounds index is still within range for entire list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AssignPolicyCommand assignCommand = new AssignPolicyCommand(INDEX_FIRST_POLICY, outOfBoundIndex);
        assertCommandFailure(assignCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    void execute_invalidPolicyIndexFilteredList_failure() {
        showPolicyAtIndex(model, INDEX_FIRST_POLICY);
        Index outOfBoundIndex = INDEX_SECOND_POLICY;

        // check if given out of bounds index is still within range for entire list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPolicyList().size());

        AssignPolicyCommand assignCommand = new AssignPolicyCommand(outOfBoundIndex, INDEX_FIRST_PERSON);
        assertCommandFailure(assignCommand, model, Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AssignPolicyCommand standardCommand = new AssignPolicyCommand(INDEX_FIRST_POLICY, INDEX_FIRST_PERSON);

        // same value -> returns true
        AssignPolicyCommand newCommand = new AssignPolicyCommand(INDEX_FIRST_POLICY, INDEX_FIRST_PERSON);
        assertTrue(standardCommand.equals(newCommand));

        // different values -> returns false
        newCommand = new AssignPolicyCommand(INDEX_SECOND_POLICY, INDEX_FIRST_PERSON);
        assertFalse(standardCommand.equals(newCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // different objects -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
    }
}