package seedu.address.transaction.model.comparators;

import java.util.Comparator;

import seedu.address.transaction.model.transaction.Transaction;

/**
 * Comparator to compare by date in transaction.
 */
public class SortByDate implements Comparator<Transaction> {
    // Used for sorting in form oldest to most recent
    @Override
    public int compare(Transaction a, Transaction b) {
        return a.getDateObject().compareTo(b.getDateObject());
    }
}
