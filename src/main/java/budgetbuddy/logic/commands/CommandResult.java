package budgetbuddy.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import budgetbuddy.ui.panel.ListPanel;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    private final Class<? extends ListPanel> panelClass;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser,
                         Class<? extends ListPanel> panelClass, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        // todo: add requireNonNull on panelClass after every command has set their panelClass
        this.panelClass = panelClass;
        this.showHelp = showHelp;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and {@code panelClass},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, Class<? extends ListPanel> panelClass) {
        this(feedbackToUser, panelClass, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public Class<? extends ListPanel> getPanelClass() {
        return panelClass;
    }

    public boolean isShowHelp() {
        return showHelp;
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
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

}
