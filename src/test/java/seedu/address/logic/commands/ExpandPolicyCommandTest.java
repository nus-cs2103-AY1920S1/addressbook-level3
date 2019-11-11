package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertExpandPolicySuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPolicyAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_POLICY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_POLICY;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.policy.Policy;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ExpandPolicyCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_expandsFirstPolicyInNotFilteredList() {
        Policy policy = model.getFilteredPolicyList().get(INDEX_FIRST_POLICY.getZeroBased());
        assertExpandPolicySuccess(new ExpandPolicyCommand(INDEX_FIRST_POLICY),
            model,
            String.format(ExpandPolicyCommand.MESSAGE_SUCCESS, policy), expectedModel, policy);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPolicyAtIndex(model, INDEX_FIRST_POLICY);
        showPolicyAtIndex(expectedModel, INDEX_FIRST_POLICY);
        Policy policy = model.getFilteredPolicyList().get(INDEX_FIRST_POLICY.getZeroBased());
        assertExpandPolicySuccess(new ExpandPolicyCommand(INDEX_FIRST_POLICY),
            model,
            String.format(ExpandPolicyCommand.MESSAGE_SUCCESS, policy), expectedModel, policy);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPolicyList().size() + 1);
        ExpandPolicyCommand expandPolicyCommand = new ExpandPolicyCommand(outOfBoundIndex);

        assertCommandFailure(expandPolicyCommand, model, Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPolicyAtIndex(model, INDEX_FIRST_POLICY);

        Index outOfBoundIndex = INDEX_SECOND_POLICY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPolicyList().size());

        ExpandPolicyCommand expandPolicyCommand = new ExpandPolicyCommand(outOfBoundIndex);

        assertCommandFailure(expandPolicyCommand, model, Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ExpandPolicyCommand expandFirstCommand = new ExpandPolicyCommand(INDEX_FIRST_POLICY);
        ExpandPolicyCommand expandSecondCommand = new ExpandPolicyCommand(INDEX_SECOND_POLICY);

        // same object -> returns true
        assertTrue(expandFirstCommand.equals(expandFirstCommand));

        // same values -> returns true
        ExpandPolicyCommand expandPersonCommandCopy = new ExpandPolicyCommand(INDEX_FIRST_POLICY);
        assertTrue(expandFirstCommand.equals(expandPersonCommandCopy));

        // different types -> returns false
        assertFalse(expandFirstCommand.equals(1));

        // null -> returns false
        assertFalse(expandFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(expandFirstCommand.equals(expandSecondCommand));
    }
}
