package seedu.elisa.logic.commands;

public class CloseCommandResult extends CommandResult {
    public static CloseCommandResult SUCCESS = new CloseCommandResult(CloseCommand.MESSAGE_SUCCESS);
    public static CloseCommandResult FAILURE = new CloseCommandResult(CloseCommand.MESSAGE_FAILURE);

    public CloseCommandResult(String feedbackToUser) {
        super(feedbackToUser);
    }
}
