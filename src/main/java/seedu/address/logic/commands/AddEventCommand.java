package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_ADD_EVENT_SUCCESS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.events.DateTime;
import seedu.address.model.events.EventSource;

/**
 * Represents a Command which adds an EventSource to the Model.
 */
public class AddEventCommand extends Command {

    private final EventSource event;

    public AddEventCommand(String description, DateTime start) {
        this.event = new EventSource(description, start);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.addEvent(this.event);
        return new CommandResult(String.format(MESSAGE_ADD_EVENT_SUCCESS, this.event.getDescription()));
    }
}
