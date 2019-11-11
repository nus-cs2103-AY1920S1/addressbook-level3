package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;


/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    private boolean isUnknown;

    private boolean showEarnings;
    private boolean showNotes;
    private boolean userRegister = false;
    private boolean showTasks;
    private boolean showPersons;
    private boolean showReminder;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean showEarnings,
                         boolean isUnknown, boolean showTasks, boolean showPersons,
                         boolean showNotes, boolean showReminder) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.showEarnings = showEarnings;
        this.isUnknown = isUnknown;
        this.showTasks = showTasks;
        this.showPersons = showPersons;
        this.showNotes = showNotes;
        this.showReminder = showReminder;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false, false,
                false, false, false);
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

    public boolean isEarnings() {
        return showEarnings;
    }


    public boolean isUnknown() {
        return isUnknown;
    }

    public boolean isPersons() {
        return showPersons;
    }

    public boolean isNotes() {
        return showNotes;
    }

    public boolean isReminder() {
        return showReminder;
    }

    public void userRegistering() {
        userRegister = !userRegister;
    }

    public boolean isRegister() {
        return userRegister;
    }

    public boolean isTasks() {
        return showTasks;
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
