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
import static seedu.address.testutil.TypicalPersons.ASYRAF;
import static seedu.address.testutil.TypicalPersons.KEITH;
import static seedu.address.testutil.TypicalPersons.ROBIN;
import static seedu.address.testutil.TypicalPersons.TAYYANG;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonPossessesPolicyPredicate;
import seedu.address.model.policy.Policy;


/**
 * Contains integration tests (interaction with the Model) for {@code FindPolicyholdersCommand}.
 */
public class FindPolicyholdersCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Index firstIndex = INDEX_FIRST_POLICY;
        Index secondIndex = INDEX_SECOND_POLICY;
        FindPolicyholdersCommand findFirstCommand = new FindPolicyholdersCommand(firstIndex);
        FindPolicyholdersCommand findSecondCommand = new FindPolicyholdersCommand(secondIndex);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindPolicyholdersCommand findFirstCommandCopy = new FindPolicyholdersCommand(firstIndex);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPolicyList().size() + 1);
        FindPolicyholdersCommand command = new FindPolicyholdersCommand(outOfBoundIndex);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPolicyAtIndex(model, INDEX_FIRST_POLICY);

        Index outOfBoundIndex = INDEX_SECOND_POLICY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPolicyList().size());

        FindPolicyholdersCommand command = new FindPolicyholdersCommand(outOfBoundIndex);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndex_multiplePeopleFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 5);
        FindPolicyholdersCommand command = new FindPolicyholdersCommand(INDEX_FIRST_POLICY);
        Policy policy = model.getFilteredPolicyList().get(INDEX_FIRST_POLICY.getZeroBased());
        PersonPossessesPolicyPredicate predicate = new PersonPossessesPolicyPredicate(policy);
        expectedModel.updateFilteredPersonList(predicate);
        assertListPeopleCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, ASYRAF, KEITH, ROBIN, TAYYANG), model.getFilteredPersonList());
    }
}
