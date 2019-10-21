package seedu.moneygowhere.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** The application should exit. */
    private final boolean exit;

    /** Graph information should be shown to the user. */
    private final boolean showGraph;

    /** Stats information should be shown to the user. */
    private final boolean showStats;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showGraph, boolean showStats, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showGraph = showGraph;
        this.showStats = showStats;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isShowGraph() {
        return showGraph;
    }

    public boolean isShowStats() {
        return showStats;
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
            && showGraph == otherCommandResult.showGraph
            && showStats == otherCommandResult.showStats
            && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showGraph, showStats, exit);
    }
}
