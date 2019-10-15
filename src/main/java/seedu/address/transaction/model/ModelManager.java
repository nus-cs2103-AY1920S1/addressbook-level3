package seedu.address.transaction.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.model.person.Person;
import seedu.address.transaction.util.TransactionList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private final Logger logger = LogsCenter.getLogger(getClass());
    private final TransactionList transactionList;
    private TransactionList filteredList;
    private Predicate<Transaction> predicate;

    /**
     * Initializes a ModelManager with the given transaction list.
     */
    public ModelManager(TransactionList transactionList) {
        this.transactionList = transactionList;
        ArrayList<Transaction> actualList = new ArrayList<>();
        for (int i = 0; i < transactionList.size(); i++) {
            actualList.add(transactionList.get(i));
        }
        this.filteredList = new TransactionList(actualList);
        this.predicate = transaction -> true;
    }

    @Override
    public TransactionList getTransactionList() {
        return this.transactionList;
    }

    @Override
    public void setTransaction(Transaction transactionToEdit, Transaction editedTransaction) {
        for (int i = 0; i < transactionList.size(); i++) {
            Transaction curr = transactionList.get(i);
            if (curr.equals(transactionToEdit)) {
                transactionList.set(i, editedTransaction);
            }
        }
    }

    @Override
    public boolean hasTransaction(Transaction editedTransaction) {
        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).equals(editedTransaction)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addTransaction(Transaction trans) {
        transactionList.add(trans);
    }

    @Override
    public Transaction findTransactionInFilteredListByIndex(int index) {
        logger.info("size of filtered list: " + filteredList.size());
        Transaction transaction = filteredList.get(index - 1);
        //Transaction transaction = transactionList.get(index - 1);
        logger.info("transaction found: " + transaction.toString());
        return transaction;
    }

    @Override
    public void deleteTransaction(int index) {
        Transaction transaction = filteredList.get(index - 1);
        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).equals(transaction)) {
                transactionList.delete(i);
            }
        }
    }

    @Override
    public void updateIndexes() {
        for (int i = 0; i < transactionList.size(); i++) {
            transactionList.get(i).setId(i + 1);
        }
    }

    @Override
    public void sortByDate() {
        transactionList.sortByDate();
    }

    @Override
    public void sortByName() {
        transactionList.sortByName();
    }

    @Override
    public void sortByAmount() {
        transactionList.sortByAmount();
    }

    @Override
    public void sortReset() {
        transactionList.unSort();
    }

    @Override
    public void resetPredicate() {
        this.predicate = transaction -> true;
    }

    @Override
    public void deleteAllTransactionOfPerson(Person person) {
        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).getPerson().equals(person)) {
                logger.info(person.toString());
                transactionList.delete(i);
                i--;
            }
        }
    }

    @Override
    public TransactionList getFilteredList() {
        List<Transaction> list = this.transactionList.stream().filter(predicate).collect(Collectors.toList());
        ArrayList<Transaction> arrayList = new ArrayList<Transaction>(list);
        this.filteredList = new TransactionList(arrayList);
        return filteredList;
    }

    @Override
    public void updatePredicate(TransactionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

}
