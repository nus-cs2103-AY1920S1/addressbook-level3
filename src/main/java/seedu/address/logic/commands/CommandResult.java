package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.ui.tab.Tab;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Help information should be shown to the user.
     */
    private final boolean showHelp;

    /**
     * The application should exit.
     */
    private final boolean exit;

    /**
     * The application should switch tab.
     */
    private final Tab switchTab;

    /**
     * Warning(s) for a permissible yet potentially problematic command
     */
    private final String warnings;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, Tab switchTab, String... warnings) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.switchTab = switchTab;
        StringBuilder sb = new StringBuilder();
        for (String warning : warnings) {
            sb.append(warning + '\n');
        }
        this.warnings = sb.toString();

    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}, {@code warnings}
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, String... warnings) {
        this(feedbackToUser, false, false, null, warnings);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, null);
    }

    public String getFeedbackToUser() {
        return feedbackToUser.concat(warnings);
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isSwitchTab() {
        return switchTab != null;
    }

    public Tab getTab() {
        return switchTab;
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
            && switchTab == otherCommandResult.switchTab;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, switchTab);
    }

}
