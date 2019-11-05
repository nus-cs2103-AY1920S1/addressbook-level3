package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertListPeopleCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPolicyAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_POLICY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_POLICY;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CHLOE;
import static seedu.address.testutil.TypicalPersons.NATASHA;
import static seedu.address.testutil.TypicalPersons.VICTORIA;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonEligibleForPolicyPredicate;
import seedu.address.model.policy.Policy;


/**
 * Contains integration tests (interaction with the Model) for {@code EligiblePeopleCommand}.
 */
public class EligiblePeopleCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Index firstIndex = INDEX_FIRST_POLICY;
        Index secondIndex = INDEX_SECOND_POLICY;
        EligiblePeopleCommand eligibleFirstCommand = new EligiblePeopleCommand(firstIndex);
        EligiblePeopleCommand eligibleSecondCommand = new EligiblePeopleCommand(secondIndex);

        // same object -> returns true
        assertTrue(eligibleFirstCommand.equals(eligibleFirstCommand));

        // same values -> returns true
        EligiblePeopleCommand eligibleFirstCommandCopy = new EligiblePeopleCommand(firstIndex);
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
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPolicyList().size() + 1);
        EligiblePeopleCommand command = new EligiblePeopleCommand(outOfBoundIndex);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPolicyAtIndex(model, INDEX_FIRST_POLICY);

        Index outOfBoundIndex = INDEX_SECOND_POLICY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPolicyList().size());

        EligiblePeopleCommand command = new EligiblePeopleCommand(outOfBoundIndex);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndex_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        EligiblePeopleCommand command = new EligiblePeopleCommand(INDEX_FIRST_POLICY);
        Policy policy = model.getFilteredPolicyList().get(INDEX_FIRST_POLICY.getZeroBased());
        PersonEligibleForPolicyPredicate predicate = new PersonEligibleForPolicyPredicate(policy);
        expectedModel.updateFilteredPersonList(predicate);
        assertListPeopleCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndex_eligiblePeopleFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        EligiblePeopleCommand command = new EligiblePeopleCommand(INDEX_SECOND_POLICY);
        Policy policy = model.getFilteredPolicyList().get(INDEX_SECOND_POLICY.getZeroBased());
        PersonEligibleForPolicyPredicate predicate = new PersonEligibleForPolicyPredicate(policy);
        expectedModel.updateFilteredPersonList(predicate);
        assertListPeopleCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CHLOE, VICTORIA, NATASHA), model.getFilteredPersonList());
    }
}
