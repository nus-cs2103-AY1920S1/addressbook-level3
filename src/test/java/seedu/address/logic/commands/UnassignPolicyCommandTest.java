package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.PersonBuilder;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.*;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

class UnassignPolicyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_UnfilteredList_success() {
        Person personToUnassign = model.getAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Policy policyToUnassign = model.getAddressBook().getPolicyList().get(INDEX_FIRST_POLICY.getZeroBased());
        Person assignedPerson = new PersonBuilder(personToUnassign).removePolicies(policyToUnassign).build();
        UnassignPolicyCommand unassignPolicyCommand = new UnassignPolicyCommand(INDEX_FIRST_POLICY, INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnassignPolicyCommand.MESSAGE_UNASSIGN_POLICY_SUCCESS,
                policyToUnassign.getName(), personToUnassign.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                assignedPerson);

        assertCommandSuccess(unassignPolicyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPolicyAtIndex(model, INDEX_FIRST_POLICY);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Policy policyInFilteredList = model.getFilteredPolicyList().get(INDEX_FIRST_POLICY.getZeroBased());
        Person unassignedPerson = new PersonBuilder(personInFilteredList).removePolicies(policyInFilteredList).build();
        UnassignPolicyCommand unassignPolicyCommand = new UnassignPolicyCommand(INDEX_FIRST_POLICY, INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnassignPolicyCommand.MESSAGE_UNASSIGN_POLICY_SUCCESS,
                model.getFilteredPolicyList().get(INDEX_FIRST_POLICY.getZeroBased()).getName(),
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), unassignedPerson);

        assertCommandSuccess(unassignPolicyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_alreadyUnassignedUnfilteredList_failure() {
        UnassignPolicyCommand unassignPolicyCommand = new UnassignPolicyCommand(INDEX_SECOND_POLICY, INDEX_FIRST_PERSON);
        assertCommandFailure(unassignPolicyCommand, model, String.format(UnassignPolicyCommand.MESSAGE_ALREADY_UNASSIGNED,
                model.getAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName(),
                model.getAddressBook().getPolicyList().get(INDEX_SECOND_POLICY.getZeroBased()).getName()));
    }

    @Test
    public void execute_alreadyUnassignedFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPolicyAtIndex(model, INDEX_SECOND_POLICY);

        UnassignPolicyCommand unassignPolicyCommand = new UnassignPolicyCommand(INDEX_FIRST_POLICY, INDEX_FIRST_PERSON);
        assertCommandFailure(unassignPolicyCommand, model, String.format(UnassignPolicyCommand.MESSAGE_ALREADY_UNASSIGNED,
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName(),
                model.getFilteredPolicyList().get(INDEX_FIRST_POLICY.getZeroBased()).getName()));
    }

    @Test
    public void execute_invalidPolicyIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPolicyList().size() + 1);
        UnassignPolicyCommand unassignPolicyCommand = new UnassignPolicyCommand(outOfBoundIndex, INDEX_FIRST_PERSON);

        assertCommandFailure(unassignPolicyCommand, model, Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnassignPolicyCommand unassignPolicyCommand = new UnassignPolicyCommand(INDEX_FIRST_POLICY, outOfBoundIndex);

        assertCommandFailure(unassignPolicyCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // check if given out of bounds index is still within range for entire list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnassignPolicyCommand unassignPolicyCommand = new UnassignPolicyCommand(INDEX_FIRST_POLICY, outOfBoundIndex);
        assertCommandFailure(unassignPolicyCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    void execute_invalidPolicyIndexFilteredList_failure() {
        showPolicyAtIndex(model, INDEX_FIRST_POLICY);
        Index outOfBoundIndex = INDEX_SECOND_POLICY;

        // check if given out of bounds index is still within range for entire list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPolicyList().size());

        UnassignPolicyCommand unassignPolicyCommand = new UnassignPolicyCommand(outOfBoundIndex, INDEX_FIRST_PERSON);
        assertCommandFailure(unassignPolicyCommand, model, Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final UnassignPolicyCommand standardCommand = new UnassignPolicyCommand(INDEX_FIRST_POLICY, INDEX_FIRST_PERSON);

        // same value -> returns true
        UnassignPolicyCommand newCommand = new UnassignPolicyCommand(INDEX_FIRST_POLICY, INDEX_FIRST_PERSON);
        assertTrue(standardCommand.equals(newCommand));

        // different values -> returns false
        newCommand = new UnassignPolicyCommand(INDEX_SECOND_POLICY, INDEX_FIRST_PERSON);
        assertFalse(standardCommand.equals(newCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // different objects -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
    }
}