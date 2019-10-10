package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user in a separate window. */
    private final boolean showHelp;

    /**
     * The application should exit.
     */
    private final boolean exit;

    /** Bio information should be shown to the user in the same window. */
    private boolean showBio = false;
    private boolean showAchvm = false;

    /**
     * Constructs a {@code CommandResult} with the specified fields. Fields of showing bio and achievements
     * are false by default.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the additional fields of showing bio and achievements.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean showBio, boolean showAchievements, boolean exit) {
        this(feedbackToUser, showHelp, exit);
        this.showBio = showBio;
        this.showAchvm = showAchievements;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isShowBio() {
        return showBio;
    }

    public boolean isShowAchvm() {
        return showAchvm;
    }

    public boolean isExit() {
        return exit;
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
                && showBio == otherCommandResult.showBio
                && showAchvm == otherCommandResult.showAchvm
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, showBio, showAchvm, exit);
    }

}
