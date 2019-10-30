package seedu.system.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** Command type that produced the command result. */
    private final CommandType commandType;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, CommandType commandType) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.commandType = commandType;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        this.feedbackToUser = feedbackToUser;
        this.showHelp = false;
        this.exit = false;
        this.commandType = CommandType.GENERAL;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and  {@code commandType},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, CommandType commandType) {
        requireNonNull(feedbackToUser);
        this.feedbackToUser = feedbackToUser;
        this.showHelp = false;
        this.exit = false;
        this.commandType = commandType;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

}
