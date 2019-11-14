/*
@@author shihaoyap
 */

package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.EventContainsKeyDatePredicate;
import seedu.address.ui.MainWindow;

/**
 * Finds and lists all events in event list which occurs on the specified specified Date.
 * Keyword matching is case insensitive and in the following format dd/MM/yyyy.
 */
public class DisplayScheduleForDateCommand extends Command {
    public static final String COMMAND_WORD = "display_schedule_date";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events which is on the specific date. \n"
            + "Example: " + COMMAND_WORD + " on/02/12/2019";

    private final EventContainsKeyDatePredicate predicate;

    public DisplayScheduleForDateCommand(EventContainsKeyDatePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (MainWindow.isFinanceTab() || MainWindow.isStatsTab()) {
            throw new CommandException(Messages.MESSAGE_WRONG_TAB);
        }
        model.updateFilteredScheduledEventList(predicate);
        int eventListSize = model.getFilteredScheduledEventList().size();

        String resultMessage = eventListSize == 1
                ? Messages.MESSAGE_EVENT_LISTED_OVERVIEW
                : Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;

        return new CommandResult(
                String.format(resultMessage, eventListSize), "Schedule_Update", predicate.getDate());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayScheduleForDateCommand // instanceof handles nulls
                && predicate.equals(((DisplayScheduleForDateCommand) other).predicate)); // state check
    }
}
