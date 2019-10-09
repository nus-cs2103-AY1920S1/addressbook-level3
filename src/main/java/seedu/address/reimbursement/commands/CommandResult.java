package seedu.address.reimbursement.commands;

import seedu.address.util.OverallCommandResult;

/**
 * Represents a reimbursement command.
 */
public class CommandResult extends OverallCommandResult {

    private String feedbackToUser;

    public CommandResult(String feedbackToUser) {
        super(feedbackToUser);
    }
}
