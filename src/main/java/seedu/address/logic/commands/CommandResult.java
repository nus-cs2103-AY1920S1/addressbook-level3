package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.core.Mode;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    private final boolean isGoTo;
    private final Mode modeToGoTo;
    private final boolean showWindow;
    private final boolean read;
    private final Object object;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean goTo,
                         Mode modeToGoTo, boolean showWindow) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.isGoTo = goTo;
        this.modeToGoTo = modeToGoTo;
        this.showWindow = showWindow;
        this.read = false;
        this.object = null;
    }

    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean goTo,
                         Mode modeToGoTo, boolean showWindow, boolean read, Object object) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.isGoTo = goTo;
        this.modeToGoTo = modeToGoTo;
        this.showWindow = showWindow;
        this.read = read;
        this.object = object;
    }

    public CommandResult(String feedbackToUser, Object object) {
        this(feedbackToUser, false, false, false,
                null, false, true, object);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false,
                null, false);
    }
    public Object getObject() {
        return object;
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

    public boolean isGoTo() {
        return isGoTo;
    }

    public Mode getModeToGoTo() {
        return modeToGoTo;
    }

    public boolean isShowWindow() {
        return showWindow;
    }

    public boolean isRead() {
        return read;
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
                && isGoTo == otherCommandResult.isGoTo
                && modeToGoTo == otherCommandResult.modeToGoTo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, isGoTo, modeToGoTo);
    }

}
