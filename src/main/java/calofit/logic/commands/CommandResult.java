package calofit.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /**Report should be shown to the user. */
    private final boolean showReport;

    /** The application should exit. */
    private final boolean exit;

    /** Notification to be shown to the user. */
    private final boolean notification;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean showReport, boolean exit, boolean notification) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.showReport = showReport;
        this.exit = exit;
        this.notification = notification;
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

    public boolean isExit() {
        return exit;
    }

    public boolean isShowReport() {
        return showReport;
    }

    public boolean isShowNotification() { return notification; }

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
                && showReport == otherCommandResult.showReport
                && exit == otherCommandResult.exit
                && notification == otherCommandResult.notification;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, showReport, exit, notification);
    }

}
