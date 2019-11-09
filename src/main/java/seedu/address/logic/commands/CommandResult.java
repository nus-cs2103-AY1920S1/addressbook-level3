package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.ui.TestFlashCardPanel;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** Statistics should be shown to the user. */
    private final boolean showStats;

    /** The application should exit. */
    private final boolean exit;

    /** check test mode. */
    private boolean isTest = false;
    private boolean isStartTest = false;

    /** check detect change in the mode. */
    private boolean isEndTest = false;

    private TestFlashCardPanel testFlashCardPanel;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean showStats, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.showStats = showStats;
        this.exit = exit;

    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false);
    }

    public CommandResult(String feedbackToUser, TestFlashCardPanel testFlashCardPanel) {
        this (feedbackToUser, false, false, false);
        isTest = true;
        this.testFlashCardPanel = testFlashCardPanel;

    }
    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isShowStats() {
        return showStats;
    }

    public boolean isTestMode() {
        return isTest;
    }

    public boolean isStartTest() {
        return isStartTest;
    }

    public boolean isEndTest() {
        return isEndTest;
    }

    public void setTestMode(boolean start, boolean end) {
        isStartTest = start;
        isEndTest = end;
    }

    public TestFlashCardPanel getTestFlashCardPanel () {
        return testFlashCardPanel;
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
