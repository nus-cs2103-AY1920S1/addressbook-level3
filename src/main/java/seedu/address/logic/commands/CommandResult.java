package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    private String targetPrintableFileName;

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

    public CommandResult(String feedbackToUser, CommandResultType commandResultType,
                         String targetPrintableFileName) {
        this.feedbackToUser = feedbackToUser;
        this.commandResultType = commandResultType;;
        this.targetPrintableFileName = targetPrintableFileName;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public CommandResultType getCommandResultType() {
        return commandResultType;
    }

    public String getTargetPrintableFileName() {
        return targetPrintableFileName;
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

        if ((targetPrintableFileName != null) && (otherCommandResult.getTargetPrintableFileName() != null)) {
            return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                    && commandResultType == otherCommandResult.commandResultType
                    && targetPrintableFileName.equals(otherCommandResult.getTargetPrintableFileName());
        }

        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
            && commandResultType == otherCommandResult.commandResultType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, commandResultType);
    }

}
