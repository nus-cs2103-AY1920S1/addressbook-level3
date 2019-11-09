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
 * Fetches the details of an existing event in the event book.
 */
public class FetchEventCommand extends Command {
    public static final String COMMAND_WORD = "fetch_ev";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the details of the event identified "
            + "by the index number used in the displayed event list \n"
            + "Parameters: INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "Fetched Event: %1$s";

    private final Index index;

    /**
     * @param index of the event in the filtered event list to fetch
     */
    public FetchEventCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (MainWindow.isFinanceTab() || MainWindow.isStatsTab()) {
            throw new CommandException(Messages.MESSAGE_WRONG_TAB_FETCH);
        }

        List<Event> lastShownList = MainWindow.getCurrentEventList(model);

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToFetch = lastShownList.get(index.getZeroBased());
        return new CommandResult(String.format(MESSAGE_SUCCESS, eventToFetch.getName()), false,
                false, index.getZeroBased(), "Event_Fetch");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FetchEventCommand)) {
            return false;
        }

        // state check
        FetchEventCommand e = (FetchEventCommand) other;
        return index.equals(e.index);
    }
}
