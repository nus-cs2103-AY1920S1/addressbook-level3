package seedu.address.reimbursement.storage;

import seedu.address.reimbursement.model.ReimbursementList;

public interface Storage {
    public ReimbursementList getReimbursementList() throws Exception;

    public void writeFile(ReimbursementList reimbursementList) throws Exception;
}
