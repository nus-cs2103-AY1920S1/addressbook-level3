package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.performance.Event;
import seedu.address.ui.feature.Feature;

/**
 * Adds an event for the performance recording.
 */
public class EventCommand extends Command {

    public static final String COMMAND_WORD = "event";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new event with the specified name";
    public static final String MESSAGE_SUCCESS = "Event Created: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "%1$s event already exists in Athletick.";

    private final Event toAdd;

    /**
     * Creates an EventCommand to add the specified event.
     */
    public EventCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_EVENT, toAdd.getName()));
        }

        model.addEvent(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getName()),
            new Feature("performance"), model);
    }

    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof EventCommand // instanceof handles nulls
            && toAdd.equals(((EventCommand) other).toAdd));
    }
    @Override
    public String toString() {
        return "'Add Event " + toAdd + "' Command";
    }
}
