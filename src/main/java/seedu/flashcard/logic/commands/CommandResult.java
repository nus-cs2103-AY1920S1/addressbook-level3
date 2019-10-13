package seedu.flashcard.logic.commands;

/**
 * Representing the feed back from a command. Contains information about the feedback to the user,
 * whether it is an exit command or it is an help command.
 */
public class CommandResult {

    private final String feedBackToUser;

    /** show the user help message. */
    private final boolean showHelp;

    /** Tell the system if it is an exit command.*/
    private final boolean exit;

    /**
     * Construct a {@Code CommandResult} with all fields specified.
     */
    public CommandResult(String feedBackToUser, boolean showHelp, boolean exit) {
        this.feedBackToUser = feedBackToUser;
        this.showHelp = showHelp;
        this.exit = exit;
    }

    public String getFeedBackToUser() {
        return feedBackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }
}
