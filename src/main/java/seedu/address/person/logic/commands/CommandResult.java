package seedu.address.person.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.util.OverallCommandResult;

/**
 * Represents the result of a command execution.
 */
public class CommandResult extends OverallCommandResult {

    private String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */


    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        super(requireNonNull(feedbackToUser), exit);
        this.showHelp = showHelp;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    /*public boolean isShowHelp() {
        return showHelp;
    }*/
}
