//@@author woon17
package seedu.address.logic.commands.duties;

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
 * Changes the details of the duty shift.
 */
public class ChangeDutyShiftCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "editshift";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": changes the details of a duty shift "
            + "by the index number used in the displayed duty roster.\n"
            + "Parameters: "
            + PREFIX_ENTRY + "INDEX (must be a positive integer) "
            + PREFIX_START + "PREFIX_START "
            + PREFIX_END + "PREFIX_END\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ENTRY + "1 "
            + PREFIX_START + SampleAppointmentDataUtil.ONE_MONTH_LATER_AFTERNOON.toString() + " "
            + PREFIX_END + SampleAppointmentDataUtil.ONE_MONTH_LATER_EVENING.toString() + " ";

    public static final String MESSAGE_SUCCESS = "The duty shift for [%1$s] %2$s has been changed:\n"
            + "from %3$s to %4$s";

    private final Event eventToEdit;
    private final Event editedEvent;

    /**
     * Creates an ChangeAppCommand to add the specified {@code Person}
     */
    public ChangeDutyShiftCommand(Event eventToEdit, Event editedEvent) {
        requireNonNull(eventToEdit);
        requireNonNull(editedEvent);
        this.eventToEdit = eventToEdit;
        this.editedEvent = editedEvent;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            model.setDutyShift(eventToEdit, editedEvent);
        } catch (InvalidEventScheduleChangeException ex) {
            throw new CommandException(ex.getMessage());
        }

        model.updateFilteredDutyShiftList(new EventMatchesRefIdPredicate(editedEvent.getPersonId()));
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
                || (other instanceof ChangeDutyShiftCommand // instanceof handles nulls
                && editedEvent.equals(((ChangeDutyShiftCommand) other).editedEvent.getEventTiming()));
    }

}
