package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.events.Event;

/**
 * Acknowledge a person to the address book.
 */
public class ChangeAppCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "changeappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": change the appointment date "
            + "by the index number used in the displayed patient's list. "
            + "Existing date will be overwritten by the input date.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_START + "PREFIX_EVENT "
            + PREFIX_END + "PREFIX_EVENT \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_START + "01/11/19 1800 "
            + PREFIX_END + "01/11/19 1900";

    public static final String MESSAGE_SUCCESS = "this appointmeent's timing has been changed: %1$s";
    public static final String MESSAGE_TIMING_NOTNEW = "please a new timing for the appointment to chaneg.";

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

        if (model.hasExactEvent(editedEvent)) {
            throw new CommandException(MESSAGE_TIMING_NOTNEW);
        }
        model.setEvent(eventToEdit, editedEvent);
        model.updateFilteredEventList(editedEvent.getPersonId());
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedEvent));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeAppCommand // instanceof handles nulls
                && editedEvent.equals(((ChangeAppCommand) other).editedEvent));
    }

}
