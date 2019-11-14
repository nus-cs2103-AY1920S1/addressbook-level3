package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Whether a page switch operation should be performed. */
    private final boolean doSwitchPage;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean shouldExit;

    /** The application should change the Ui **/
    private final boolean doChangeUi;

    /** The command word executed **/
    private final String commandWord;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean shouldExit, boolean doSwitchPage,
                         boolean doChangeUi, String commandWord) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.shouldExit = shouldExit;
        this.doSwitchPage = doSwitchPage;
        this.doChangeUi = doChangeUi;
        this.commandWord = commandWord;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean shouldExit, boolean doSwitchPage) {
        this(feedbackToUser, showHelp, shouldExit, doSwitchPage, false, null);
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean shouldExit) {
        this(feedbackToUser, showHelp, shouldExit, false, false, null);
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean doChangeUi, String commandWord) {
        this(feedbackToUser, false, false, false, true, commandWord);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and a page switch {@code Class} extending from {@code MainWindow} using {@code page},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean doSwitchPage) {
        this(feedbackToUser, false, false, doSwitchPage, false, null);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return shouldExit;
    }

    public boolean doSwitchPage() {
        return doSwitchPage;
    }

    public boolean doChangeUi() {
        return doChangeUi;
    }

    public String getCommandWord() {
        return commandWord;
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
                && shouldExit == otherCommandResult.shouldExit
                && doSwitchPage == otherCommandResult.doSwitchPage
                && doChangeUi == otherCommandResult.doChangeUi
                && (Objects.equals(commandWord, otherCommandResult.commandWord));
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, shouldExit, doChangeUi, commandWord);
    }

}
