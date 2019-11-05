package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_ADD_EVENT_DUPLICATE;
import static seedu.address.commons.core.Messages.MESSAGE_ADD_EVENT_SUCCESS;

import java.util.List;
import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DateTime;
import seedu.address.model.ModelData;
import seedu.address.model.ModelManager;
import seedu.address.model.UniqueList;
import seedu.address.model.events.EventSource;
import seedu.address.model.exceptions.DuplicateElementException;
import seedu.address.ui.UserOutput;

/**
 * Represents a Command which adds an EventSource to the Model.
 */
public class AddEventCommand extends Command {

    private final ModelManager model;
    private final EventSource event;

    AddEventCommand(AddEventCommandBuilder builder) {
        String description = Objects.requireNonNull(builder.getDescription());
        DateTime start = Objects.requireNonNull(builder.getStart());

        this.model = builder.getModel();
        this.event = EventSource.newBuilder(description, start)
            .setEnd(builder.getEnd())
            .setRemind(builder.getRemind())
            .setTags(builder.getTags())
            .build();
    }

    public static CommandBuilder newBuilder(ModelManager model) {
        return new AddEventCommandBuilder(model).init();
    }

    @Override
    public UserOutput execute() throws CommandException {
        List<EventSource> events = new UniqueList<>(this.model.getEvents());
        try {
            events.add(this.event);
        } catch (DuplicateElementException e) {
            throw new CommandException(MESSAGE_ADD_EVENT_DUPLICATE);
        }
        this.model.setModelData(new ModelData(events, this.model.getTasks()));
        return new UserOutput(String.format(MESSAGE_ADD_EVENT_SUCCESS, this.event.getDescription()));
    }
}
