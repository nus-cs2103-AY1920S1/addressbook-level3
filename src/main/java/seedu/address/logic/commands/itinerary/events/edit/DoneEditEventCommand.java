package seedu.address.logic.commands.itinerary.events.edit;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.expenditure.exceptions.ExpenditureNotFoundException;
import seedu.address.model.itinerary.event.Event;
import seedu.address.model.itinerary.event.exceptions.ClashingEventException;
import seedu.address.model.itinerary.event.exceptions.EventNotFoundException;

/**
 * Placeholder.
 */
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
                eventToAdd = editEventDescriptor.buildEvent(model);
                model.getPageStatus().getDay().getEventList().add(eventToAdd);
                if(eventToAdd.getExpenditure().isPresent()){
                    model.getPageStatus().getTrip().getExpenditureList().add(eventToAdd.getExpenditure().get());
                }
            } else {
                //edit the current "selected" event
                eventToAdd = editEventDescriptor.buildEvent(eventToEdit, model);
                model.getPageStatus().getDay().getEventList().set(eventToEdit, eventToAdd);
                if(eventToAdd.getExpenditure().isPresent()){
                    model.getPageStatus().getTrip().getExpenditureList().add(eventToAdd.getExpenditure().get());
                }
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
        } catch (ExpenditureNotFoundException e) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof DoneEditEventCommand;
    }


}
