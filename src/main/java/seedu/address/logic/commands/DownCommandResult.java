package seedu.address.logic.commands;

/**
 * A class to identify when a command result is a down command.
 */
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
