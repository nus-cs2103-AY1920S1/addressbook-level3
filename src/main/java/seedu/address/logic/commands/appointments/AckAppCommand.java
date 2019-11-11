//@@author woon17
package seedu.address.logic.commands.appointments;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.events.Event;
import seedu.address.model.events.exceptions.InvalidEventScheduleChangeException;
import seedu.address.model.events.predicates.EventMatchesRefIdPredicate;

/**
 * Acknowledge a person to the address book.
 */
public class AckAppCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "ackappt";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Ack a appointment to the address book. "
            + "The specified keywords (case-insensitive).\n"
            + "Parameters: KEYWORD \n"
            + "Example: " + COMMAND_WORD + " E0000001A";

    public static final String MESSAGE_SUCCESS = "The upcoming appointment for [%1$s] %2$s "
            + "has been acknowledged:\n%3$s";
    public static final String MESSAGE_DUPLICATE_ACKED = "The upcoming appointment has already been acknowledged.";
    public static final String MESSAGE_SUCCESS_UNDO = "The appointment for [%1$s]"
            + " %2$s has been unacknowledged: \n%3$s";

    private final Event eventToEdit;
    private final Event editedEvent;

    /**
     * Creates an AckAppCommand to add the specified {@code Event}
     */
    public AckAppCommand(Event eventToEdit, Event editedEvent) {
        requireNonNull(eventToEdit);
        requireNonNull(editedEvent);
        this.eventToEdit = eventToEdit;
        this.editedEvent = editedEvent;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasExactAppointment(editedEvent)) {
            throw new CommandException(String.format(
                    MESSAGE_DUPLICATE_ACKED,
                    editedEvent.getPersonId(),
                    editedEvent.getPersonName()));
        }

        try {
            model.setAppointment(eventToEdit, editedEvent);
        } catch (InvalidEventScheduleChangeException ex) {
            throw new CommandException(ex.getMessage());
        }

        model.updateFilteredAppointmentList(
                new EventMatchesRefIdPredicate(
                        editedEvent.getPersonId()));


        if (editedEvent.getStatus().isAcked()) {
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
                || (other instanceof AckAppCommand // instanceof handles nulls
                && editedEvent.equals(((AckAppCommand) other).editedEvent));
    }

}
