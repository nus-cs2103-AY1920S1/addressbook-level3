package seedu.address.logic.comparator;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;

import seedu.address.model.transaction.BankAccountOperation;

/**
 * Comparator class for sorting Transactions in ascending order of date
 */
public class DateComparator implements Comparator<BankAccountOperation> {
    @Override
    public int compare(BankAccountOperation o1, BankAccountOperation o2) {
        requireAllNonNull(o1, o2);
        return o1.getDate().compareTo(o2.getDate());
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
            || obj instanceof DateComparator;
    }
}
