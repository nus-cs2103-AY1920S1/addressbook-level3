package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Represents the type of Command entered.
     */
    private CommandResultType commandResultType;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.commandResultType = CommandResultType.OTHER;
    }

    public CommandResult(String feedbackToUser, CommandResultType commandResultType) {
        this.feedbackToUser = feedbackToUser;
        this.commandResultType = commandResultType;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public CommandResultType getCommandResultType() {
        return commandResultType;
    }

    public void setResultType(CommandResultType resultType) {
        this.commandResultType = resultType;
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
            && commandResultType == otherCommandResult.commandResultType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, commandResultType);
    }

}
