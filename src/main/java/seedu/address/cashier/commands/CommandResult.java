package seedu.address.cashier.commands;

import seedu.address.util.OverallCommandResult;

/**
 * Represents the result of a command execution.
 */
public class CommandResult extends OverallCommandResult {

    private String feedbackToUser;

    /**
     * Constructs a {@code CommandResult} with the specified field.
     */
    public CommandResult(String feedbackToUser) {
        super(feedbackToUser);
    }
}
