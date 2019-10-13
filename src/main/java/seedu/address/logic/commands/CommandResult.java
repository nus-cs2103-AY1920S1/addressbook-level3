package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.util.StatsPayload;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;
    private final List<UiChange> uiChange;
    private final StatsPayload statsPayload;

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and specified {@Code type},
     * and set other fields to their default value.
     */
    public CommandResult(String feedbackToUser, UiChange ...type) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.uiChange = Arrays.asList(type);
        this.statsPayload = null;
    }

    public CommandResult(String feedbackToUser, StatsPayload statsPayload, UiChange ...type) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.uiChange = Arrays.asList(type);
        this.statsPayload = statsPayload;
    }

    public List<UiChange> getUiChange() {
        return uiChange;
    }

    public boolean hasPayloadObject() {
        return this.statsPayload != null;
    }

    public StatsPayload getPayloadObject() {
        if (this.hasPayloadObject()) {
            return this.statsPayload;
        } else {
            throw new NullPointerException();
        }
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
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
        return feedbackToUser.equals(otherCommandResult.feedbackToUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser);
    }

}
