//@@author woon17
package seedu.address.logic.commands.appointments;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.events.Event;
import seedu.address.model.events.exceptions.InvalidEventScheduleChangeException;
import seedu.address.model.events.predicates.EventsMissedPredicate;


/**
 * mark a appointment's status as SETTLED for a patient.
 */
public class SettleApptCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "settleappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": settle a missed appointment. "
            + "by the index number used in the displayed missed appointment's list. \n"
            + "Parameters: INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_DUPLICATE_SETTLE = "you have settled this missed appointment already";
    public static final String MESSAGE_SUCCESS = "This missed appointment for [%1$s] %2$s has been settled: \n%3$s";
    public static final String MESSAGE_SUCCESS_UNDO = "The missed appointment for [%1$s]"
            + " %2$s has been unsettled: \n%3$s";


    private final Event eventToEdit;
    private final Event editedEvent;


    /**
     * Creates an SettleAppCommand to add the specified {@code Person}
     */
    public SettleApptCommand(Event eventToEdit, Event editedEvent) {
        requireNonNull(eventToEdit);
        requireNonNull(editedEvent);
        this.eventToEdit = eventToEdit;
        this.editedEvent = editedEvent;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasExactAppointment(editedEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_SETTLE);
        }

        try {
            model.setAppointment(eventToEdit, editedEvent);
        } catch (InvalidEventScheduleChangeException ex) {
            throw new CommandException(ex.getMessage());
        }
        model.updateFilteredAppointmentList(new EventsMissedPredicate());


        if (editedEvent.getStatus().isSettled()) {
            return new CommandResult(String.format(
                    MESSAGE_SUCCESS,
                    editedEvent.getPersonId(),
                    editedEvent.getPersonName(),
                    editedEvent));
        } else {
            return new CommandResult(String.format(
                    MESSAGE_SUCCESS_UNDO,
                    editedEvent.getPersonId(),
                    editedEvent.getPersonName(),
                    editedEvent));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SettleApptCommand // instanceof handles nulls
                && editedEvent.equals(((SettleApptCommand) other).editedEvent));
    }

}
