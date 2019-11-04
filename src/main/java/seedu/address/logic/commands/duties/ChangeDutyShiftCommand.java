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
import seedu.address.model.events.predicates.EventContainsRefIdPredicate;


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
            + PREFIX_START + "02/12/19 0900 "
            + PREFIX_END + "02/12/19 2100";

    public static final String MESSAGE_SUCCESS = "Duty shift changed to %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the duty roster";

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

        if (model.hasExactDutyShift(editedEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        try {
            model.setDutyShift(eventToEdit, editedEvent);
        } catch (InvalidEventScheduleChangeException ex) {
            throw new CommandException(ex.getMessage());
        }

        model.updateFilteredDutyShiftList(new EventContainsRefIdPredicate(editedEvent.getPersonId()));
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedEvent));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeDutyShiftCommand // instanceof handles nulls
                && editedEvent.equals(((ChangeDutyShiftCommand) other).editedEvent));
    }

}
