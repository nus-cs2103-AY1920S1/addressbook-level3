package seedu.address.transaction.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTransactions.ALICE_TRANSACTION_1;
import static seedu.address.testutil.TypicalTransactions.ALICE_TRANSACTION_3;
import static seedu.address.testutil.TypicalTransactions.ALICE_TRANSACTION_4;
import static seedu.address.transaction.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_FIND_COMMAND;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.transaction.TransactionContainsKeywordsPredicate;

class FindCommandTest {
    private Model model =
            new seedu.address.transaction.model.ModelManager(TypicalTransactions.getTypicalTransactionList());
    private Model expectedModel =
            new seedu.address.transaction.model.ModelManager(TypicalTransactions.getTypicalTransactionList());
    private CheckAndGetPersonByNameModel personModel = new
            ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_FIND_COMMAND, 0);
        TransactionContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updatePredicate(predicate);
        expectedModel.getFilteredList();
        assertCommandSuccess(command, model, expectedMessage, expectedModel, personModel);
        assertEquals(Collections.emptyList(), model.getFilteredList().getTarrList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_FIND_COMMAND, 3);
        TransactionContainsKeywordsPredicate transactionPredicate = preparePredicate("alice");

        FindCommand command = new FindCommand(transactionPredicate);
        expectedModel.updatePredicate(transactionPredicate);
        expectedModel.getFilteredList();
        assertCommandSuccess(command, model, expectedMessage, expectedModel, personModel);
        assertEquals(Arrays.asList(ALICE_TRANSACTION_1, ALICE_TRANSACTION_3, ALICE_TRANSACTION_4),
                model.getFilteredList().getTarrList());
    }

    @Test
    public void equals() {
        TransactionContainsKeywordsPredicate firstPredicate =
                new TransactionContainsKeywordsPredicate(Collections.singletonList("first"));
        TransactionContainsKeywordsPredicate secondPredicate =
                new TransactionContainsKeywordsPredicate(Collections.singletonList("second"));

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

        // different find command -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    /**
     * Parses {@code userInput} into a {@code TransactionContainsKeywordsPredicate}.
     */
    private TransactionContainsKeywordsPredicate preparePredicate(String userInput) {
        String[] nameKeywords = userInput.split("\\s+");
        return new TransactionContainsKeywordsPredicate(Arrays.asList(nameKeywords));
    }
}
