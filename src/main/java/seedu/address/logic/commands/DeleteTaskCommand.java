package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_DELETE_TASK_SUCCESS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_INDEX;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
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
        List<TaskSource> list = model.getTaskList();

        List<TaskSource> tasks = new ArrayList<>();
        for (Integer index : indexes) {
            try {
                tasks.add(list.get(index));
            } catch (IndexOutOfBoundsException e) {
                throw new CommandException(String.format(MESSAGE_INVALID_TASK_INDEX, index + 1));
            }
        }

        for (TaskSource task : tasks) {
            model.removeTask(task);
        }
        return new UserOutput(String.format(MESSAGE_DELETE_TASK_SUCCESS, tasks.stream()
                .map(TaskSource::getDescription)
                .collect(Collectors.joining(", "))));
    }
}
