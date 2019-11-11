package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EDIT_TASK_DUPLICATE;
import static seedu.address.commons.core.Messages.MESSAGE_EDIT_TASK_NO_INDEXES;
import static seedu.address.commons.core.Messages.MESSAGE_EDIT_TASK_NO_PARAMETERS;
import static seedu.address.commons.core.Messages.MESSAGE_EDIT_TASK_SUCCESS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_INDEX;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import seedu.address.logic.commands.arguments.DateTimeArgument;
import seedu.address.logic.commands.arguments.IndexVariableArguments;
import seedu.address.logic.commands.arguments.StringArgument;
import seedu.address.logic.commands.arguments.StringVariableArguments;
import seedu.address.logic.commands.arguments.list.ArgumentList;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DateTime;
import seedu.address.model.ModelData;
import seedu.address.model.ModelManager;
import seedu.address.model.exceptions.DuplicateElementException;
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.UserOutput;

//@@author bruceskellator

/**
 * Represents a Command that edits TaskSources in the Model.
 */
public class EditTaskCommand extends Command {

    private final ModelManager model;
    private final List<Integer> indexes;
    private final String description;
    private final DateTime due;
    private final List<String> tags;

    private EditTaskCommand(Builder builder) {
        this.model = builder.model;
        this.indexes = Objects.requireNonNull(builder.indexes);
        this.description = builder.description;
        this.due = builder.due;
        this.tags = builder.tags;
    }

    public static CommandBuilder newBuilder(ModelManager model) {
        return new Builder(model).init();
    }

    @Override
    public UserOutput execute() throws CommandException {

        // No indexes given
        if (this.indexes.isEmpty()) {
            throw new CommandException(MESSAGE_EDIT_TASK_NO_INDEXES);
        }

        // No parameters given
        if (this.description == null
            && this.due == null
            && this.tags.isEmpty()) {
            throw new CommandException(MESSAGE_EDIT_TASK_NO_PARAMETERS);
        }

        List<TaskSource> tasks = new ArrayList<>(this.model.getTasks());
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
                if (this.tags.isEmpty()) {
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
        try {
            this.model.setModelData(new ModelData(this.model.getEvents(), tasks));
        } catch (DuplicateElementException e) {
            throw new CommandException(MESSAGE_EDIT_TASK_DUPLICATE);
        }

        return new UserOutput(String.format(MESSAGE_EDIT_TASK_SUCCESS, toEdit.stream()
            .map(TaskSource::getDescription)
            .collect(Collectors.joining(", "))));
    }

    /**
     * Represents a CommandBuilder responsible for creating {@link EditTaskCommand}.
     */
    static class Builder extends CommandBuilder {

        public static final String OPTION_DESCRIPTION = "--description";
        public static final String OPTION_DUE_DATE_DATE_TIME = "--due";
        public static final String OPTION_TAGS = "--tag";

        private static final String ARGUMENT_INDEXES = "INDEXES";
        private static final String ARGUMENT_DESCRIPTION = "DESCRIPTION";
        private static final String ARGUMENT_DUE_DATE_DATE_TIME = "DUE_DATE_DATE_TIME";
        private static final String ARGUMENT_TAGS = "TAGS";

        private final ModelManager model;

        private List<Integer> indexes;
        private String description;
        private DateTime due;
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
                OPTION_DESCRIPTION, ArgumentList.optional()
                    .addArgument(StringArgument.newBuilder(ARGUMENT_DESCRIPTION, o -> this.description = o)),
                OPTION_DUE_DATE_DATE_TIME, ArgumentList.optional()
                    .addArgument(DateTimeArgument.newBuilder(ARGUMENT_DUE_DATE_DATE_TIME, o -> this.due = o)),
                OPTION_TAGS, ArgumentList.optional()
                    .setVariableArguments(StringVariableArguments.newBuilder(ARGUMENT_TAGS, o -> this.tags = o))
            );
        }

        @Override
        protected Command commandBuild() {
            return new EditTaskCommand(this);
        }
    }
}
