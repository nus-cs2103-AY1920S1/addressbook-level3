package seedu.address.logic.commands;

/**
 * A class to identify when a command result is an up command.
 */
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
