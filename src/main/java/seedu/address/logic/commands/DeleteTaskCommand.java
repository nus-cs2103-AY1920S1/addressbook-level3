package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_DELETE_TASK_FAILURE;
import static seedu.address.commons.core.Messages.MESSAGE_DELETE_TASK_NO_PARAMETERS;
import static seedu.address.commons.core.Messages.MESSAGE_DELETE_TASK_SUCCESS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_INDEX;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelData;
import seedu.address.model.ModelManager;
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.UserOutput;

/**
 * Represents a Command that deletes TaskSources from the Model.
 */
public class DeleteTaskCommand extends Command {

    private final ModelManager model;
    private final List<Integer> indexes;
    private final List<String> tags;

    DeleteTaskCommand(DeleteTaskCommandBuilder builder) {
        this.model = builder.getModel();
        this.indexes = builder.getIndexes();
        this.tags = builder.getTags();
    }

    public static CommandBuilder newBuilder(ModelManager model) {
        return new DeleteTaskCommandBuilder(model).init();
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
}
