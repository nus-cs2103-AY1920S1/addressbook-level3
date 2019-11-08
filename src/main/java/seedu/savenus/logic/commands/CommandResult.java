package seedu.savenus.logic.commands;

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

    /** New food was added by user. Scroll to latest added food item */
    private boolean justAdd;

    /** Boolean to determine if user only wants to view withdrawals. */
    private boolean showWithdrawOnly;

    private boolean showSavingsOnly;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean justAdd) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.justAdd = justAdd;
        this.showWithdrawOnly = false;
        this.showSavingsOnly = false;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false);
        this.showSavingsOnly = false;
        this.showWithdrawOnly = false;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}, and filters
     * the list of Savings History such that only Savings or Withdrawals are shown.
     */
    public CommandResult(String feedbackToUser, boolean showSavingsOnly, boolean showWithdrawOnly) {
        this(feedbackToUser, false, false, false);
        this.showSavingsOnly = showSavingsOnly;
        this.showWithdrawOnly = showWithdrawOnly;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public void justAdded(boolean justAdd) {
        this.justAdd = justAdd;
    }

    public boolean isJustAdd() {
        return justAdd;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isShowWithdrawOnly() {
        return showWithdrawOnly;
    }

    public boolean isShowSavingsOnly() {
        return showSavingsOnly;
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
                && showWithdrawOnly == otherCommandResult.showWithdrawOnly
                && showSavingsOnly == otherCommandResult.showSavingsOnly;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

}
