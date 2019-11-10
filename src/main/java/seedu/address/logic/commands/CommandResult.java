package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    private final String commandWord;

    private final boolean changeInUi;

    private final boolean back;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, String commandWord) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.commandWord = commandWord;
        List<String> list = Arrays.asList(LogOutCommand.COMMAND_WORD,
                SendMailCommand.COMMAND_WORD,
                BroadcastMailCommand.COMMAND_WORD,
                SendReminderCommand.COMMAND_WORD,
                SignInCommand.COMMAND_WORD,
                BackCommand.COMMAND_WORD,
                HelpCommand.COMMAND_WORD,
                ExitCommand.COMMAND_WORD
        );
        if (list.contains(commandWord)) {
            this.changeInUi = false;
        } else {
            this.changeInUi = true;
        }
        if (commandWord.equals(BackCommand.COMMAND_WORD)) {
            back = true;
        } else {
            back = false;
        }
    }

    /**
     * Find out whether a change in Ui is needed.
     */
    public boolean changeNeeded() {
        return changeInUi;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, String commandWord) {
        this(feedbackToUser, false, false, commandWord);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public String getCommandWord() {
        return commandWord;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isBack() {
        return back;
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
                && commandWord == otherCommandResult.commandWord;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, commandWord);
    }

}
