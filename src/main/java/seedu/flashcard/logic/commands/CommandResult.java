package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Representing the feed back from a command. Contains information about the feedback to the user,
 * whether it is an exit command or it is an help command.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** show the user help message. */
    private final boolean showHelp;

    /** Tell the system if it is an exit command.*/
    private final boolean exit;

    /**Tell the system to show stats screen*/
    private final boolean showStats;

    /** Tell the system if it is a flip command.*/
    private final boolean flip;

    private final String flashcardToDisplay;

    /**
     * Construct a {@Code CommandResult} with all fields specified.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.showStats = false;
        this.flip = false;
        this.flashcardToDisplay = null;
    }

    /**
     *Construct a {@Code CommandResult} with all fields specified.
     * to be merged with the above method
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean showStats) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.showStats = showStats;
        this.flip = false;
        this.flashcardToDisplay = null;
    }

    /**
     *Construct a {@Code CommandResult} with all fields specified.
     * to be merged with the above method
     */
    public CommandResult(String feedbackToUser, boolean flip, String flashcardToDisplay) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = false;
        this.exit = false;
        this.showStats = false;
        this.flip = flip;
        this.flashcardToDisplay = flashcardToDisplay;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public String getFlashcardToDisplay() {
        return flashcardToDisplay;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isShowStats() {
        return showStats;
    }

    public boolean isFlip() {
        return flip;
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }
}
