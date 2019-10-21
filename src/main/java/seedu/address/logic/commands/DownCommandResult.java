package seedu.address.logic.commands;

public class DownCommandResult extends CommandResult {

    private String pane;

    public DownCommandResult(String feedbackToUser, String pane) {
        super(feedbackToUser);
        this.pane = pane;
    }

    public String getPane() {
        return this.pane;
    }
}
