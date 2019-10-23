package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.events.Event;

import java.util.List;


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
            deleteOneEvent(model);
            model.updateFilteredEventList(toDelete.getPersonId());
            return new CommandResult(String.format(MESSAGE_CANCEL_APPOINTMENT_SUCCESS, toDelete));

        } else {
            for (Event e : eventList) {
                if (!model.hasExactEvent(e)) {
                    throw new CommandException(String.format(Messages.MESSAGE_EVENT_NOT_FOUND, e));
                }
                model.deleteEvent(e);
            }
            model.updateFilteredEventList(eventList.get(0).getPersonId());
            return new CommandResult(String.format(MESSAGE_CANCEL_APPOINTMENT_SUCCESS, eventList));

        }

    }

    /**
     * delete a exist event from the address book.
     */
    private void deleteOneEvent(Model model) throws CommandException {
        if (!model.hasExactEvent(toDelete)) {
            throw new CommandException(String.format(Messages.MESSAGE_EVENT_NOT_FOUND, toDelete));
        }
        model.deleteEvent(toDelete);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CancelAppCommand // instanceof handles nulls
                && toDelete.equals(((CancelAppCommand) other).toDelete));
    }
}
