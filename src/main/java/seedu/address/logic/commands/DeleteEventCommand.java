package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_DELETE_EVENT_FAILURE;
import static seedu.address.commons.core.Messages.MESSAGE_DELETE_EVENT_NO_PARAMETERS;
import static seedu.address.commons.core.Messages.MESSAGE_DELETE_EVENT_SUCCESS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EVENT_INDEX;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import seedu.address.logic.commands.arguments.IndexVariableArguments;
import seedu.address.logic.commands.arguments.StringVariableArguments;
import seedu.address.logic.commands.arguments.list.ArgumentList;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
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

    private DeleteEventCommand(Builder builder) {
        this.model = builder.model;
        this.indexes = builder.indexes;
        this.tags = builder.tags;
    }

    public static CommandBuilder newBuilder(ModelManager model) {
        return new Builder(model).init();
    }

    @Override
    public UserOutput execute() throws CommandException {

        // No indexes or tags specified.
        if (this.indexes.isEmpty() && this.tags.isEmpty()) {
            throw new CommandException(MESSAGE_DELETE_EVENT_NO_PARAMETERS);
        }

        List<EventSource> events = new ArrayList<>(this.model.getEvents());
        // toDelete all events with matching indexes.
        // If no indexes specified, toDelete all events.
        List<EventSource> toDelete;
        if (this.indexes.isEmpty()) {
            toDelete = new ArrayList<>(this.model.getEvents());
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

    /**
     * Represents a CommandBuilder responsible for creating {@link DeleteEventCommand}.
     */
    static class Builder extends CommandBuilder {

        public static final String OPTION_TAGS = "--tag";

        private static final String ARGUMENT_INDEXES = "INDEXES";
        private static final String ARGUMENT_TAGS = "TAGS";

        private final ModelManager model;

        private List<Integer> indexes;
        private List<String> tags;

        private Builder(ModelManager model) {
            this.model = model;
        }

        @Override
        protected RequiredArgumentList defineCommandArguments() {
            return ArgumentList.required()
                .setVariableArguments(IndexVariableArguments.newBuilder(ARGUMENT_INDEXES, o -> this.indexes = o));
        }

        @Override
        protected Map<String, OptionalArgumentList> defineCommandOptions() {
            return Map.of(
                OPTION_TAGS, ArgumentList.optional()
                    .setVariableArguments(StringVariableArguments.newBuilder(ARGUMENT_TAGS, o -> this.tags = o))
            );
        }

        @Override
        protected Command commandBuild() {
            return new DeleteEventCommand(this);
        }
    }
}
