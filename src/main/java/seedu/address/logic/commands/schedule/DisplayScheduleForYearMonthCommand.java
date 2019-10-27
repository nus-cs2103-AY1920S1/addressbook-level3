package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.event.EventContainsKeyYearMonthPredicate;

/**
 * Finds and lists all events in event list whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class DisplayScheduleForYearMonthCommand extends Command {
    public static final String COMMAND_WORD = "view_schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events whose on the specific month, year"
            + "Example: " + COMMAND_WORD + " for/12/2019";

    private final EventContainsKeyYearMonthPredicate predicate;

    public DisplayScheduleForYearMonthCommand(EventContainsKeyYearMonthPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredScheduledEventList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW,
                        model.getFilteredScheduledEventList().size()), "Schedule");
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }
}
