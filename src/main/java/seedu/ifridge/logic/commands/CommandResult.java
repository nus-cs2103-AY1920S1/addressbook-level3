package seedu.ifridge.logic.commands;

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

    private boolean isTemplateListItemCommand;
    private boolean isTemplateListCommand;
    private boolean isWasteListCommand;
    private boolean isShoppingListCommand;
    private boolean isWasteReportCommand;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
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

    public boolean isExit() {
        return exit;
    }

    public boolean isTemplateListItemCommand() {
        return isTemplateListItemCommand;
    }

    public void setTemplateListItemCommand() {
        isTemplateListItemCommand = true;
    }

    public boolean isTemplateListCommand() {
        return isTemplateListCommand;
    }

    public void setTemplateListCommand() {
        isTemplateListCommand = true;
    }

    public boolean isWasteListCommand() {
        return isWasteListCommand;
    }

    public boolean isWasteReportCommand() {
        return isWasteReportCommand;
    }

    public void setWasteReportCommand() {
        this.isWasteReportCommand = true;
    }

    public void setWasteListCommand() {
        isWasteListCommand = true;
    }

    public boolean isShoppingListCommand() {
        return isShoppingListCommand;
    }

    public void setShoppingListCommand() {
        isShoppingListCommand = true;
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
