package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.event.EventContainsKeyDateRangePredicate;

/**
 * Finds and lists all events in event list that is between the 2 specified dates
 * Keyword matching is case insensitive and in the following format dd/MM/yyyy.
 */
public class DisplayScheduleBetweenCommand extends Command {
    public static final String COMMAND_WORD = "display_schedule_between";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events that "
            + "occurs between the 2 dates specified. \n"
            + "Example: " + COMMAND_WORD + " start/02/12/2019 end/05/12/2019";

    private final EventContainsKeyDateRangePredicate predicate;

    public DisplayScheduleBetweenCommand(EventContainsKeyDateRangePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredScheduledEventList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW,
                        model.getFilteredScheduledEventList().size()), "Schedule_Update", "");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayScheduleBetweenCommand // instanceof handles nulls
                && predicate.equals(((DisplayScheduleBetweenCommand) other).predicate)); // state check
    }
}
