package seedu.address.logic.comparator;

import java.util.Comparator;

import seedu.address.model.transaction.BankAccountOperation;

/**
 * Comparator class for sorting Transactions in ascending order of amount
 */
public class AmountComparator implements Comparator<BankAccountOperation> {
    @Override
    public int compare(BankAccountOperation t1, BankAccountOperation t2) {
        return t1.getAmount().compareTo(t2.getAmount());
    }
}
