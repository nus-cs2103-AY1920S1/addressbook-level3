package seedu.address.model.transaction;

import java.util.List;

import seedu.address.model.person.UniquePersonList;
import seedu.address.model.util.Date;

/**
 * Interface for operations on Ledger
 */
public interface LedgerOperation {
    Amount handleBalance(Amount balance, UniquePersonList peopleInLedger);

    boolean isSameLedgerOperation(LedgerOperation ledgerOperation);

    Date getDate();

    Amount getAmount();

    UniquePersonList getPeopleInvolved();

    Description getDescription();

    List<Integer> getShares();

    List<Amount> getAmounts();
}
