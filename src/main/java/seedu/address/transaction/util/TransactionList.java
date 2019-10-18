package seedu.address.transaction.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.transaction.model.Transaction;

/**
 * Wraps all data of the transactions into a list.
 * Duplicates are allowed but are considered the same transaction when commands are done.
 */
public class TransactionList {
    private final ArrayList<Transaction> original;
    private ArrayList<Transaction> tArrList;
    private ObservableList<Transaction> tList;

    /**
     * Initialises the transaction list when there are no prior transactions inputted.
     */
    public TransactionList() {
        this.tArrList = new ArrayList<>();
        this.original = new ArrayList<>();
        this.tList = FXCollections.observableList(tArrList);
    }

    /**
     * Initialises the transaction list when there are prior inputted transactions.
     * @param tArrList Array list of the transactions saved.
     */
    public TransactionList(ArrayList<Transaction> tArrList) {
        this.tArrList = tArrList;
        this.original = new ArrayList<>(tArrList);
        this.tList = FXCollections.observableList(this.tArrList);
    }

    public ArrayList<Transaction> gettArrList() {
        return tArrList;
    }

    public ArrayList<Transaction> getOriginal() {
        return original;
    }

    public ObservableList<Transaction> gettList() {
        return tList;
    }

    /**
     * Returns the transaction of given index.
     * @param index Index
     * @return Transaction specified
     * @throws IndexOutOfBoundsException If an error occurs when index is out of bounds.
     */
    public Transaction get(int index) throws IndexOutOfBoundsException {
        if (index >= tList.size()) {
            throw new IndexOutOfBoundsException();
        } else {
            return tList.get(index);
        }
    }

    /**
     * Adds transaction to the transaction list.
     * @param transaction Transaction to be added.
     */
    public void add(Transaction transaction) {
        tList.add(transaction);
        original.add(transaction);
    }

    /**
     * Deletes transaction at the given index.
     * @param index Index of transaction to be deleted.
     */
    public void delete(int index) {
        Transaction transaction = tList.get(index);
        tList.remove(index);
        original.remove(transaction);
    }

    /**
     * Returns integer size of the transaction list.
     * @return Integer size of the number of transactions in the transaction list.
     */
    public int size() {
        return tList.size();
    }

    /**
     * Sets the transaction at the specified index to a new transaction.
     * @param index Index to replace.
     * @param transaction Transaction to replace current transaction at specified index.
     */
    public void set(int index, Transaction transaction) {
        Transaction trans = tList.get(index);
        tList.set(index, transaction);
        int id = original.indexOf(trans);
        original.set(id, transaction);
    }

    /**
     * Resets the order of the transactions in the transaction list to the original order of input when file was read.
     */
    public void unSort() {
        tArrList = new ArrayList<>(original);
        tList = FXCollections.observableList(tArrList);
    }

    /**
     * Sorts all the transactions in the transaction list by date.
     */
    public void sortByDate() {
        Collections.sort(tList, new SortByDate());
    }

    /**
     * Sorts all the transactions in the transaction list by name.
     */
    public void sortByName() {
        Collections.sort(tList, new SortByName());
    }

    /**
     * Sorts all the transactions in the transaction list by amount.
     */
    public void sortByAmount() {
        Collections.sort(tList, new SortByAmount());
    }

    /**
     * Returns a stream of the transactions in the transaction list.
     * @return Stream of transactions.
     */
    public Stream<Transaction> stream() {
        return this.tList.stream();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionList // instanceof handles nulls
                && tArrList.equals(((TransactionList) other).gettArrList())
                && original.equals(((TransactionList) other).getOriginal())
                && tList.equals(((TransactionList) other).gettList()));
    }
}

/**
 * Comparator to compare by the name in transaction.
 */
class SortByName implements Comparator<Transaction> {
    // Used for sorting in ascending order
    @Override
    public int compare(Transaction a, Transaction b) {
        return a.getName().compareTo(b.getName());
    }
}

/**
 * Comparator to compare by amount in transaction.
 */
class SortByAmount implements Comparator<Transaction> {
    // Used for sorting in descending order
    @Override
    public int compare(Transaction a, Transaction b) {
        if (a.getAmount() < b.getAmount()) {
            return 1;
        } else if (a.getAmount() == b.getAmount()) {
            return 0;
        } else {
            return -1;
        }
    }
}

/**
 * Comparator to compare by date in transaction.
 */
class SortByDate implements Comparator<Transaction> {
    // Used for sorting in ascending order
    @Override
    public int compare(Transaction a, Transaction b) {
        return a.getDateObject().compareTo(b.getDateObject());
    }
}
