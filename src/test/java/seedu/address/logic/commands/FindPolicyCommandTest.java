package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_POLICIES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertListPolicyCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPolicy.LIFE_INSURANCE;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.policy.PolicyNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindPolicyCommand}.
 */
public class FindPolicyCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PolicyNameContainsKeywordsPredicate firstPredicate =
                new PolicyNameContainsKeywordsPredicate(Collections.singletonList("first"));
        PolicyNameContainsKeywordsPredicate secondPredicate =
                new PolicyNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindPolicyCommand findFirstCommand = new FindPolicyCommand(firstPredicate);
        FindPolicyCommand findSecondCommand = new FindPolicyCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindPolicyCommand findFirstCommandCopy = new FindPolicyCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPolicyFound() {
        String expectedMessage = String.format(MESSAGE_POLICIES_LISTED_OVERVIEW, 0);
        PolicyNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindPolicyCommand command = new FindPolicyCommand(predicate);
        expectedModel.updateFilteredPolicyList(predicate);
        assertListPolicyCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPolicyList());
    }

    @Test
    public void execute_singleKeyword_singlePolicyFound() {
        String expectedMessage = String.format(MESSAGE_POLICIES_LISTED_OVERVIEW, 1);
        PolicyNameContainsKeywordsPredicate predicate = preparePredicate("Life");
        FindPolicyCommand command = new FindPolicyCommand(predicate);
        expectedModel.updateFilteredPolicyList(predicate);
        assertListPolicyCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(LIFE_INSURANCE), model.getFilteredPolicyList());
    }

    /**
     * Parses {@code userInput} into a {@code PolicyNameContainsKeywordsPredicate}.
     */
    private PolicyNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new PolicyNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
