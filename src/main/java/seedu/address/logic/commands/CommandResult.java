package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.statistic.StatsPayload;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;
    private final List<UiChange> uiChange;
    private final Optional<StatsPayload> statsPayload;

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and specified {@Code type},
     * and set other fields to their default value.
     */
    public CommandResult(String feedbackToUser, UiChange ...type) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.uiChange = Arrays.asList(type);
        this.statsPayload = Optional.empty();
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}
     * and {@code statsPayload}
     * and specified {@Code type},
     * @param statsPayload user input argument for statistics command
     */
    public CommandResult(String feedbackToUser, StatsPayload statsPayload, UiChange ...type) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.uiChange = Arrays.asList(type);
        this.statsPayload = Optional.of(statsPayload);
    }

    public List<UiChange> getUiChange() {
        return uiChange;
    }

    public boolean hasPayloadObject() {
        return this.statsPayload.isPresent();
    }

    public StatsPayload getPayloadObject() {
        return this.statsPayload.get();
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
