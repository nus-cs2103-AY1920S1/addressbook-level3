package seedu.mark.logic.commands.commandresult;

/**
 * Represents the result of a goto command execution.
 */
public class GotoCommandResult extends CommandResult {
    private String url;

    /**
     * Constructs an {@code GotoCommandResult} with the feedback and url.
     *
     * @param feedbackToUser feedback
     * @param url            url to be opened
     */
    public GotoCommandResult(String feedbackToUser, String url) {
        super(feedbackToUser);
        this.url = url;
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
