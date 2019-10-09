package seedu.address.logic.commands.itinerary.events;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.itinerary.event.Event;

import java.util.List;

import static java.util.Objects.requireNonNull;

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

        if (indexToDelete.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        // References preserved by PageStatus
        Event eventToDelete = lastShownList.get(indexToDelete.getZeroBased());
        try {
            model.getPageStatus().getDay().getEventList().remove(indexToDelete);
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
