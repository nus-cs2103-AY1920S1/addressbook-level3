package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_ADD_TASK_DUPLICATE;
import static seedu.address.commons.core.Messages.MESSAGE_ADD_TASK_SUCCESS;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelData;
import seedu.address.model.ModelManager;
import seedu.address.model.exceptions.DuplicateElementException;
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.UserOutput;

/**
 * Represents a Command which adds a TaskSource to the Model.
 */
public class AddTaskCommand extends Command {

    private final ModelManager model;
    private final TaskSource task;

    AddTaskCommand(AddTaskCommandBuilder builder) {
        String description = Objects.requireNonNull(builder.getDescription());

        this.model = builder.getModel();
        this.task = TaskSource.newBuilder(description)
            .setDueDate(builder.getDueDate())
            .setTags(builder.getTags())
            .build();
    }

    public static CommandBuilder newBuilder(ModelManager model) {
        return new AddTaskCommandBuilder(model).init();
    }

    @Override
    public UserOutput execute() throws CommandException {
        List<TaskSource> tasks = new ArrayList<>(this.model.getTasks());
        tasks.add(this.task);
        try {
            this.model.setModelData(new ModelData(this.model.getEvents(), tasks));
        } catch (DuplicateElementException e) {
            throw new CommandException(MESSAGE_ADD_TASK_DUPLICATE);
        }
        return new UserOutput(String.format(MESSAGE_ADD_TASK_SUCCESS, this.task.getDescription()));
    }
}
