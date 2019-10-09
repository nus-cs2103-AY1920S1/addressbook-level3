package seedu.address.logic.commands.itinerary.events;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.itinerary.events.edit.EditEventFieldCommand;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.itinerary.event.Event;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class EnterEditEventCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the event information editing screen\n"
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_ENTER_EDIT_EVENT_SUCCESS = " Welcome to your event! %1$s";

    private final Index indexToEdit;

    public EnterEditEventCommand(Index indexToEdit) {
        this.indexToEdit = indexToEdit;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Assumes EnterDayCommand has already been called
        List<Event> lastShownList = model.getPageStatus().getDay().getEventList().internalList;

        if (indexToEdit.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        Event eventToEdit = lastShownList.get(indexToEdit.getZeroBased());
        EditEventFieldCommand.EditEventDescriptor editEventDescriptor = new EditEventFieldCommand.EditEventDescriptor(eventToEdit);

        model.setPageStatus(model.getPageStatus()
                .withNewPageType(PageType.ADD_EVENT)
                .withNewEvent(eventToEdit)
                .withNewEditEventDescriptor(editEventDescriptor));

        return new CommandResult(String.format(MESSAGE_ENTER_EDIT_EVENT_SUCCESS, eventToEdit), true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof EnterEditEventCommand;
    }

}
