package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.tarence.model.tutorial.Tutorial;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** The application should display attendance */
    private boolean hasAttendanceDisplay;

    /** The attendance to be displayed by the application */
    private Tutorial tutorialAttendanceToDisplay;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.hasAttendanceDisplay = false;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and
     * a specified {@code Tutorial} and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, Tutorial tutorialAttendanceToDisplay) {
        this(feedbackToUser, false, false);
        this.tutorialAttendanceToDisplay = tutorialAttendanceToDisplay;
        this.hasAttendanceDisplay = true;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isShowAttendance() {
        return hasAttendanceDisplay;
    }

    public Tutorial getTutorialAttendance() {
        return this.tutorialAttendanceToDisplay;
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
                && exit == otherCommandResult.exit
                && hasAttendanceDisplay == otherCommandResult.hasAttendanceDisplay;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, hasAttendanceDisplay);
    }

    @Override
    public String toString() {
        return feedbackToUser;
    }
}
