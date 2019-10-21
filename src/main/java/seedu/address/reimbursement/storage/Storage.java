package seedu.address.reimbursement.storage;

import seedu.address.reimbursement.model.ReimbursementList;
import seedu.address.transaction.util.TransactionList;

/**
 * Storage interfaces. Defines the methods that a StorageManager has to implement.
 */
public interface Storage {
    ReimbursementList getReimbursementFromFile(TransactionList transList) throws Exception;

    void writeFile(ReimbursementList reimbursementList) throws Exception;
}
