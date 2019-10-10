package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Date;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.common.Command;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.events.ContainsKeywordsPredicate;
import seedu.address.model.events.Event;
import seedu.address.model.events.Timing;


/**
 * Finds and lists all events in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class AppointmentsCommand extends Command {

    public static final String COMMAND_WORD = "appointments";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD \n"
            + "Example: " + COMMAND_WORD + " 001A";

    private final ContainsKeywordsPredicate predicate;

    public AppointmentsCommand(ContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEventList(predicate);
        autoMissEvent(model.getFilteredEventList());
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, model.getFilteredEventList().size()));
    }

    /**
     * checks all the appointments that before the current time and then make them as missed.
     *
     * @param filteredEventList which is the eventList contains the referenceId
     */
    private void autoMissEvent(ObservableList<Event> filteredEventList) {
        for (Event ev : filteredEventList) {
            Timing evTiming = ev.getEventTiming();
            Date current = new Date();
            if (evTiming.getEndTime().getTime().before(current)) {
                //todo mark event as missed.
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentsCommand // instanceof handles nulls
                && predicate.equals(((AppointmentsCommand) other).predicate)); // state check
    }
}
