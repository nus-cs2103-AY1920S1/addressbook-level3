package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HEALTH_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showPolicyAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_POLICY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

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
import seedu.address.model.policy.PolicyName;

class UnassignPolicyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unfilteredList_success() {
        Person personToAssign = model.getAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Policy policyToAssign = model.getAddressBook().getPolicyList().get(INDEX_FIRST_POLICY.getZeroBased());
        Person assignedPerson = new PersonBuilder(personToAssign).removePolicies(policyToAssign).build();
        UnassignPolicyCommand unassignPolicyCommand = new UnassignPolicyCommand(INDEX_FIRST_PERSON,
                policyToAssign.getName());

        String expectedMessage = String.format(UnassignPolicyCommand.MESSAGE_UNASSIGN_POLICY_SUCCESS,
                policyToAssign.getName(), personToAssign.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                assignedPerson);
        expectedModel.saveAddressBookState();

        assertCommandSuccess(unassignPolicyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPolicyAtIndex(model, INDEX_FIRST_POLICY);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Policy policyInFilteredList = model.getFilteredPolicyList().get(INDEX_FIRST_POLICY.getZeroBased());
        Person unassignedPerson = new PersonBuilder(personInFilteredList).removePolicies(policyInFilteredList).build();
        UnassignPolicyCommand unassignPolicyCommand = new UnassignPolicyCommand(INDEX_FIRST_POLICY,
                policyInFilteredList.getName());

        String expectedMessage = String.format(UnassignPolicyCommand.MESSAGE_UNASSIGN_POLICY_SUCCESS,
                model.getFilteredPolicyList().get(INDEX_FIRST_POLICY.getZeroBased()).getName(),
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        showPolicyAtIndex(expectedModel, INDEX_FIRST_POLICY);
        expectedModel.setPerson(model.getFilteredPersonList().get(0), unassignedPerson);
        expectedModel.saveAddressBookState();

        assertCommandSuccess(unassignPolicyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_alreadyAssignedUnfilteredList_failure() {
        final PolicyName policyName = new PolicyName(VALID_NAME_HEALTH_INSURANCE);
        UnassignPolicyCommand unassignPolicyCommand = new UnassignPolicyCommand(INDEX_SECOND_PERSON, policyName);
        assertCommandFailure(unassignPolicyCommand, model, String.format(
                UnassignPolicyCommand.MESSAGE_ALREADY_UNASSIGNED,
                model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased()).getName(),
                policyName));
    }

    @Test
    public void execute_alreadyUnassignedFilteredList_failure() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        final PolicyName policyName = new PolicyName(VALID_NAME_HEALTH_INSURANCE);

        UnassignPolicyCommand unassignPolicyCommand = new UnassignPolicyCommand(INDEX_FIRST_PERSON, policyName);
        assertCommandFailure(unassignPolicyCommand, model, String.format(
                UnassignPolicyCommand.MESSAGE_ALREADY_UNASSIGNED,
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName(), policyName));
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnassignPolicyCommand unassignPolicyCommand = new UnassignPolicyCommand(outOfBoundIndex,
                new PolicyName(VALID_NAME_HEALTH_INSURANCE));

        assertCommandFailure(unassignPolicyCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    void execute_policyNotInAddressBook_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        final PolicyName policyName = new PolicyName("TESTING INVALID POLICY");

        UnassignPolicyCommand unassignPolicyCommand = new UnassignPolicyCommand(INDEX_FIRST_PERSON, policyName);
        assertCommandFailure(unassignPolicyCommand, model, String.format(UnassignPolicyCommand.MESSAGE_POLICY_NOT_FOUND,
                policyName));
    }

    @Test
    void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // check if given out of bounds index is still within range for entire list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnassignPolicyCommand unassignPolicyCommand = new UnassignPolicyCommand(outOfBoundIndex,
                new PolicyName(VALID_NAME_HEALTH_INSURANCE));
        assertCommandFailure(unassignPolicyCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final PolicyName policyName = new PolicyName(VALID_NAME_HEALTH_INSURANCE);
        final UnassignPolicyCommand standardCommand = new UnassignPolicyCommand(INDEX_FIRST_PERSON, policyName);

        // same value -> returns true
        UnassignPolicyCommand newCommand = new UnassignPolicyCommand(INDEX_FIRST_PERSON, policyName);
        assertTrue(standardCommand.equals(newCommand));

        // different values -> returns false
        newCommand = new UnassignPolicyCommand(INDEX_SECOND_PERSON, policyName);
        assertFalse(standardCommand.equals(newCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // different objects -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
    }
}
