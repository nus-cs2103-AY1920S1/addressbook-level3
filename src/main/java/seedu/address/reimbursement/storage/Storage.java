package seedu.address.reimbursement.storage;

import seedu.address.reimbursement.model.ReimbursementList;
import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.storage.exception.FileReadWriteException;

/**
 * Storage interfaces. Defines the methods that a StorageManager has to implement.
 */
public interface Storage {
    ReimbursementList getReimbursementFromFile(TransactionList transList) throws FileReadWriteException;

    void writeFile(ReimbursementList reimbursementList) throws Exception;
}
