package seedu.address.reimbursement.storage;

import seedu.address.reimbursement.model.ReimbursementList;

/**
 * Storage interfaces. Defines the methods that a StorageManager has to implement.
 */
public interface Storage {
    public ReimbursementList readReimbursementList() throws Exception;

    public void writeFile(ReimbursementList reimbursementList) throws Exception;
}
