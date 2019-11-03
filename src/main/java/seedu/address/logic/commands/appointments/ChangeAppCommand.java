package seedu.address.logic.commands.appointments;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.events.Event;
import seedu.address.model.events.exceptions.InvalidEventScheduleChangeException;
import seedu.address.model.events.predicates.EventContainsRefIdPredicate;

/**
 * Chnageing the timing of the appointment.
 */
public class ChangeAppCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "editappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": change the appointment date "
            + "by the index number used in the displayed patient's list.\n"
            + "Parameters: "
            + PREFIX_ENTRY + "INDEX (must be a positive integer) "
            + PREFIX_START + "PREFIX_START "
            + "[" + PREFIX_END + "PREFIX_END]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ENTRY + "1 "
            + PREFIX_START + "01/12/19 1000 "
            + PREFIX_END + "01/12/19 1040";

    public static final String MESSAGE_SUCCESS = "this appointment's details has been changed to\n%1$s";
    //public static final String MESSAGE_TIMING_EXIST = "please give a new valid timing for the appointment to change.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the appointment book.";

    private final Event eventToEdit;
    private final Event editedEvent;

    /**
     * Creates an ChangeAppCommand to add the specified {@code Person}
     */
    public ChangeAppCommand(Event eventToEdit, Event editedEvent) {
        requireNonNull(eventToEdit);
        requireNonNull(editedEvent);
        this.eventToEdit = eventToEdit;
        this.editedEvent = editedEvent;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasExactAppointment(editedEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        try {
            model.setAppointment(eventToEdit, editedEvent);
        } catch (InvalidEventScheduleChangeException ex) {
            throw new CommandException(ex.getMessage());
        }

        model.updateFilteredAppointmentList(new EventContainsRefIdPredicate(editedEvent.getPersonId()));
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedEvent));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeAppCommand // instanceof handles nulls
                && editedEvent.equals(((ChangeAppCommand) other).editedEvent));
    }

}
