package seedu.address.transaction.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.model.TransactionContainsKeywordsPredicate;
import seedu.address.transaction.model.exception.NoSuchIndexException;
import seedu.address.transaction.ui.TransactionMessages;

public class TransactionList {
    private ArrayList<Transaction> tList;
    private ArrayList<Transaction> original;
    private ArrayList<Transaction> filteredList;

    public TransactionList() {
         tList = new ArrayList<>();
         original = new ArrayList<>();
         filteredList = new ArrayList<>();
    }

    public TransactionList(ArrayList<Transaction> tList) {
        this.tList = tList;
        this.original = new ArrayList<>(tList);
        this.filteredList = new ArrayList<>(tList);
    }


    public Transaction get(int index) throws NoSuchIndexException {
        if (index >= tList.size()) {
            throw new NoSuchIndexException(TransactionMessages.NO_SUCH_INDEX_TRANSACTION);
        } else {
            return tList.get(index);
        }
    }

    public void add(Transaction transaction) {
        tList.add(transaction);
        original.add(transaction);
        filteredList.add(transaction);
    }

    public void delete(int index) {
        Transaction transaction = tList.get(index);
        tList.remove(index);
        original.remove(transaction);
        filteredList.remove(transaction);
        /*for (Transaction t: original) {
            if (t.equals(transaction)) {
                original.remove(t);
            }
        }*/
    }

    public int size() {
        return tList.size();
    }

    public void set(int i, Transaction transaction) {
        tList.set(i, transaction);
        filteredList.set(i, transaction);
    }

    public void unSort() {
        tList = original;
    }

    public void sortByDate() {
        Collections.sort(tList, new SortByDate());
    }

    public void sortByName() {
        Collections.sort(tList, new SortByName());
    }

    public void sortByAmount() {
        Collections.sort(tList, new SortByAmount());
    }

    public void updateFilteredTransactionList(TransactionContainsKeywordsPredicate predicate) {
        filteredList.stream().filter(predicate);
    }

    public void resetFilteredList() {
        filteredList = new ArrayList<>(tList);
    }
}

class SortByName implements Comparator<Transaction> {
    // Used for sorting in ascending order
    public int compare(Transaction a, Transaction b) {
        return a.getName().compareTo(b.getName());
    }
}

class SortByAmount implements Comparator<Transaction> {
    // Used for sorting in descending order
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

class SortByDate implements Comparator<Transaction> {
    // Used for sorting in ascending order
    public int compare(Transaction a, Transaction b) {
        return a.getDateObject().compareTo(b.getDateObject());
    }
}
