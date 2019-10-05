package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.events.EventSource;
import seedu.address.model.events.Time;

public class AddEventCommand extends Command {

    private final EventSource event;

    public AddEventCommand(String description, Time start) {
        this.event = new EventSource(description, start);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
