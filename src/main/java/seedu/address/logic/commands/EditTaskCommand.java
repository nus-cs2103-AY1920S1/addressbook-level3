package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EDIT_TASK_SUCCESS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_INDEX;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DateTime;
import seedu.address.model.ModelManager;
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.UserOutput;

/**
 * Represents a Command that edits TaskSources in the Model.
 */
public class EditTaskCommand extends Command {

    private final ModelManager model;
    private final List<Integer> indexes;
    private final String description;
    private final DateTime dueDate;
    private final List<String> tags;

    EditTaskCommand(EditTaskCommandBuilder builder) {
        this.model = builder.getModel();
        this.indexes = Objects.requireNonNull(builder.getIndexes());
        this.description = builder.getDescription();
        this.dueDate = builder.getDueDate();
        this.tags = builder.getTags();
    }

    public static CommandBuilder newBuilder(ModelManager model) {
        return new EditTaskCommandBuilder(model).init();
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
            String description;
            if (this.description == null) {
                description = task.getDescription();
            } else {
                description = this.description;
            }

            DateTime dueDate;
            if (this.dueDate == null) {
                dueDate = task.getDueDate();
            } else {
                dueDate = this.dueDate;
            }

            TaskSource replacement = TaskSource.newBuilder(description, dueDate)
                    .build();
            model.replaceTask(task, replacement);
        }

        return new UserOutput(String.format(MESSAGE_EDIT_TASK_SUCCESS, tasks.stream()
                .map(TaskSource::getDescription)
                .collect(Collectors.joining(", "))));
    }
}
