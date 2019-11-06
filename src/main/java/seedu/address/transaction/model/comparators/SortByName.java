package seedu.address.transaction.model.comparators;

import java.util.Comparator;

import seedu.address.transaction.model.transaction.Transaction;

/**
 * Comparator to compare by the name in transaction.
 */
public class SortByName implements Comparator<Transaction> {
    // Used for sorting in ascending order
    @Override
    public int compare(Transaction a, Transaction b) {
        return a.getName().compareTo(b.getName());
    }
}
