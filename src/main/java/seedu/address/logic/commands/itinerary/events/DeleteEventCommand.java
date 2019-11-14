package seedu.address.logic.commands.itinerary.events;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.itinerary.event.Event;

/**
 * Command to delete a single {@link Event}.
 */
public class DeleteEventCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a event from day list.\n"
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_DELETE_EVENT_FAILURE = "Failed to delete your event, "
            + "the index you specified is likely out of bounds!";
    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted your event : %1$s!";

    private final Index indexToDelete;

    public DeleteEventCommand(Index indexToDelete) {
        this.indexToDelete = indexToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Assumes EnterDayCommand has been called first
        // Reference to EventList in model is preserved in PageStatus
        List<Event> lastShownList = model.getPageStatus().getDay().getEventList().internalList;

        // Set when the trip list is first displayed to the user
        SortedList currentSortedDayList = model.getPageStatus().getSortedOccurrencesList();

        int rawZeroBasedIndex = currentSortedDayList.getSourceIndex(indexToDelete.getZeroBased());

        if (rawZeroBasedIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        // References preserved by PageStatus
        Event eventToDelete = lastShownList.get(rawZeroBasedIndex);
        try {
            model.getPageStatus().getDay().getEventList().remove(Index.fromZeroBased(rawZeroBasedIndex));
            if (eventToDelete.getExpense().isPresent()) {
                model.getPageStatus().getTrip().getExpenseList().remove(eventToDelete.getExpense().get());
            }
        } catch (Exception ex) {
            return new CommandResult(MESSAGE_DELETE_EVENT_FAILURE);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof DeleteEventCommand;
    }

}
