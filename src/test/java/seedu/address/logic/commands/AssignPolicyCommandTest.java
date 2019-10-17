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
import seedu.address.model.policy.PolicyName;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.*;

class AssignPolicyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_UnfilteredList_success() {
        Person personToAssign = model.getAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Policy policyToAssign = model.getAddressBook().getPolicyList().get(INDEX_SECOND_POLICY.getZeroBased());
        Person assignedPerson = new PersonBuilder(personToAssign).addPolicies(policyToAssign).build();
        AssignPolicyCommand assignPolicyCommand = new AssignPolicyCommand(INDEX_FIRST_PERSON, policyToAssign.getName());

        String expectedMessage = String.format(AssignPolicyCommand.MESSAGE_ASSIGN_POLICY_SUCCESS,
                policyToAssign.getName(), personToAssign.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                assignedPerson);

        assertCommandSuccess(assignPolicyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPolicyAtIndex(model, INDEX_SECOND_POLICY);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Policy policyInFilteredList = model.getFilteredPolicyList().get(INDEX_FIRST_POLICY.getZeroBased());
        Person assignedPerson = new PersonBuilder(personInFilteredList).addPolicies(policyInFilteredList).build();
        AssignPolicyCommand assignPolicyCommand = new AssignPolicyCommand(INDEX_FIRST_POLICY,
                policyInFilteredList.getName());

        String expectedMessage = String.format(AssignPolicyCommand.MESSAGE_ASSIGN_POLICY_SUCCESS,
                model.getFilteredPolicyList().get(INDEX_FIRST_POLICY.getZeroBased()).getName(),
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), assignedPerson);

        assertCommandSuccess(assignPolicyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_alreadyAssignedUnfilteredList_failure() {
        final PolicyName policyName = new PolicyName(VALID_NAME_LIFE_INSURANCE);
        AssignPolicyCommand assignPolicyCommand = new AssignPolicyCommand(INDEX_SECOND_PERSON, policyName);
        assertCommandFailure(assignPolicyCommand, model, String.format(AssignPolicyCommand.MESSAGE_ALREADY_ASSIGNED,
                model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased()).getName(),
                policyName));
    }

    @Test
    public void execute_alreadyAssignedFilteredList_failure() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        final PolicyName policyName = new PolicyName(VALID_NAME_LIFE_INSURANCE);

        AssignPolicyCommand assignPolicyCommand = new AssignPolicyCommand(INDEX_FIRST_PERSON, policyName);
        assertCommandFailure(assignPolicyCommand, model, String.format(AssignPolicyCommand.MESSAGE_ALREADY_ASSIGNED,
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName(), policyName));
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AssignPolicyCommand assignPolicyCommand = new AssignPolicyCommand(outOfBoundIndex,
                new PolicyName(VALID_NAME_HEALTH_INSURANCE));

        assertCommandFailure(assignPolicyCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    void execute_policyNotInAddressBook_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        final PolicyName policyName = new PolicyName("TESTING INVALID POLICY");

        AssignPolicyCommand assignPolicyCommand = new AssignPolicyCommand(INDEX_FIRST_PERSON, policyName);
        assertCommandFailure(assignPolicyCommand, model, String.format(AssignPolicyCommand.MESSAGE_POLICY_NOT_FOUND,
                policyName));
    }

    @Test
    void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // check if given out of bounds index is still within range for entire list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AssignPolicyCommand assignPolicyCommand = new AssignPolicyCommand(outOfBoundIndex,
                new PolicyName(VALID_NAME_HEALTH_INSURANCE));
        assertCommandFailure(assignPolicyCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final PolicyName policyName = new PolicyName(VALID_NAME_HEALTH_INSURANCE);
        final AssignPolicyCommand standardCommand = new AssignPolicyCommand(INDEX_FIRST_PERSON, policyName);

        // same value -> returns true
        AssignPolicyCommand newCommand = new AssignPolicyCommand(INDEX_FIRST_PERSON, policyName);
        assertTrue(standardCommand.equals(newCommand));

        // different values -> returns false
        newCommand = new AssignPolicyCommand(INDEX_SECOND_PERSON, policyName);
        assertFalse(standardCommand.equals(newCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // different objects -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
    }
}