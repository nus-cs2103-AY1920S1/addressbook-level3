package seedu.address.transaction.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

import seedu.address.transaction.model.comparators.SortByAmount;
import seedu.address.transaction.model.comparators.SortByDate;
import seedu.address.transaction.model.comparators.SortByName;
import seedu.address.transaction.model.transaction.Transaction;

/**
 * Wraps all data of the transactions into a list.
 * Duplicates are allowed but are considered the same transaction when commands are done.
 */
public class TransactionList {
    private final ArrayList<Transaction> original;
    private ArrayList<Transaction> tArrList;
    private boolean isModifiable;

    /**
     * Initialises the transaction list when there are no prior transactions inputted.
     */
    public TransactionList() {
        this.tArrList = new ArrayList<>();
        this.original = new ArrayList<>();
        this.isModifiable = true;
    }

    /**
     * Initialises the transaction list when there are prior inputted transactions.
     * @param tArrList Array list of the transactions saved.
     */
    public TransactionList(ArrayList<Transaction> tArrList) {
        this.tArrList = tArrList;
        this.original = new ArrayList<>(tArrList);
        this.isModifiable = true;
    }

    public void setAsUnmodifiable() {
        isModifiable = false;
    }

    public ArrayList<Transaction> getTarrList() {
        return tArrList;
    }

    public ArrayList<Transaction> getOriginal() {
        return original;
    }

    /**
     * Returns the transaction of given index.
     * @param index Index
     * @return Transaction specified
     * @throws IndexOutOfBoundsException If an error occurs when index is out of bounds.
     */
    public Transaction get(int index) throws IndexOutOfBoundsException {
        if (index >= tArrList.size()) {
            throw new IndexOutOfBoundsException();
        } else {
            return tArrList.get(index);
        }
    }

    /**
     * Adds transaction to the transaction list.
     * @param transaction Transaction to be added.
     */
    public void add(Transaction transaction) throws UnsupportedOperationException, NullPointerException {
        if (transaction == null) {
            throw new NullPointerException();
        }
        if (isModifiable) {
            tArrList.add(transaction);
            original.add(transaction);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Deletes transaction at the given index.
     * @param index Index of transaction to be deleted.
     */
    public void delete(int index) throws UnsupportedOperationException {
        if (isModifiable) {
            Transaction transaction = tArrList.get(index);
            tArrList.remove(index);
            original.remove(transaction);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns integer size of the transaction list.
     * @return Integer size of the number of transactions in the transaction list.
     */
    public int size() {
        return tArrList.size();
    }

    /**
     * Sets the transaction at the specified index to a new transaction.
     * @param index Index to replace.
     * @param transaction Transaction to replace current transaction at specified index.
     */
    public void set(int index, Transaction transaction) throws UnsupportedOperationException, NullPointerException {
        if (transaction == null) {
            throw new NullPointerException();
        }
        if (isModifiable) {
            Transaction trans = tArrList.get(index);
            tArrList.set(index, transaction);
            int id = original.indexOf(trans);
            original.set(id, transaction);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Resets the order of the transactions in the transaction list to the original order of input when file was read.
     */
    public void unSort() {
        tArrList = new ArrayList<>(original);
    }

    /**
     * Sorts all the transactions in the transaction list by date.
     */
    public void sortByDate() {
        Collections.sort(tArrList, new SortByDate());
    }

    /**
     * Sorts all the transactions in the transaction list by name.
     */
    public void sortByName() {
        Collections.sort(tArrList, new SortByName());
    }

    /**
     * Sorts all the transactions in the transaction list by amount.
     */
    public void sortByAmount() {
        Collections.sort(tArrList, new SortByAmount());
    }

    /**
     * Returns a stream of the transactions in the transaction list.
     * @return Stream of transactions.
     */
    public Stream<Transaction> stream() {
        return this.tArrList.stream();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionList // instanceof handles nulls
                && tArrList.equals(((TransactionList) other).getTarrList())
                && original.equals(((TransactionList) other).getOriginal()));
    }
}

