package seedu.address.person.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.util.OverallCommandResult;

/**
 * Represents the result of a command execution.
 */
public class CommandResult extends OverallCommandResult {

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean exit) {
        super(requireNonNull(feedbackToUser), exit);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false);
    }

}
