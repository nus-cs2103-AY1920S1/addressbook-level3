package seedu.address.transaction.model.comparators;

import java.util.Comparator;

import seedu.address.transaction.model.transaction.Transaction;

/**
 * Comparator to compare by amount in transaction.
 */
public class SortByAmount implements Comparator<Transaction> {
    // Used for sorting in ascending order
    @Override
    public int compare(Transaction a, Transaction b) {
        if (a.getAmount() < b.getAmount()) {
            return -1;
        } else if (a.getAmount() == b.getAmount()) {
            return 0;
        } else {
            return 1;
        }
    }
}
