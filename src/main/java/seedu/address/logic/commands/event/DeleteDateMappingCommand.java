package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DATE_NOT_FOUND_IN_MAP;
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
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventDateTimeMap;
import seedu.address.ui.MainWindow;

/**
 * Deletes a date from an Event DateTime mapping identified using it's displayed index from the address book.
 */
public class DeleteDateMappingCommand extends Command {

    public static final String COMMAND_WORD = "delete_ev_dt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Delete a single Date-Time Mappings of an Event, "
            + "identified by the index number used in the displayed event list.\n"
            + "Parameters: INDEX (must be a positive integer) on/EVENTDATE \n"
            + "Example: " + COMMAND_WORD + " 1 on/18/10/2019";

    public static final String MESSAGE_CLEAR_EVENT_DATE_MAPPING_SUCCESS = "Deleted DateTime [%s] from Event: [%s]";
    public static final String MESSAGE_CLEAR_EVENT_DATE_RANGE_MAPPING_SUCCESS = "Deleted DateTime Mapping of "
            + "[%s] to [%s] from Event: [%s]";
    private final Index targetIndex;
    private final EventDate startOrTargetEventDate;
    private final Optional<EventDate> endDateRange;

    public DeleteDateMappingCommand(Index targetIndex, EventDate targetDate) {
        this.targetIndex = targetIndex;
        this.startOrTargetEventDate = targetDate;
        this.endDateRange = Optional.empty();
    }

    public DeleteDateMappingCommand(Index targetIndex, EventDate startDateRange, EventDate endDateRange) {
        this.targetIndex = targetIndex;
        this.startOrTargetEventDate = startDateRange;
        this.endDateRange = Optional.of(endDateRange);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (MainWindow.isFinanceTab() || MainWindow.isStatsTab()) {
            throw new CommandException(Messages.MESSAGE_WRONG_TAB);
        }

        List<Event> lastShownList = MainWindow.getCurrentEventList(model);
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event targetEvent = lastShownList.get(targetIndex.getZeroBased());
        EventDateTimeMap eventDateTimeMap = targetEvent.getEventDateTimeMap();

        if (endDateRange.isPresent()) { //delete for a range
            EventDate endDate = endDateRange.get();
            EventDate eventStartDate = targetEvent.getStartDate();
            EventDate eventEndDate = targetEvent.getEndDate();

            if (eventStartDate.isAfter(startOrTargetEventDate) || eventEndDate.isBefore(endDate)) {
                throw new CommandException(MESSAGE_DATE_NOT_IN_EVENT_RANGE);
            }

            startOrTargetEventDate.datesUntil(endDateRange.get())
                    .forEach(eventDate -> eventDateTimeMap.deleteDateKey(eventDate));

            return new CommandResult(String.format(MESSAGE_CLEAR_EVENT_DATE_RANGE_MAPPING_SUCCESS,
                    startOrTargetEventDate, endDateRange.get(), targetEvent.getName()));
        } else { //delete for a single date
            if (eventDateTimeMap.containsDateKey(startOrTargetEventDate)) {
                eventDateTimeMap.deleteDateKey(startOrTargetEventDate);
                return new CommandResult(String.format(MESSAGE_CLEAR_EVENT_DATE_MAPPING_SUCCESS,
                        startOrTargetEventDate, targetEvent.getName()));
            } else {
                throw new CommandException(String.format(MESSAGE_DATE_NOT_FOUND_IN_MAP,
                        startOrTargetEventDate, targetEvent.getName()));
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteDateMappingCommand)) {
            return false;
        }

        // state check
        DeleteDateMappingCommand e = (DeleteDateMappingCommand) other;
        return targetIndex.equals(e.targetIndex)
                && startOrTargetEventDate.equals(e.startOrTargetEventDate)
                && endDateRange.equals(e.endDateRange);
    }
}
