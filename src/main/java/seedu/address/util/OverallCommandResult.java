package seedu.address.util;

/**
 * Represents the result of a command execution.
 */
public class OverallCommandResult {
    private String feedbackToUser;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public OverallCommandResult(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
    }

    /**
     * Returns the feedback to the user.
     *
     * @return feedback to user.
     */
    public String getFeedbackToUser() {
        return feedbackToUser;
    }
}
