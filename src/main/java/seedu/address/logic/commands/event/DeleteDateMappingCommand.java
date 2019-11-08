package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DATE_NOT_FOUND_IN_MAP;

import java.util.List;

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

    private final Index targetIndex;
    private final EventDate targetDate;

    public DeleteDateMappingCommand(Index targetIndex, EventDate targetDate) {
        this.targetIndex = targetIndex;
        this.targetDate = targetDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (MainWindow.isFinanceTab()) {
            throw new CommandException(Messages.MESSAGE_WRONG_TAB_MISSING_EVENT_LIST);
        }

        List<Event> lastShownList = MainWindow.getCurrentEventList(model);
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event targetEvent = lastShownList.get(targetIndex.getZeroBased());
        EventDateTimeMap eventDateTimeMap = targetEvent.getEventDateTimeMap();

        if (eventDateTimeMap.containsDateKey(targetDate)) {
            eventDateTimeMap.deleteDateKey(targetDate);
        } else {
            throw new CommandException(String.format(MESSAGE_DATE_NOT_FOUND_IN_MAP, targetDate, targetEvent.getName()));
        }

        return new CommandResult(String.format(MESSAGE_CLEAR_EVENT_DATE_MAPPING_SUCCESS,
                targetDate, targetEvent.getName()));
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
                && targetDate.equals(e.targetDate);
    }
}
