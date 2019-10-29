package seedu.address.model.transaction;

import seedu.address.model.person.UniquePersonList;
import seedu.address.model.util.Date;

/**
 * Interface for operations on Ledger
 */
public interface LedgerOperation {
    Amount handleBalance(Amount balance, UniquePersonList peopleInLedger);

    boolean isSameTransaction(BankAccountOperation transaction);

    Date getDate();

    Amount getAmount();

    UniquePersonList getPeopleInvolved();

    Description getDescription();
}
