package seedu.address.transaction.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTransactions.ALICE_TRANSACTION_1;
import static seedu.address.testutil.TypicalTransactions.ALICE_TRANSACTION_3;
import static seedu.address.testutil.TypicalTransactions.ALICE_TRANSACTION_4;
import static seedu.address.transaction.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_FIND_COMMAND;

import java.util.Arrays;
import java.util.Collections;

import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.ModelManager;
import seedu.address.transaction.model.TransactionContainsKeywordsPredicate;

import org.junit.jupiter.api.Test;

class FindCommandTest {
    private Model model = new ModelManager(TypicalTransactions.getTypicalTransactionList());
    private Model expectedModel = new ModelManager(TypicalTransactions.getTypicalTransactionList());
    private seedu.address.person.model.Model personModel = new
            seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());

    /*@Test
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

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }*/

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_FIND_COMMAND, 0);
        TransactionContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updatePredicate(predicate);
        expectedModel.getFilteredList();
        assertCommandSuccess(command, model, expectedMessage, expectedModel, personModel);
        assertEquals(Collections.emptyList(), model.getFilteredList().gettArrList());
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
                model.getFilteredList().gettArrList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private TransactionContainsKeywordsPredicate preparePredicate(String userInput) {
        String[] nameKeywords = userInput.split("\\s+");
        return new TransactionContainsKeywordsPredicate(Arrays.asList(nameKeywords));
    }
}