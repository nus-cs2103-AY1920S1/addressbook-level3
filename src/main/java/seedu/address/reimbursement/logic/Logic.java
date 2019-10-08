package seedu.address.reimbursement.logic;

import seedu.address.reimbursement.commands.CommandResult;
import seedu.address.reimbursement.model.Reimbursement;
import seedu.address.reimbursement.model.ReimbursementList;

public interface Logic {

    CommandResult execute(String commandText) throws Exception;

    ReimbursementList getReimbursementListFromFile() throws Exception;

    void writeIntoReimbursementFile() throws Exception;

    ReimbursementList getReimbursementList();
}
