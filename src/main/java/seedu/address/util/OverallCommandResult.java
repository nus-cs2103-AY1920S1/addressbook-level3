package seedu.address.util;

public class OverallCommandResult {
    private String feedbackToUser;

    public OverallCommandResult(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
    }

    public String toString() {
        return feedbackToUser;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }
}
