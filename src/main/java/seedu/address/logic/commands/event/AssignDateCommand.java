/*
@@author DivineDX
*/

package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DATE_NOT_IN_EVENT_RANGE;

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
            + "You may either state a single date, using the on/ prefix, "
            + "or a range, using both the on/ and till/ prefixes \n"
            + "Omitting both on/ and till/ will set the time mappings for the entire date range of the event.\n"
            + "Parameters: INDEX [on/EVENTDATE] [till/EVENTDATE] time/TIMEPERIOD \n"
            + "Example: " + COMMAND_WORD + " 2 on/20/10/2019 time/0500-2000";

    private static final String MESSAGE_SUCCESS_TARGET = "[%s:%s] has been successfully assigned to Event: [%s]";
    private static final String MESSAGE_SUCCESS_RANGE =
            "Dates [%s] to [%s] of Event: [%s] has been successfully assigned with Time: [%s]";

    private final Index index;
    private final Optional<EventDate> startOrTargetEventDate;
    private final Optional<EventDate> endEventDate;

    private final EventDayTime eventDayTime;


    /**
     * Creates an AssignDateCommand where a range of dates is stated to be assigned
     *
     * @param index        Index of Event in Displayed List
     * @param startDate    Starting Date of the Range of Dates to be assigned
     * @param endDate      Ending Date (Inclusive) of the Range of Dates to be assigned
     * @param eventDayTime Time Period to be assigned
     */
    public AssignDateCommand(Index index, EventDate startDate, EventDate endDate, EventDayTime eventDayTime) {
        requireNonNull(index);
        this.index = index;
        this.startOrTargetEventDate = Optional.of(startDate);
        this.endEventDate = Optional.of(endDate);
        this.eventDayTime = eventDayTime;
    }

    /**
     * Creates an AssignDateCommand where only a single date is stated to be assigned
     *
     * @param index           Index of Event in Displayed List
     * @param targetEventDate Target Date to be assigned
     * @param eventDayTime    Time Period to be assigned
     */
    public AssignDateCommand(Index index, EventDate targetEventDate, EventDayTime eventDayTime) {
        requireNonNull(index);
        this.index = index;
        this.startOrTargetEventDate = Optional.of(targetEventDate);
        this.endEventDate = Optional.empty();
        this.eventDayTime = eventDayTime;
    }

    /**
     * AutoAssigns a mapping for the full range of dates that the Event is held
     */
    public AssignDateCommand(Index index, EventDayTime eventDayTime) {
        requireNonNull(index);
        this.index = index;
        this.startOrTargetEventDate = Optional.empty();
        this.endEventDate = Optional.empty();
        this.eventDayTime = eventDayTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (MainWindow.isFinanceTab() || MainWindow.isStatsTab()) {
            throw new CommandException(Messages.MESSAGE_WRONG_TAB);
        }

        List<Event> lastShownList = MainWindow.getCurrentEventList(model);
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToAssign = lastShownList.get(index.getZeroBased());

        boolean isSingleAssign = startOrTargetEventDate.isPresent() && endEventDate.isEmpty();
        boolean isDateRangeAssign = startOrTargetEventDate.isPresent() && endEventDate.isPresent();

        if (isSingleAssign) {
            EventContainsKeyDatePredicate dateCheck =
                    new EventContainsKeyDatePredicate(startOrTargetEventDate.get());
            if (!dateCheck.test(eventToAssign)) { //date provided is out of range of Event
                throw new CommandException(MESSAGE_DATE_NOT_IN_EVENT_RANGE);
            }

            eventToAssign.assignDateTime(startOrTargetEventDate.get(), eventDayTime);
            return new CommandResult(String.format(MESSAGE_SUCCESS_TARGET,
                    startOrTargetEventDate.get(), eventDayTime, eventToAssign.getName()));
        } else { //Either Target or Range
            EventDate startDate = isDateRangeAssign ? startOrTargetEventDate.get()
                    : eventToAssign.getStartDate();
            EventDate endDate = isDateRangeAssign ? endEventDate.get() : eventToAssign.getEndDate();

            EventDate eventStartDate = eventToAssign.getStartDate();
            EventDate eventEndDate = eventToAssign.getEndDate();

            EventContainsKeyDatePredicate startDateCheck = new EventContainsKeyDatePredicate(startDate);
            EventContainsKeyDatePredicate endDateCheck = new EventContainsKeyDatePredicate(endDate);

            if (!startDateCheck.test(eventToAssign) || !endDateCheck.test(eventToAssign)) {
                throw new CommandException(MESSAGE_DATE_NOT_IN_EVENT_RANGE);
            }

            startDate.datesUntil(endDate)
                    .forEach(eventDate -> eventToAssign.assignDateTime(eventDate, eventDayTime));

            return new CommandResult(String.format(MESSAGE_SUCCESS_RANGE,
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
        return index.equals(e.index)
                && startOrTargetEventDate.equals(e.startOrTargetEventDate)
                && endEventDate.equals(e.endEventDate);
    }
}
