package seedu.address.logic.commands;

public class UpCommandResult extends CommandResult {

    private String pane;

    public UpCommandResult(String feedbackToUser, String pane) {
        super(feedbackToUser);
        this.pane = pane;
    }

    public String getPane() {
        return this.pane;
    }
}
