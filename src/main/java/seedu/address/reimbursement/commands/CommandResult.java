package seedu.address.reimbursement.commands;

import seedu.address.util.OverallCommandResult;

/**
 * Represents a reimbursement command.
 */
public class CommandResult extends OverallCommandResult {

    public CommandResult(String feedbackToUser) {
        super(feedbackToUser);
    }

    public CommandResult(String feedbackToUser, boolean exit) {
        super(feedbackToUser, exit);
    }
}
