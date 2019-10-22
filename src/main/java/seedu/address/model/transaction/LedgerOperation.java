package seedu.address.model.transaction;

import seedu.address.model.person.UniquePersonList;

public interface LedgerOperation {
    public Amount handleBalance(Amount balance, UniquePersonList peopleInLedger);
}
