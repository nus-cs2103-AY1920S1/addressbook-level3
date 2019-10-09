package seedu.address.logic.commands.itinerary.events.edit;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.itinerary.event.Event;

import static java.util.Objects.requireNonNull;

public class CancelEditEventCommand extends Command {
    public static final String COMMAND_WORD = "cancel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Cancels editing or creating a new event.";

    public static final String MESSAGE_CANCEL_CREATE_SUCCESS = "Cancelled creating the event!";
    public static final String MESSAGE_CANCEL_EDIT_SUCCESS = "Cancelled editing the event: %1$s";

    public CancelEditEventCommand() { }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Event currentlyEditingDay = model.getPageStatus().getEvent();
        model.setPageStatus(model.getPageStatus()
                .withResetEditEventDescriptor()
                .withNewPageType(PageType.EVENT_PAGE)
                .withResetDay());

        if (currentlyEditingDay == null) {
            return new CommandResult(MESSAGE_CANCEL_CREATE_SUCCESS, true);
        } else {
            return new CommandResult(
                    String.format(MESSAGE_CANCEL_EDIT_SUCCESS, currentlyEditingDay), true);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof CancelEditEventCommand;
    }


}
