package seedu.address.transaction.model.comparators;

import java.util.Comparator;

import seedu.address.transaction.model.transaction.Transaction;

/**
 * Comparator to compare by the name in transaction.
 */
public class SortByName implements Comparator<Transaction> {
    // Used for sorting in alphabetical order, case-insensitive
    @Override
    public int compare(Transaction a, Transaction b) {
        return a.getName().toUpperCase().compareTo(b.getName().toUpperCase());
    }
}
