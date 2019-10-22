package seedu.address.model.transaction;

import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.util.Date;

/**
 * Interface to differentiate between operations on BankAccount and Ledger
 */
public interface BankAccountOperation {
    Amount handleBalance(Amount balance);

    Amount getAmount();

    Set<Tag> getTags();

    boolean isSameTransaction(BankAccountOperation transaction);

    Date getDate();
}
