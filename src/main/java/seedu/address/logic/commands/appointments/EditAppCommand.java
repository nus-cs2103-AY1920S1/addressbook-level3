//@@author woon17
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
import seedu.address.model.events.predicates.EventMatchesRefIdPredicate;
import seedu.address.model.util.SampleAppointmentDataUtil;

/**
 * Chnageing the timing of the appointment.
 */
public class EditAppCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "editappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": change the appointment date "
            + "by the index number used in the displayed patient's list.\n"
            + "Parameters: "
            + PREFIX_ENTRY + "INDEX (must be a positive integer) "
            + PREFIX_START + "PREFIX_START "
            + "[" + PREFIX_END + "PREFIX_END]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ENTRY + "1 "
            + PREFIX_START + SampleAppointmentDataUtil.ONE_MONTH_LATER_MORNING_CHANGE.toString() + " "
            + PREFIX_END + SampleAppointmentDataUtil.ONE_MONTH_LATER_MORNING_PLUS_CHANGE.toString() + " ";

    public static final String MESSAGE_SUCCESS = "The appointment's timing for [%1$s] %2$s has been changed:\n"
            + "from %3$s to %4$s";

    private final Event eventToEdit;
    private final Event editedEvent;

    /**
     * Creates an ChangeAppCommand to add the specified {@code Person}
     */
    public EditAppCommand(Event eventToEdit, Event editedEvent) {
        requireNonNull(eventToEdit);
        requireNonNull(editedEvent);
        this.eventToEdit = eventToEdit;
        this.editedEvent = editedEvent;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.setAppointment(eventToEdit, editedEvent);
        } catch (InvalidEventScheduleChangeException ex) {
            throw new CommandException(ex.getMessage());
        }

        model.updateFilteredAppointmentList(new EventMatchesRefIdPredicate(editedEvent.getPersonId()));
        return new CommandResult(String.format(
                MESSAGE_SUCCESS,
                editedEvent.getPersonId(),
                editedEvent.getPersonName(),
                eventToEdit.getEventTiming(),
                editedEvent.getEventTiming()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditAppCommand // instanceof handles nulls
                && editedEvent.equals(((EditAppCommand) other).editedEvent));
    }

}
