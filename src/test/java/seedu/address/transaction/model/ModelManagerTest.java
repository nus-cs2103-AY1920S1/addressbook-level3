package seedu.address.transaction.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.transaction.logic.commands.CommandTestUtil.VALID_CATEGORY;
import static seedu.address.transaction.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.transaction.logic.commands.CommandTestUtil.VALID_DESC;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.Assert;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.transaction.Transaction;
import seedu.address.transaction.model.transaction.TransactionContainsKeywordsPredicate;

class ModelManagerTest {
    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new TransactionList(), new TransactionList(modelManager.getTransactionList().getTarrList()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ModelManager(null));
    }

    @Test
    public void addTransaction_nullInput_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.addTransaction(null));
    }

    @Test
    public void setTransaction_successful() {
        Transaction transaction = TypicalTransactions.ALICE_TRANSACTION_3;
        Transaction newTransaction =
                new Transaction(VALID_DATE, VALID_DESC, VALID_CATEGORY, 12, TypicalPersons.DANIEL,
                        0, false);
        ModelManager filledModelManager = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        filledModelManager.setTransaction(transaction, newTransaction);
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(TypicalTransactions.ALICE_TRANSACTION_1);
        transactions.add(TypicalTransactions.BENSON_TRANSACTION_2);
        transactions.add(newTransaction);
        transactions.add(TypicalTransactions.ALICE_TRANSACTION_4);
        transactions.add(TypicalTransactions.CARL_TRANSACTION_5);
        transactions.add(TypicalTransactions.ELLE_TRANSACTION_6);
        transactions.add(TypicalTransactions.GEORGE_TRANSACTION_7);
        transactions.add(TypicalTransactions.FIONA_TRANSACTION_8);
        TransactionList transactionList = new TransactionList(transactions);
        ModelManager expectedModelManager = new ModelManager(transactionList);
        assertEquals(expectedModelManager, filledModelManager);
    }

    @Test
    public void hasTransaction_foundTransaction_returnTrue() {
        ModelManager filledModelManager = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        assertTrue(filledModelManager.hasTransaction(TypicalTransactions.ALICE_TRANSACTION_1));
        assertTrue(filledModelManager.hasTransaction(TypicalTransactions.ALICE_TRANSACTION_3));

        assertTrue(filledModelManager.hasTransaction(TypicalTransactions.BENSON_TRANSACTION_2));
        assertTrue(filledModelManager.hasTransaction(TypicalTransactions.GEORGE_TRANSACTION_7));

        assertTrue(filledModelManager.hasTransaction(TypicalTransactions.FIONA_TRANSACTION_8));
        assertTrue(filledModelManager.hasTransaction(TypicalTransactions.ELLE_TRANSACTION_6));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertFalse(modelManager.hasTransaction(null));
    }

    @Test
    public void hasTransaction_transactionNotFound_returnFalse() {
        ModelManager filledModelManager = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        assertFalse(filledModelManager.hasTransaction(TypicalTransactions.DANIEL_TRANSACTION_9));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredList().delete(0));
    }

    @Test
    public void deleteTransactionByIndexFilteredList_successful() {
        ModelManager filledModelManager = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        filledModelManager.updatePredicate(new TransactionContainsKeywordsPredicate(Arrays.asList(keywords)));
        System.out.println(filledModelManager.getFilteredList().get(2));
        filledModelManager.deleteTransaction(2);
        ModelManager expectedModelManager = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        System.out.println(expectedModelManager.getFilteredList().get(3));
        expectedModelManager.deleteTransaction(3);
        assertEquals(filledModelManager.getTransactionList(), expectedModelManager.getTransactionList());
        assertNotEquals(filledModelManager.getFilteredList(), expectedModelManager.getFilteredList());
        assertNotEquals(filledModelManager, expectedModelManager);

        filledModelManager.resetPredicate();
        assertEquals(filledModelManager.getFilteredList(), expectedModelManager.getFilteredList());
        assertEquals(filledModelManager, expectedModelManager);

    }

    @Test
    public void hasTransactionWithName_hasName_returnTrue() {
        ModelManager filledModelManager = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        assertTrue(filledModelManager.hasTransactionWithName(ALICE.getName().toString()));
        assertTrue(filledModelManager.hasTransactionWithName(TypicalPersons.CARL.getName().toString()));
    }

    @Test
    public void hasTransactionWithName_noSuchName_returnFalse() {
        ModelManager filledModelManager = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        assertFalse(filledModelManager.hasTransactionWithName(TypicalPersons.DANIEL.getName().toString()));
    }

    @Test
    public void equals() {
        // same values -> returns true
        modelManager = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        ModelManager modelManagerCopy = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager()));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updatePredicate(new TransactionContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager()));

        // different predicate -> returns false
        assertFalse(modelManager.equals(new ModelManager()));
    }

}
