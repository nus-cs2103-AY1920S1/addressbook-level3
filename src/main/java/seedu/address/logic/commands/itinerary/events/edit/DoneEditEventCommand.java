package seedu.address.logic.commands.itinerary.events.edit;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.itinerary.event.Event;
import seedu.address.model.itinerary.event.exceptions.ClashingEventException;
import seedu.address.model.itinerary.event.exceptions.EventNotFoundException;

import static java.util.Objects.requireNonNull;

public class DoneEditEventCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Commits your new or edited event information.";

    public static final String MESSAGE_CREATE_EVENT_SUCCESS = "Created Event: %1$s";
    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "All the fields must be provided!";
    public static final String MESSAGE_CLASHING_EVENT = "This event clashes with one of your other events!";

    public DoneEditEventCommand() { }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EditEventFieldCommand.EditEventDescriptor editEventDescriptor = model.getPageStatus().getEditEventDescriptor();
        Event eventToEdit = model.getPageStatus().getEvent();
        Event eventToAdd;

        if (editEventDescriptor == null) {
            return new CommandResult(MESSAGE_NOT_EDITED);
        }

        try {
            if (eventToEdit == null) {
                //buildEvent() requires compulsory fields to be non null, failing which
                //NullPointerException is caught below
                eventToAdd = editEventDescriptor.buildEvent();
                model.getPageStatus().getDay().getEventList().add(eventToAdd);
            } else {
                //edit the current "selected" event
                eventToAdd = editEventDescriptor.buildEvent(eventToEdit);
                model.getPageStatus().getDay().getEventList().set(eventToEdit, eventToAdd);
            }

            model.setPageStatus(model.getPageStatus()
                    .withResetEditEventDescriptor()
                    .withNewPageType(PageType.EVENT_PAGE)
                    .withResetEvent());

            return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, eventToAdd), true);
        } catch (NullPointerException | EventNotFoundException ex) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        } catch (ClashingEventException ex) {
            throw new CommandException(MESSAGE_CLASHING_EVENT);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof DoneEditEventCommand;
    }


}
