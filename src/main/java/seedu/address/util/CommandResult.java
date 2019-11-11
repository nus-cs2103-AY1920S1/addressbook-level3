package seedu.address.util;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {
    private String feedbackToUser;
    /** The application should exit. */
    private boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
        this.exit = false;
    }

    public CommandResult(String feedbackToUser, boolean exit) {
        this.feedbackToUser = feedbackToUser;
        this.exit = exit;
    }

    /**
     * Returns the feedback to the user.
     *
     * @return feedback to user.
     */
    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isExit() {
        return this.exit;
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
        return feedbackToUser.equals(otherCommandResult.getFeedbackToUser())
                //&& showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, /*showHelp,*/ exit);
    }
}
