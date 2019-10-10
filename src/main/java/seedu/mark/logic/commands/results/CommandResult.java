package seedu.mark.logic.commands.results;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.mark.model.bookmark.Url;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Constructs a {@code CommandResult} with the feedback.
     */
    public CommandResult(String feedbackToUser) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return false;
    }

    public boolean isExit() {
        return false;
    }

    public boolean isGoto() {
        return getUrl() != null;
    }

    public Url getUrl() {
        return null;
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
                && isShowHelp() == otherCommandResult.isShowHelp()
                && isExit() == otherCommandResult.isExit()
                && isGoto() == otherCommandResult.isGoto()
                && getUrl() == otherCommandResult.getUrl();
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, isShowHelp(), isExit(), isGoto(), getUrl());
    }

}
