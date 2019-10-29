package seedu.address.logic.commands.common;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.logic.commands.AddAppCommand;
import seedu.address.logic.commands.CancelAppCommand;
import seedu.address.model.events.Event;

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

    private final String requestCommand;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.requestCommand = null;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    //CancelAppCommand.COMMAND_WORD
    public CommandResult(String requestCommand, List<Event> eventList) {
        this(requestCommand, eventList, false, false);
    }

    public CommandResult(String requestCommand, List<Event> eventList, boolean showHelp, boolean exit) {
        this.requestCommand = requestCommand;
        this.feedbackToUser = requireNonNull(listToString(eventList));
        this.showHelp = showHelp;
        this.exit = exit;
    }
    /**
     * Deals with input eventList and return a String message,
     * eventList cannot be empty.
     *
     * @param eventList {@code List<Event>} which is the input eventList.
     * @return string message of all events.
     */
    public String listToString(List<Event> eventList) {
        requireNonNull(requestCommand);
        String stringList = requestCommand.equals(CancelAppCommand.COMMAND_WORD)
                ? CancelAppCommand.MESSAGE_CANCEL_APPOINTMENTS_SUCCESS
                : AddAppCommand.MESSAGE_ADD_APPOINTMENTS_SUCCESS;
        for (Event e : eventList) {
            stringList += e + "\n";
        }
        return stringList;
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
    public String toString() {
        return feedbackToUser;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

}
