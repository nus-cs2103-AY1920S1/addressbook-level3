package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Fetches the details of an existing event in the event book.
 */
public class FetchEventCommand extends Command {
    public static final String COMMAND_WORD = "fetch_ev";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the details of the event identified "
            + "by the index number used in the displayed event list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "fetched Event: %1$s";

    private final Index index;
    private Predicate<Event> predicateToShowIndexedEvent;


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
        List<Event> lastShownList = model.getFilteredEventList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToFetch = lastShownList.get(index.getZeroBased());
        predicateToShowIndexedEvent = event -> event.equals(eventToFetch);
        model.updateFilteredEventList(predicateToShowIndexedEvent);
        return new CommandResult(String.format(MESSAGE_SUCCESS, eventToFetch));
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
