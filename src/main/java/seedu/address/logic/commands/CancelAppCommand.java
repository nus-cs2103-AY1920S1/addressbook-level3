//@@author woon17
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.events.Event;
import seedu.address.model.events.predicates.EventContainsRefIdPredicate;

/**
 * cancel a appointments for a patient.
 */
public class CancelAppCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "cancelappt";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Cancels appointment from the schedule. "
            + "Parameters: INDEX (positive integer)\n"
            + "need to go to patient's appointment list first\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_CANCEL_APPOINTMENT_SUCCESS = "Appointment cancelled: %1$s";
    public static final String MESSAGE_CANCEL_APPOINTMENTS_SUCCESS = "Recursive appointments cancelled: \n";

    private final Event toDelete;
    private final List<Event> eventList;


    public CancelAppCommand(Event toDelete) {
        requireNonNull(toDelete);
        this.toDelete = toDelete;
        this.eventList = null;
    }

    public CancelAppCommand(List<Event> eventList) {
        requireNonNull(eventList);
        this.toDelete = null;
        this.eventList = eventList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (eventList == null) {
            deleteOneEvent(model, toDelete);
            model.updateFilteredAppointmentList(new EventContainsRefIdPredicate(toDelete.getPersonId()));
            return new CommandResult(String.format(MESSAGE_CANCEL_APPOINTMENT_SUCCESS, toDelete));
        }
        for (Event e : eventList) {
            //TODO: Should it still delete the other appointments if one fails?
            deleteOneEvent(model, e);
        }
        model.updateFilteredAppointmentList(new EventContainsRefIdPredicate(eventList.get(0).getPersonId()));
        return new CommandResult(CancelAppCommand.COMMAND_WORD, eventList);
    }

    /**
     * delete a exist event from the address book.
     */
    private void deleteOneEvent(Model model, Event eventToDelete) throws CommandException {
        if (!model.hasExactAppointment(eventToDelete)) {
            throw new CommandException(String.format(Messages.MESSAGE_EVENT_NOT_FOUND, eventToDelete));
        }
        model.deleteAppointment(eventToDelete);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CancelAppCommand // instanceof handles nulls
                && toDelete.equals(((CancelAppCommand) other).toDelete));
    }
}
