package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.FLAG_EVENT;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.performance.Event;
import seedu.address.ui.feature.Feature;

/**
 * Deletes a event identified using its name in performance.
 */
public class DeleteEventCommand extends DeleteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + FLAG_EVENT
        + ": Deletes the event identified by the event name as seen in the performance view.\n"
        + "Parameters: EVENT_NAME (must exist in performance)\n"
        + "Example: " + COMMAND_WORD + " " + FLAG_EVENT + " freestyle 50m";
    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted event: %1$s";

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
    @Override
    public String toString() {
        return "'Delete " + targetEvent + "' Command";
    }
}
