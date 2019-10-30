package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

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

    private final Integer fetch;

    private final String type;

    private final String uiInfo;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.fetch = null;
        this.type = "";
        this.uiInfo = "";
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, String inputType, String date) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.type = inputType;
        this.uiInfo = date;
        this.fetch = null;
        this.exit = false;
        this.showHelp = false;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, String inputType) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.type = inputType;
        this.uiInfo = "";
        this.fetch = null;
        this.exit = false;
        this.showHelp = false;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     * Uniquely called for Fetch Commands
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, Integer fetch, String type) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.fetch = fetch;
        this.exit = exit;
        this.type = type;
        this.uiInfo = "";
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

    public boolean isShowHelp() {
        return showHelp;
    }

    public Integer getFetch() {
        return fetch;
    }

    public boolean isExit() {
        return exit;
    }

    public String getType() {
        return type;
    }

    public String getUiChange() {
        return uiInfo;
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
