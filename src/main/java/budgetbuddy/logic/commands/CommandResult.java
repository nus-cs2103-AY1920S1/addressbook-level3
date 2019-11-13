package budgetbuddy.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    private final CommandCategory commandCategory;

    private final List<CommandContinuation<?>> continuations;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser,
                         CommandCategory commandCategory, CommandContinuation<?>... continuations) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.commandCategory = requireNonNull(commandCategory);
        this.continuations = Arrays.stream(requireNonNull(continuations))
                .collect(Collectors.toUnmodifiableList());
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public CommandCategory getCommandCategory() {
        return commandCategory;
    }

    public List<CommandContinuation<?>> getContinuations() {
        return continuations;
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
                && continuations.equals(otherCommandResult.continuations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, continuations);
    }

}
