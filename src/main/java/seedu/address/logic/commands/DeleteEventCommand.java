package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.FLAG_EVENT;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.feature.Feature;
import seedu.address.model.performance.Event;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteEventCommand extends DeleteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the person identified by the index number used in the displayed person list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " " + FLAG_EVENT + " 1";
    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted event: %1$s";
    public static final String MESSAGE_NO_EVENT_GIVEN = "Please provide the name of the event to be deleted.";

    private final Event targetEvent;

    public DeleteEventCommand(Event targetEvent) {
        this.targetEvent = targetEvent;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasEvent(targetEvent)) {
            throw new CommandException(String.format(Event.MESSAGE_NO_SUCH_EVENT, targetEvent.getName()));
        }
        model.deleteEvent(targetEvent);
        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, targetEvent.getName()),
            new Feature("performance"), model);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteEventCommand // instanceof handles nulls
            && targetEvent.equals(((DeleteEventCommand) other).targetEvent)); // state check
    }
}
