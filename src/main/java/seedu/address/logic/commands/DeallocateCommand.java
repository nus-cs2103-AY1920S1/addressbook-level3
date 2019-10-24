package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Frees all employees associated with an event.
 */
public class DeallocateCommand extends Command {

    public static final String COMMAND_WORD = "free";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deallocate the employees identified by the index number used in the displayed event list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FREE_EVENT_SUCCESS = "Deallocate all Employees for: %1$s";

    private final Index targetIndex;

    public DeallocateCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToFree = lastShownList.get(targetIndex.getZeroBased());
        eventToFree.freeEmployees();
        model.setEvent(eventToFree, eventToFree);
        return new CommandResult(String.format(MESSAGE_FREE_EVENT_SUCCESS, eventToFree.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeallocateCommand // instanceof handles nulls
                && targetIndex.equals(((DeallocateCommand) other).targetIndex)); // state check
    }
}
