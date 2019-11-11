package seedu.address.reimbursement.storage;

import java.io.IOException;

import seedu.address.reimbursement.model.ReimbursementList;
import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.storage.exception.FileReadException;

/**
 * Storage interfaces. Defines the methods that a StorageManager has to implement.
 */
public interface Storage {
    ReimbursementList getReimbursementFromFile(TransactionList transList) throws FileReadException;

    void writeFile(ReimbursementList reimbursementList) throws IOException;
}
