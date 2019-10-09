package seedu.mark.logic.commands.commandresult;

/**
 * Represents the result of an exit command execution.
 */
public class ExitCommandResult extends CommandResult {

    /**
     * Constructs an {@code ExitCommandResult} with the feedback.
     *
     * @param feedbackToUser feedback
     */
    public ExitCommandResult(String feedbackToUser) {
        super(feedbackToUser);
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
