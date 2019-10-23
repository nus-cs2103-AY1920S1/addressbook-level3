package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.logic.commands.statistics.Type;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {
    private final String feedbackToUser;

    /**
     * Help information should be shown to the user.
     */
    private final boolean showHelp;

    /**
     * The application should exit.
     */
    private final boolean exit;

    private final boolean isQuiz;

    private final boolean showStats;

    private Type type;

    /**
     * Constructs a {@code CommandResult} with fields specified by flags, an integer variable acting
     * as a bit field for four boolean variables.
     *
     * @param flags a non-negative integer whose last four bits represent showHelp (1), exit (2),
     *         isQuiz (4) and showStats (8)
     */
    public CommandResult(String feedbackToUser, int flags) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        assert flags >= 0;

        this.showHelp = (flags & 1) > 0;
        this.exit = (flags & 2) > 0;
        this.isQuiz = (flags & 4) > 0;
        this.showStats = (flags & 8) > 0;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value of <code>false</code>.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, 0);
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

    public boolean isQuiz() {
        return isQuiz;
    }

    public boolean isShowStats() {
        return showStats;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
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
