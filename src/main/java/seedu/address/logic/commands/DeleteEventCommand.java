package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_DELETE_EVENT_EMPTY;
import static seedu.address.commons.core.Messages.MESSAGE_DELETE_EVENT_FAILURE;
import static seedu.address.commons.core.Messages.MESSAGE_DELETE_EVENT_SUCCESS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EVENT_INDEX;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelData;
import seedu.address.model.ModelManager;
import seedu.address.model.events.EventSource;
import seedu.address.ui.UserOutput;

/**
 * Represents a Command that deletes EventSources from the Model.
 */
public class DeleteEventCommand extends Command {

    private final ModelManager model;
    private final List<Integer> indexes;
    private final List<String> tags;

    DeleteEventCommand(DeleteEventCommandBuilder builder) {
        this.model = builder.getModel();
        this.indexes = builder.getIndexes();
        this.tags = builder.getTags();
    }

    public static CommandBuilder newBuilder(ModelManager model) {
        return new DeleteEventCommandBuilder(model).init();
    }

    @Override
    public UserOutput execute() throws CommandException {
        List<EventSource> events = new ArrayList<>(model.getEvents());

        // No indexes or tags specified.
        if (this.indexes.isEmpty() && this.tags.isEmpty()) {
            throw new CommandException(MESSAGE_DELETE_EVENT_EMPTY);
        }

        // toDelete all events with matching indexes.
        // If no indexes specified, toDelete all events.
        List<EventSource> toDelete;
        if (this.indexes.isEmpty()) {
            toDelete = new ArrayList<>(model.getEvents());
        } else {
            toDelete = new ArrayList<>();
            for (Integer index : this.indexes) {
                try {
                    toDelete.add(events.get(index));
                } catch (IndexOutOfBoundsException e) {
                    throw new CommandException(String.format(MESSAGE_INVALID_EVENT_INDEX, index));
                }
            }
        }

        // Remove events from toDelete that do not have matching tags.
        toDelete.removeIf(event -> !event.getTags().containsAll(this.tags));

        // No events to delete found.
        if (toDelete.isEmpty()) {
            throw new CommandException(MESSAGE_DELETE_EVENT_FAILURE);
        }

        // Remove all events that are in toDelete.
        events.removeAll(toDelete);

        // Replace model
        this.model.setModelData(new ModelData(events, this.model.getTasks()));

        return new UserOutput(String.format(MESSAGE_DELETE_EVENT_SUCCESS, toDelete.stream()
            .map(EventSource::getDescription)
            .collect(Collectors.joining(", "))));
    }
}
