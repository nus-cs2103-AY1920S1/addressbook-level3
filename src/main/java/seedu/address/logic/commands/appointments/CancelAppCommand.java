//@@author woon17
package seedu.address.logic.commands.appointments;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.List;
import java.util.stream.Collectors;

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
    public static final String MESSAGE_CANCEL_APPOINTMENT_SUCCESS = "appointment cancelled: %1$s";
    public static final String MESSAGE_CANCEL_APPOINTMENTS_SUCCESS = "%1$s repeated appointments cancelled: \n";
    public static final String MESSAGE_CANCEL_APPOINTMENTS_CONSTRAINTS
            = "Must indicate at least 1 appointment to delete";

    private final Event toDelete;
    private final List<Event> eventList;


    public CancelAppCommand(Event toDelete) {
        requireNonNull(toDelete);
        this.toDelete = toDelete;
        this.eventList = null;
    }

    public CancelAppCommand(List<Event> eventList) {
        requireNonNull(eventList);
        checkArgument(eventList.size() > 0, MESSAGE_CANCEL_APPOINTMENTS_CONSTRAINTS);
        this.toDelete = null;
        this.eventList = eventList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (eventList == null) {
            checkHasEvent(model, toDelete);
            model.deleteAppointment(toDelete);
            model.updateFilteredAppointmentList(new EventContainsRefIdPredicate(toDelete.getPersonId()));
            return new CommandResult(String.format(MESSAGE_CANCEL_APPOINTMENT_SUCCESS, toDelete));
        }

        for (Event e : eventList) {
            if(model.hasExactAppointment(e)) {
                model.deleteAppointment(e);
            }
        }
        model.updateFilteredAppointmentList(new EventContainsRefIdPredicate(eventList.get(0).getPersonId()));
        return new CommandResult(String.format(
                MESSAGE_CANCEL_APPOINTMENTS_SUCCESS,
                eventList.size(),
                eventList.stream()
                        .map(e -> e.toString()).collect(Collectors.joining("\n"))));
    }

    //@@author SakuraBlossom
    /**
     * Checks if the given {@code eventToDelete} exist in the appointment book.
     */
    private void checkHasEvent(Model model, Event eventToDelete) throws CommandException {
        if (!model.hasExactAppointment(eventToDelete)) {
            throw new CommandException(String.format(Messages.MESSAGE_EVENT_NOT_FOUND, eventToDelete));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CancelAppCommand // instanceof handles nulls
                && toDelete.equals(((CancelAppCommand) other).toDelete));
    }
}
