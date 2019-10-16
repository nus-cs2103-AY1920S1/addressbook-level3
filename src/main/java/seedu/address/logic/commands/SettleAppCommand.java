package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.events.Event;


/**
 * mark a appointment's status as SETTLED for a patient.
 */
public class SettleAppCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "settleappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": settle a missed appointment. "
            + "by the index number used in the displayed missed appointment's list. \n"
            + "Parameters: INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_DUPLICATE_SETTLE = "you have settled this missed appointment already";
    public static final String MESSAGE_SUCCESS = "this missed appointmeent has been settle: %1$s";


    private final Event eventToEdit;
    private final Event editedEvent;


    /**
     * Creates an SettleAppCommand to add the specified {@code Person}
     */
    public SettleAppCommand(Event eventToEdit, Event editedEvent) {
        requireNonNull(eventToEdit);
        requireNonNull(editedEvent);
        this.eventToEdit = eventToEdit;
        this.editedEvent = editedEvent;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasExactEvent(editedEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_SETTLE);
        }

        model.deleteEvent(eventToEdit);

        model.addEvent(editedEvent);
        model.updateToMissedEventList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedEvent));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SettleAppCommand // instanceof handles nulls
                && editedEvent.equals(((SettleAppCommand) other).editedEvent));
    }

}
