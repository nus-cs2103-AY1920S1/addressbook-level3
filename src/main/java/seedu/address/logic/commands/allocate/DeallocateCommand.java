package seedu.address.logic.commands.allocate;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventManpowerAllocatedList;
import seedu.address.model.event.EventManpowerNeeded;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventVenue;
import seedu.address.model.tag.Tag;

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
        Event newEvent = createEditedEvent(eventToFree);
        model.setEvent(eventToFree, newEvent);
        return new CommandResult(String.format(MESSAGE_FREE_EVENT_SUCCESS, eventToFree.getName()));
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * and a new {@code EventManpowerAllocatedList}.
     */
    private static Event createEditedEvent(Event eventToEdit) {
        assert eventToEdit != null;

        EventName updatedEventName = eventToEdit.getName();
        EventVenue updatedEventVenue = eventToEdit.getVenue();
        EventManpowerNeeded updatedManpowerNeeded = eventToEdit.getManpowerNeeded();
        EventDate updatedStartDate = eventToEdit.getStartDate();
        EventDate updatedEndDate = eventToEdit.getEndDate();
        EventManpowerAllocatedList updatedManpowerAllocatedList = new EventManpowerAllocatedList();
        Set<Tag> updatedTags = eventToEdit.getTags();

        return new Event(updatedEventName, updatedEventVenue,
                updatedManpowerNeeded, updatedStartDate,
                updatedEndDate, updatedManpowerAllocatedList, updatedTags);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeallocateCommand // instanceof handles nulls
                && targetIndex.equals(((DeallocateCommand) other).targetIndex)); // state check
    }
}
