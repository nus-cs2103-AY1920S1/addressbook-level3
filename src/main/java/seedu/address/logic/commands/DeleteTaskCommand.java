package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_DELETE_TASK_FAILURE;
import static seedu.address.commons.core.Messages.MESSAGE_DELETE_TASK_NO_PARAMETERS;
import static seedu.address.commons.core.Messages.MESSAGE_DELETE_TASK_SUCCESS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_INDEX;

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
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.UserOutput;

//@@author bruceskellator

/**
 * Represents a Command that deletes TaskSources from the Model.
 */
public class DeleteTaskCommand extends Command {

    private final ModelManager model;
    private final List<Integer> indexes;
    private final List<String> tags;

    private DeleteTaskCommand(Builder builder) {
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
            throw new CommandException(MESSAGE_DELETE_TASK_NO_PARAMETERS);
        }

        List<TaskSource> tasks = new ArrayList<>(this.model.getTasks());
        // toDelete all tasks with matching indexes.
        // If no indexes specified, toDelete all tasks.
        List<TaskSource> toDelete;
        if (this.indexes.isEmpty()) {
            toDelete = new ArrayList<>(this.model.getTasks());
        } else {
            toDelete = new ArrayList<>();
            for (Integer index : this.indexes) {
                try {
                    toDelete.add(tasks.get(index));
                } catch (IndexOutOfBoundsException e) {
                    throw new CommandException(String.format(MESSAGE_INVALID_TASK_INDEX, index));
                }
            }
        }

        // Remove tasks from toDelete that do not have matching tags.
        toDelete.removeIf(task -> !task.getTags().containsAll(this.tags));

        // No tasks to delete found.
        if (toDelete.isEmpty()) {
            throw new CommandException(MESSAGE_DELETE_TASK_FAILURE);
        }

        // Remove all tasks that are in toDelete.
        tasks.removeAll(toDelete);

        // Replace model
        this.model.setModelData(new ModelData(this.model.getEvents(), tasks));

        return new UserOutput(String.format(MESSAGE_DELETE_TASK_SUCCESS, toDelete.stream()
            .map(TaskSource::getDescription)
            .collect(Collectors.joining(", "))));
    }

    /**
     * Represents a CommandBuilder responsible for creating {@link DeleteTaskCommand}.
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
            return new DeleteTaskCommand(this);
        }
    }
}
