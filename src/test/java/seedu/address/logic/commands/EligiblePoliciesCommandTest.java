package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_POLICIES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertListPolicyCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPolicy.FIRE_INSURANCE;
import static seedu.address.testutil.TypicalPolicy.HEALTH_INSURANCE;
import static seedu.address.testutil.TypicalPolicy.LIFE_INSURANCE;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.policy.PolicyEligibleForPersonPredicate;


/**
 * Contains integration tests (interaction with the Model) for {@code EligiblePoliciesCommand}.
 */
public class EligiblePoliciesCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Index firstIndex = INDEX_FIRST_PERSON;
        Index secondIndex = INDEX_SECOND_PERSON;
        EligiblePoliciesCommand eligibleFirstCommand = new EligiblePoliciesCommand(firstIndex);
        EligiblePoliciesCommand eligibleSecondCommand = new EligiblePoliciesCommand(secondIndex);

        // same object -> returns true
        assertTrue(eligibleFirstCommand.equals(eligibleFirstCommand));

        // same values -> returns true
        EligiblePoliciesCommand eligibleFirstCommandCopy = new EligiblePoliciesCommand(firstIndex);
        assertTrue(eligibleFirstCommand.equals(eligibleFirstCommandCopy));

        // different types -> returns false
        assertFalse(eligibleFirstCommand.equals(1));

        // null -> returns false
        assertFalse(eligibleFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(eligibleFirstCommand.equals(eligibleSecondCommand));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EligiblePoliciesCommand command = new EligiblePoliciesCommand(outOfBoundIndex);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EligiblePoliciesCommand command = new EligiblePoliciesCommand(outOfBoundIndex);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndex_noPolicyFound() {
        String expectedMessage = String.format(MESSAGE_POLICIES_LISTED_OVERVIEW, 0);
        EligiblePoliciesCommand command = new EligiblePoliciesCommand(INDEX_THIRD_PERSON);
        Person person = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        PolicyEligibleForPersonPredicate predicate = new PolicyEligibleForPersonPredicate(person);
        expectedModel.updateFilteredPolicyList(predicate);
        assertListPolicyCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndex_multiplePoliciesFound() {
        String expectedMessage = String.format(MESSAGE_POLICIES_LISTED_OVERVIEW, 3);
        EligiblePoliciesCommand command = new EligiblePoliciesCommand(INDEX_FIRST_PERSON);
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PolicyEligibleForPersonPredicate predicate = new PolicyEligibleForPersonPredicate(person);
        expectedModel.updateFilteredPolicyList(predicate);
        assertListPolicyCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(HEALTH_INSURANCE, LIFE_INSURANCE, FIRE_INSURANCE), model.getFilteredPolicyList());
    }
}
