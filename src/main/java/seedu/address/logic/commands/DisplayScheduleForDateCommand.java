package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.event.EventContainsKeyDatePredicate;

/**
 * Finds and lists all events in event list whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class DisplayScheduleForDateCommand extends Command {
    public static final String COMMAND_WORD = "display_schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events whose on the specific date"
            + "Example: " + COMMAND_WORD + " on/02/12/2019";

    private final EventContainsKeyDatePredicate predicate;

    public DisplayScheduleForDateCommand(EventContainsKeyDatePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredScheduledEventList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, model.getFilteredScheduledEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }
}
