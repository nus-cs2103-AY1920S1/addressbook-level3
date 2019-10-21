package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.performance.Event;

/**
 * Adds an event for the performance recording.
 */
public class EventCommand extends Command {

    public static final String COMMAND_WORD = "event";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new event with the specified name";
    public static final String MESSAGE_CREATE_EVENT_SUCCESS = "Event Created: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in Athletick.";

    private final String name;

    /**
     * Creates an EventCommand to add the specified event.
     * @param name of the event.
     */
    public EventCommand(String name) {
        requireNonNull(name);
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (Event.doesExist(name)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        Event.addEvent(name);
        return new CommandResult(String.format(MESSAGE_CREATE_EVENT_SUCCESS, name));
    }
}
