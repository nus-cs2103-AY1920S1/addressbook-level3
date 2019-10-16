package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import seedu.address.model.events.Event;

/**
 * Acknowledge a person to the address book.
 */
public class AckAppCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "ackappt";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Ack a appointment to the address book. "
            + "the specified keywords (case-insensitive).\n"
            + "Parameters: KEYWORD \n"
            + "Example: " + COMMAND_WORD + " 001A";

    public static final String MESSAGE_SUCCESS = "this appointmeent has been acked: %1$s";
    public static final String MESSAGE_DUPLICATE_ACKED = "the upcoming appointment has been acked already.";

    private final Event eventToEdit;
    private final Event editedEvent;


    /**
     * Creates an AckAppCommand to add the specified {@code Person}
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

        model.deleteEvent(eventToEdit);

        if (model.hasExactEvent(editedEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACKED);
        }

        model.addEvent(editedEvent);
        model.displayApprovedAndAckedPatientEvent(editedEvent.getPersonId());
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedEvent));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AckAppCommand // instanceof handles nulls
                && editedEvent.equals(((AckAppCommand) other).editedEvent));
    }

}
