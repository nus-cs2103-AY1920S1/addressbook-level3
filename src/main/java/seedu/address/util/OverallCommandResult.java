package seedu.address.util;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class OverallCommandResult {
    private String feedbackToUser;
    /** The application should exit. */
    private boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public OverallCommandResult(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
        this.exit = false;
    }

    public OverallCommandResult(String feedbackToUser, boolean exit) {
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
        if (!(other instanceof OverallCommandResult)) {
            return false;
        }

        OverallCommandResult otherCommandResult = (OverallCommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                //&& showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, /*showHelp,*/ exit);
    }
}
