package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTransactions.getTypicalUserState;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.transaction.TransactionContainsCategoriesPredicate;
import seedu.address.ui.tab.Tab;


/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {

    private Model model = new ModelManager(getTypicalUserState(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalUserState(), new UserPrefs());

    @Test
    public void equals() {
        TransactionContainsCategoriesPredicate firstPredicate =
            new TransactionContainsCategoriesPredicate(Collections.singletonList("first"));
        TransactionContainsCategoriesPredicate secondPredicate =
            new TransactionContainsCategoriesPredicate(Collections.singletonList("second"));

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand findFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different command -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    // TODO: more testcases
    @Test
    public void execute_zeroKeywords_noTransactionFound() {
        String expectedMessage = String.format(MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 0);
        TransactionContainsCategoriesPredicate predicate = preparePredicate(" ");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredTransactionList(predicate);
        expectedModel.commitUserState();
        assertCommandSuccess(command, model,
            new CommandResult(expectedMessage, false, false, Tab.TRANSACTION), expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTransactionList());
    }

    /**
     * Parses {@code userInput} into a {@code TransactionContainsCategoriesPredicate}.
     */
    private TransactionContainsCategoriesPredicate preparePredicate(String userInput) {
        return new TransactionContainsCategoriesPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
