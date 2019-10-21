package seedu.address.model.transaction;

import java.util.Comparator;

/**
 * Comparator class for sorting Transactions in ascending order of amount
 */
public class AmountComparator implements Comparator<Transaction> {
    @Override
    public int compare(Transaction t1, Transaction t2) {
        return t1.getAmount().compareTo(t2.getAmount());
    }
}
