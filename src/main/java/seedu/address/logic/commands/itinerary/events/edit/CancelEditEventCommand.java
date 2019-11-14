package seedu.address.logic.commands.itinerary.events.edit;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.itinerary.event.Event;

/**
 * Cancel editing an event on the {@link seedu.address.ui.itinerary.EditEventPage}.
 */
public class CancelEditEventCommand extends Command {
    public static final String COMMAND_WORD = "cancel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Cancels editing or creating a new event.";

    public static final String MESSAGE_CANCEL_CREATE_SUCCESS = "Cancelled creating the event!";
    public static final String MESSAGE_CANCEL_EDIT_SUCCESS = "Cancelled editing the event: %1$s";

    public CancelEditEventCommand() { }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Event currentlyEditingEvent = model.getPageStatus().getEvent();
        model.setPageStatus(model.getPageStatus()
                .withResetEditEventDescriptor()
                .withNewPageType(PageType.EVENT_PAGE)
                .withResetEvent());

        if (currentlyEditingEvent == null) {
            return new CommandResult(MESSAGE_CANCEL_CREATE_SUCCESS, true);
        } else {
            return new CommandResult(
                    String.format(MESSAGE_CANCEL_EDIT_SUCCESS, currentlyEditingEvent), true);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof CancelEditEventCommand;
    }


}
