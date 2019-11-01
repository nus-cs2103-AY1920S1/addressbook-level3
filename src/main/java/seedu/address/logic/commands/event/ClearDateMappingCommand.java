package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.ui.MainWindow;

/**
 * Deletes a event identified using it's displayed index from the address book.
 */
public class ClearDateMappingCommand extends Command {

    public static final String COMMAND_WORD = "clear_ev_dt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears all Date-Time Mappings of an Event, "
            + "identified by the index number used in the displayed event list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_CLEAR_EVENT_DATE_MAPPING_SUCCESS =
            "Cleared all Date-Time Mappings from Event: [%s]";

    private final Index targetIndex;

    public ClearDateMappingCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList;
        if (MainWindow.getCurrentTabIndex() == 0) {
            lastShownList = model.getFilteredEventList();
        } else {
            lastShownList = model.getFilteredScheduledEventList();
        }
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToClear = lastShownList.get(targetIndex.getZeroBased());
        eventToClear.clearDateTimeMapping();
        return new CommandResult(String.format(MESSAGE_CLEAR_EVENT_DATE_MAPPING_SUCCESS, eventToClear.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClearDateMappingCommand // instanceof handles nulls
                && targetIndex.equals(((ClearDateMappingCommand) other).targetIndex)); // state check
    }
}
