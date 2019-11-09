package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_ADD_TASK_DUPLICATE;
import static seedu.address.commons.core.Messages.MESSAGE_ADD_TASK_SUCCESS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import seedu.address.logic.commands.arguments.DateTimeArgument;
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
 * Represents a Command which adds a TaskSource to the Model.
 */
public class AddTaskCommand extends Command {

    private final ModelManager model;
    private final TaskSource task;

    private AddTaskCommand(Builder builder) {
        String description = Objects.requireNonNull(builder.description);

        this.model = builder.model;
        this.task = TaskSource.newBuilder(description)
            .setDueDate(builder.due)
            .setTags(builder.tags)
            .build();
    }

    public static CommandBuilder newBuilder(ModelManager model) {
        return new Builder(model).init();
    }

    @Override
    public UserOutput execute() throws CommandException {
        List<TaskSource> tasks = new ArrayList<>(this.model.getTasks());
        tasks.add(this.task);

        // Replace model
        try {
            this.model.setModelData(new ModelData(this.model.getEvents(), tasks));
        } catch (DuplicateElementException e) {
            throw new CommandException(MESSAGE_ADD_TASK_DUPLICATE);
        }

        return new UserOutput(String.format(MESSAGE_ADD_TASK_SUCCESS, this.task.getDescription()));
    }

    /**
     * Represents a CommandBuilder responsible for creating {@link AddTaskCommand}.
     */
    static class Builder extends CommandBuilder {

        public static final String OPTION_TAGS = "--tag";
        public static final String OPTION_DUE_DATE_TIME = "--due";

        private static final String ARGUMENT_DESCRIPTION = "DESCRIPTION";
        private static final String ARGUMENT_DUE_DATE_TIME = "DUE_DATE_TIME";
        private static final String ARGUMENT_TAGS = "TAGS";

        private final ModelManager model;

        private String description;
        private DateTime due;
        private List<String> tags;

        private Builder(ModelManager model) {
            this.model = model;
        }

        @Override
        protected RequiredArgumentList defineCommandArguments() {
            return ArgumentList.required()
                .addArgument(StringArgument.newBuilder(ARGUMENT_DESCRIPTION, v -> this.description = v));
        }

        @Override
        protected Map<String, OptionalArgumentList> defineCommandOptions() {
            return Map.of(
                OPTION_TAGS, ArgumentList.optional()
                    .setVariableArguments(StringVariableArguments.newBuilder(ARGUMENT_TAGS, v -> this.tags = v)),
                OPTION_DUE_DATE_TIME, ArgumentList.optional()
                    .addArgument(DateTimeArgument.newBuilder(ARGUMENT_DUE_DATE_TIME, v -> this.due = v))
            );
        }

        @Override
        protected Command commandBuild() {
            return new AddTaskCommand(this);
        }
    }
}
