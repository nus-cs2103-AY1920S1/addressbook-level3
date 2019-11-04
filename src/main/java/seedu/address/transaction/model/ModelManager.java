package seedu.address.transaction.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.model.person.Person;
import seedu.address.transaction.model.transaction.Transaction;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model, AddTransactionOnlyModel {
    private final Logger logger = LogsCenter.getLogger(getClass());
    private final TransactionList transactionList;
    private TransactionList filteredList;
    private Predicate<Transaction> predicate;

    /**
     * Initializes a ModelManager with the given transaction list.
     */
    public ModelManager(TransactionList transactionList) {
        requireNonNull(transactionList);
        this.transactionList = transactionList;
        ArrayList<Transaction> actualList = new ArrayList<>();
        for (int i = 0; i < transactionList.size(); i++) {
            actualList.add(transactionList.get(i));
        }
        this.filteredList = new TransactionList(actualList);
        this.filteredList.setAsUnmodifiable();
        this.predicate = transaction -> true;
    }

    public ModelManager() {
        this.transactionList = new TransactionList();
        this.filteredList = new TransactionList();
        this.filteredList.setAsUnmodifiable();
        this.predicate = transaction -> true;
    }

    @Override
    public TransactionList getTransactionList() {
        return this.transactionList;
    }

    @Override
    public Predicate<Transaction> getPredicate() {
        return this.predicate;
    }

    @Override
    public void setTransaction(Transaction transactionToEdit, Transaction editedTransaction) {
        boolean isTransactionToEditFound = false;
        for (int i = 0; i < transactionList.size(); i++) {
            Transaction curr = transactionList.get(i);
            if (curr.equals(transactionToEdit)) {
                isTransactionToEditFound = true;
                transactionList.set(i, editedTransaction);
            }
        }
        assert isTransactionToEditFound : "The transaction to edit was not found.";
        filteredList = getFilteredList();
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
        requireNonNull(trans);
        transactionList.add(trans);
        filteredList = getFilteredList();
    }

    @Override
    public Transaction findTransactionInFilteredListByIndex(int index) {
        logger.info("size of filtered list: " + filteredList.size());
        logger.info("index inputted: " + index);
        Transaction transaction = filteredList.get(index - 1);
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
        filteredList = getFilteredList();
    }

    @Override
    public void updateIndexes() {
        for (int i = 0; i < transactionList.size(); i++) {
            transactionList.get(i).setId(i + 1);
        }
        filteredList = getFilteredList();
    }

    @Override
    public void sortByDate() {
        transactionList.sortByDate();
        filteredList.sortByDate();
    }

    @Override
    public void sortByName() {
        transactionList.sortByName();
        filteredList.sortByName();
    }

    @Override
    public void sortByAmount() {
        transactionList.sortByAmount();
        filteredList.sortByAmount();
    }

    @Override
    public void sortReset() {
        transactionList.unSort();
        filteredList.unSort();
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
        filteredList = getFilteredList();
    }

    @Override
    public TransactionList getFilteredList() {
        List<Transaction> list = this.transactionList.stream().filter(predicate).collect(Collectors.toList());
        ArrayList<Transaction> arrayList = new ArrayList<>(list);
        this.filteredList = new TransactionList(arrayList);
        this.filteredList.setAsUnmodifiable();
        return filteredList;
    }

    @Override
    public void updatePredicate(Predicate<Transaction> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean hasTransactionWithName(String name) {
        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).getPerson().getName().toString().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return transactionList.equals(other.getTransactionList())
                && filteredList.equals(other.getFilteredList());
    }

}
