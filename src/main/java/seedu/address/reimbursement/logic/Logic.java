package seedu.address.reimbursement.logic;

import seedu.address.reimbursement.commands.CommandResult;
import seedu.address.reimbursement.model.ReimbursementList;

/**
 * Logic interface. Defines the methods for a logic manager.
 */
public interface Logic {
    //command execution
    CommandResult execute(String commandText) throws Exception;

    //get list
    ReimbursementList getReimbursementList();

    ReimbursementList getFilteredList();

}
