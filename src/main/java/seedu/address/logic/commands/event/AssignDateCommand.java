package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventContainsKeyDatePredicate;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventDayTime;
import seedu.address.ui.MainWindow;

/**
 * Assigns a Date-TimePeriod mapping to an Event.
 */
public class AssignDateCommand extends Command {
    public static final String COMMAND_WORD = "set_ev_dt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns a Date-TimePeriod mapping to an existing Event identified"
            + " by the index number used in the displayed event list. \n"
            + "Parameters: INDEX on/EVENTDATE time/TIMEPERIOD \n"
            + "Example: " + COMMAND_WORD + " 2 on/20/10/2019 time/0500-2000";

    private static final String MESSAGE_SUCCESS_TARGET = "[%s:%s] has been successfully assigned to Event: [%s]";
    private static final String MESSAGE_SUCCESS_ALL =
            "Dates [%s] to [%s] of Event: [%s] has been successfully assigned with Time: [%s]";
    private static final String EVENT_DATE_INVALID = "Date provided is not within range of the current Event!";

    private final Index index;
    private final Optional<EventDate> targetEventDate;
    private final EventDayTime eventDayTime;

    /**
     * @param index of the event in the filtered event list to assign to
     */
    public AssignDateCommand(Index index, EventDate targetEventDate, EventDayTime eventDayTime) {
        requireNonNull(index);
        this.index = index;
        this.targetEventDate = Optional.of(targetEventDate);
        this.eventDayTime = eventDayTime;
    }

    /**
     * AutoAssigns a mapping for the full range of dates that the Event is held
     */
    public AssignDateCommand(Index index, EventDayTime eventDayTime) {
        requireNonNull(index);
        this.index = index;
        this.targetEventDate = Optional.empty();
        this.eventDayTime = eventDayTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (MainWindow.isFinanceTab()) {
            throw new CommandException(Messages.MESSAGE_WRONG_TAB_MISSING_EVENT_LIST);
        }

        List<Event> lastShownList = MainWindow.getCurrentEventList(model);
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToAssign = lastShownList.get(index.getZeroBased());

        if (targetEventDate.isPresent()) {
            EventContainsKeyDatePredicate dateCheck =
                    new EventContainsKeyDatePredicate(targetEventDate.get());
            if (!dateCheck.test(eventToAssign)) { //date provided is out of range of Event
                throw new CommandException(EVENT_DATE_INVALID);
            }

            eventToAssign.assignDateTime(targetEventDate.get(), eventDayTime);
            return new CommandResult(String.format(MESSAGE_SUCCESS_TARGET,
                    targetEventDate.get(), eventDayTime, eventToAssign.getName()));
        } else { //Empty, assign for all dates
            EventDate startDate = eventToAssign.getStartDate();
            EventDate endDate = eventToAssign.getEndDate();

            startDate.datesUntil(endDate)
                    .forEach(eventDate -> {
                        eventToAssign.assignDateTime(eventDate, eventDayTime);
                    });

            return new CommandResult(String.format(MESSAGE_SUCCESS_ALL,
                    startDate, endDate, eventToAssign.getName(), eventDayTime));
        }

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
