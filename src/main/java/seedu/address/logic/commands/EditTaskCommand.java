package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EDIT_TASK_SUCCESS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_INDEX;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DateTime;
import seedu.address.model.ModelData;
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
    private final DateTime due;
    private final List<String> tags;

    EditTaskCommand(EditTaskCommandBuilder builder) {
        this.model = builder.getModel();
        this.indexes = Objects.requireNonNull(builder.getIndexes());
        this.description = builder.getDescription();
        this.due = builder.getDueDate();
        this.tags = builder.getTags();
    }

    public static CommandBuilder newBuilder(ModelManager model) {
        return new EditTaskCommandBuilder(model).init();
    }

    @Override
    public UserOutput execute() throws CommandException {
        List<TaskSource> tasks = new ArrayList<>(model.getTasks());

        LinkedHashSet<TaskSource> toEdit = new LinkedHashSet<>();
        for (Integer index : indexes) {
            try {
                toEdit.add(tasks.get(index));
            } catch (IndexOutOfBoundsException e) {
                throw new CommandException(String.format(MESSAGE_INVALID_TASK_INDEX, index));
            }
        }

        ListIterator<TaskSource> iterator = tasks.listIterator();
        while (iterator.hasNext()) {
            TaskSource task = iterator.next();

            if (toEdit.contains(task)) {
                String description;
                // Replace field if it is not null.
                if (this.description == null) {
                    description = task.getDescription();
                } else {
                    description = this.description;
                }

                DateTime due;
                if (this.due == null) {
                    due = task.getDueDate();
                } else {
                    due = this.due;
                }

                Collection<String> tags;
                if (this.tags == null) {
                    tags = task.getTags();
                } else {
                    tags = this.tags;
                }

                TaskSource replacement = TaskSource.newBuilder(description)
                    .setDueDate(due)
                    .setTags(tags)
                    .build();

                iterator.set(replacement);
            }
        }

        // Replace model
        this.model.setModelData(new ModelData(this.model.getEvents(), tasks));

        return new UserOutput(String.format(MESSAGE_EDIT_TASK_SUCCESS, toEdit.stream()
            .map(TaskSource::getDescription)
            .collect(Collectors.joining(", "))));
    }
}
