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
 * Shows the details of a specified {@link Event} on the {@link seedu.address.ui.itinerary.EventsPage}.
 */
public class ShowEventDetailsCommand extends Command {
    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the details in a particular event\n"
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_ENTER_EDIT_EVENT_SUCCESS = " The event details are as shown! %1$s";

    private final Index indexToEdit;

    public ShowEventDetailsCommand(Index indexToShow) {
        this.indexToEdit = indexToShow;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Assumes EnterDayCommand has already been called
        List<Event> lastShownList = model.getPageStatus().getDay().getEventList().internalList;

        // Set when the trip list is first displayed to the user
        SortedList currentSortedDayList = model.getPageStatus().getSortedOccurrencesList();

        int rawZeroBasedIndex = currentSortedDayList.getSourceIndex(indexToEdit.getZeroBased());


        if (rawZeroBasedIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        Event eventToShow = lastShownList.get(rawZeroBasedIndex);

        //Sets context for the details
        model.setPageStatus(model.getPageStatus()
                .withNewEvent(eventToShow));

        return new CommandResult(String.format(MESSAGE_ENTER_EDIT_EVENT_SUCCESS, eventToShow), true, COMMAND_WORD);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof EnterEditEventCommand;
    }

}
