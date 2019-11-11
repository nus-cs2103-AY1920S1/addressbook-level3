package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.GoCommand.DEFAULT_TAB;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;
    private String tabType;
    private int id;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** ViewWindow should be shown to the user*/
    private final boolean viewDriver;
    private final boolean viewCustomer;

    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.tabType = DEFAULT_TAB;
        this.viewDriver = false;
        this.viewCustomer = false;
        this.id = 0;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, String tabType) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.tabType = tabType;
        this.viewDriver = false;
        this.viewCustomer = false;
        this.id = 0;
    }

    /**
     * Constructs a {@code CommandResult with the specified fields}
     */
    public CommandResult(String feedbackToUser, boolean viewDriver, boolean viewCustomer, int id) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = false;
        this.exit = false;
        this.tabType = DEFAULT_TAB;
        this.viewDriver = viewDriver;
        this.viewCustomer = viewCustomer;
        this.id = id;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public String getTabType() {
        return tabType;
    }

    public int getId() {
        return id;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isSwitchTab() {
        return !tabType.equals(DEFAULT_TAB);
    }

    public boolean isShowCustomer() {
        return viewCustomer;
    }

    public boolean isShowDriver() {
        return viewDriver;
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
