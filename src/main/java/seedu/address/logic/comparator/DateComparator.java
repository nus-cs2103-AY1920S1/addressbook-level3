package seedu.address.logic.comparator;

import seedu.address.model.transaction.BankAccountOperation;

import java.util.Comparator;

/**
 * Comparator class for sorting Transactions in ascending order of date
 */
public class DateComparator implements Comparator<BankAccountOperation> {
    @Override
    public int compare(BankAccountOperation o1, BankAccountOperation o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
