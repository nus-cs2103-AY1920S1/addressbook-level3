package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_ADD_EVENT_SUCCESS;

import java.util.Objects;

import seedu.address.model.Model;
import seedu.address.model.events.DateTime;
import seedu.address.model.events.EventSource;

/**
 * Represents a Command which adds an EventSource to the Model.
 */
public class AddEventCommand extends Command {

    private final EventSource event;

    AddEventCommand(AddEventCommandBuilder builder) {
        String description = Objects.requireNonNull(builder.getDescription());
        DateTime start = Objects.requireNonNull(builder.getStart());
        this.event = new EventSource(description, start);
    }

    public static CommandBuilder newBuilder() {
        return new AddEventCommandBuilder().init();
    }

    @Override
    public CommandResult execute(Model model) {
        model.addEvent(this.event);
        return new CommandResult(String.format(MESSAGE_ADD_EVENT_SUCCESS, this.event.getDescription()));
    }
}
