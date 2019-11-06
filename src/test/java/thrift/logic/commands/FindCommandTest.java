package thrift.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.commons.core.Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW;
import static thrift.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import thrift.model.Model;
import thrift.model.ModelManager;
import thrift.model.UserPrefs;
import thrift.model.transaction.DescriptionOrRemarkContainsKeywordsPredicate;
import thrift.testutil.TypicalTransactions;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(TypicalTransactions.getTypicalThrift(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalTransactions.getTypicalThrift(), new UserPrefs());

    @Test
    public void equals() {
        DescriptionOrRemarkContainsKeywordsPredicate firstPredicate =
                new DescriptionOrRemarkContainsKeywordsPredicate(Collections.singletonList("first"));
        DescriptionOrRemarkContainsKeywordsPredicate secondPredicate =
                new DescriptionOrRemarkContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different transaction -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noTransactionFound() {
        String expectedMessage = String.format(MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 0);
        DescriptionOrRemarkContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTransactionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTransactionList());
    }

    @Test
    public void execute_multipleKeywordsDescriptionField_transactionsFound() {
        String expectedMessage = String.format(MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 1);
        DescriptionOrRemarkContainsKeywordsPredicate predicate = preparePredicate("Penang Laksa1");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTransactionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalTransactions.PENANG_LAKSA), model.getFilteredTransactionList());
    }

    @Test
    public void execute_multipleKeywordsRemarkField_transactionsFound() {
        String expectedMessage = String.format(MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 2);
        DescriptionOrRemarkContainsKeywordsPredicate predicate = preparePredicate("the best");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTransactionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalTransactions.LAKSA, TypicalTransactions.PENANG_LAKSA),
                model.getFilteredTransactionList());
    }

    /**
     * Parses {@code userInput} into a {@code DescriptionOrRemarkContainsKeywordsPredicate}.
     */
    private DescriptionOrRemarkContainsKeywordsPredicate preparePredicate(String userInput) {
        return new DescriptionOrRemarkContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
