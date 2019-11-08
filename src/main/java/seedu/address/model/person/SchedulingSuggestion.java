package seedu.address.model.person;

import static seedu.address.logic.commands.AssignCommand.MESSAGE_PROMPT_FORCE;
import static seedu.address.model.person.Schedule.MESSAGE_EARLIER_AVAILABLE;
import static seedu.address.model.person.Schedule.MESSAGE_SUGGEST_TIME_FORMAT;

import java.util.Optional;

import seedu.address.model.EventTime;

/**
 * A wrapper around the suggestion returned by Schedule. It has a convenient {@code toString} method for printing.
 */
public class SchedulingSuggestion {
    private String errorMessage;
    private Optional<EventTime> suggestedTime;

    public SchedulingSuggestion(String errorMessage, Optional<EventTime> suggestedTime, EventTime requestedTime) {
        this.errorMessage = errorMessage;
        if (suggestedTime.isPresent() && suggestedTime.get().equals(requestedTime)) {
            // requested time is the same as the suggested time
            // no better time slot exists
            this.suggestedTime = Optional.empty();
        } else {
            this.suggestedTime = suggestedTime;
        }
    }

    public SchedulingSuggestion(String errorMessage, Optional<EventTime> suggestedTime) {
        this.errorMessage = errorMessage;
        this.suggestedTime = suggestedTime;
    }

    public boolean isFatal() {
        return !errorMessage.isEmpty();
    }

    public boolean isEmpty() {
        return suggestedTime.isEmpty() && errorMessage.isEmpty();
    }


    public Optional<EventTime> getSuggestedTime() {
        return suggestedTime;
    }

    @Override
    public String toString() {
        if (isFatal()) {
            return errorMessage + getSuggestedTime()
                    .map(x -> "\n" + String.format(MESSAGE_SUGGEST_TIME_FORMAT, x.toString()))
                    .orElse("");
        } else if (suggestedTime.isPresent()) {
            return MESSAGE_EARLIER_AVAILABLE + suggestedTime.get() + "\n" + MESSAGE_PROMPT_FORCE;
        } else {
            return ""; // no suggestion, command is good
        }
    }
}
