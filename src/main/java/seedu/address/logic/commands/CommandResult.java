package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.model.Context;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** Updated application context - empty if context was not changed by executing this command. */
    private final Optional<Context> newContext;

    /**
     * Constructs a {@code CommandResult} with the specified fields, for commands that does not change
     * the current {@code Context}.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.newContext = Optional.empty();
    }

    /**
     * Constructs a new {@code CommandResult} with the specified fields, for commands that change the
     * current {@code Context} of the application. All other fields are set to their default values.
     * @param feedbackToUser {@code String} output from executing the command
     * @param newContext the new {@code ContextType} after executing the command
     */
    public CommandResult(String feedbackToUser, Context newContext) {
        requireAllNonNull(feedbackToUser, newContext);
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = false;
        this.exit = false;
        this.newContext = Optional.of(newContext);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default values.
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

    public Optional<Context> getUpdatedContext() {
        return newContext;
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
                && newContext.equals(otherCommandResult.getUpdatedContext());
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, newContext);
    }
}
