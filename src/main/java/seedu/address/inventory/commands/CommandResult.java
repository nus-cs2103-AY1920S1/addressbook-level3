package seedu.address.inventory.commands;

import seedu.address.util.OverallCommandResult;

/**
 * Represents the result of a command execution.
 */
public class CommandResult extends OverallCommandResult {

    private String feedbackToUser;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser) {
        super(feedbackToUser);
    }
}
