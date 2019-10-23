package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventContainsKeyDatePredicate;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventDayTime;

/**
 * Assigns a Date-TimePeriod mapping to an Event.
 */
public class AssignDateCommand extends Command {
    public static final String COMMAND_WORD = "set_ev_dt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns a Date-TimePeriod mapping to an existing Event identified"
            + " by the index number used in the displayed event list. \n"
            + "Parameters: INDEX on/EVENTDATE time/TIMEPERIOD \n"
            + "Example: " + COMMAND_WORD + " 2 on/18/10/2019 time/0500-2000";

    public static final String MESSAGE_SUCCESS = "Event has been successfully assigned!";
    private static final String EVENTDATE_INVALID = "Date provided is not within range of the current Event!";

    private final Index index;
    private final EventDate targetEventDate;
    private final EventDayTime eventDayTime;


    /**
     * @param index of the event in the filtered event list to assign to
     */
    public AssignDateCommand(Index index, EventDate targetEventDate, EventDayTime eventDayTime) {
        requireNonNull(index);
        this.index = index;
        this.targetEventDate = targetEventDate;
        this.eventDayTime = eventDayTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToAssign = lastShownList.get(index.getZeroBased());
        EventContainsKeyDatePredicate dateCheck = new EventContainsKeyDatePredicate(targetEventDate.getDate());
        if (!dateCheck.test(eventToAssign)) { //date provided is out of range of Event
            throw new CommandException(EVENTDATE_INVALID);
        }

        eventToAssign.assignDateTime(targetEventDate, eventDayTime);
        return new CommandResult(String.format(MESSAGE_SUCCESS, eventToAssign.getName()), false,
                false, index.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        }

        if (!(other instanceof AssignDateCommand)) { // instanceof handles nulls
            return false;
        }

        // state check
        AssignDateCommand e = (AssignDateCommand) other;
        return index.equals(e.index);
    }
}
