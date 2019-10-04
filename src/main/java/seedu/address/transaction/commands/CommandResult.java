package seedu.address.transaction.commands;

public class CommandResult {

    private String feedbackToUser;

    public CommandResult(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
    }

    public String toString() {
        return feedbackToUser;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }
}
