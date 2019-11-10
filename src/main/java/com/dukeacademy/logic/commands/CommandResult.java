package com.dukeacademy.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;


/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** The application should exit. */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     * @param feedbackToUser the feedback to user
     * @param exit           the exit
     */
    public CommandResult(String feedbackToUser, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified
     * {@code feedbackToUser}, and other fields set to their default value.
     *
     * @param feedbackToUser the feedback to user
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false);
    }

    /**
     * Gets feedback to user.
     *
     * @return the feedback to user
     */
    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    /**
     * Is exit boolean.
     *
     * @return the boolean
     */
    public boolean isExit() {
        return exit;
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
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, exit);
    }

}
