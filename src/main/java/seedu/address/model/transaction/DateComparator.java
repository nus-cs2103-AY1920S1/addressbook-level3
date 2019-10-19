package seedu.address.model.transaction;

import java.util.Comparator;

/**
 * Comparator class for sorting Transactions in ascending order of date
 */
public class DateComparator implements Comparator<Transaction> {
    @Override
    public int compare(Transaction o1, Transaction o2) {
        return o1.getDate().toLocalDate().compareTo(o2.getDate().toLocalDate());
    }
}
