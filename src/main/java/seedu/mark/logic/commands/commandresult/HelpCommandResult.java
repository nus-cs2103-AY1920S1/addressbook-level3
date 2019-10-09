package seedu.mark.logic.commands.commandresult;

/**
 * Represents the result of a help command execution.
 */
public class HelpCommandResult extends CommandResult {

    /**
     * Constructs a {@code HelpCommandResult} with the feedback.
     *
     * @param feedbackToUser feedback
     */
    public HelpCommandResult(String feedbackToUser) {
        super(feedbackToUser);
    }

    @Override
    public boolean isShowHelp() {
        return true;
    }

}
