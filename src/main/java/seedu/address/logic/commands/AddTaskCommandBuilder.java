package seedu.address.logic.commands;

import java.util.Map;

import seedu.address.logic.commands.arguments.DateTimeArgument;
import seedu.address.logic.commands.arguments.DateTimeArgumentBuilder;
import seedu.address.logic.commands.arguments.StringArgument;
import seedu.address.logic.commands.arguments.StringArgumentBuilder;
import seedu.address.logic.commands.arguments.StringVariableArguments;
import seedu.address.logic.commands.arguments.StringVariableArgumentsBuilder;
import seedu.address.logic.commands.options.Option;
import seedu.address.logic.commands.options.OptionBuilder;
import seedu.address.model.DateTime;
import seedu.address.model.ModelManager;

/**
 * Represents a CommandBuilder responsible for creating {@link AddTaskCommand}.
 */
class AddTaskCommandBuilder extends CommandBuilder {

    public static final String OPTION_TAGS = "--tag";

    private static final String ARGUMENT_DESCRIPTION = "DESCRIPTION";
    private static final String ARGUMENT_DUE_DATE_DATE_TIME = "DUE_DATE_DATE_TIME";
    private static final String ARGUMENT_TAGS = "TAGS";

    private final ModelManager model;

    private final StringArgumentBuilder description;
    private final DateTimeArgumentBuilder dueDate;
    private final StringVariableArgumentsBuilder tags;

    AddTaskCommandBuilder(ModelManager model) {
        this.model = model;

        this.description = StringArgument.newBuilder(ARGUMENT_DESCRIPTION);
        this.dueDate = DateTimeArgument.newBuilder(ARGUMENT_DUE_DATE_DATE_TIME);
        this.tags = StringVariableArguments.newBuilder(ARGUMENT_TAGS);
    }

    @Override
    OptionBuilder getCommandArguments() {
        return Option.newBuilder()
                .addArgument(this.description)
                .addArgument(this.dueDate);
    }

    @Override
    Map<String, OptionBuilder> getCommandOptions() {
        return Map.of(
                OPTION_TAGS, Option.newBuilder()
                        .setVariableArguments(this.tags)
        );
    }

    ModelManager getModel() {
        return model;
    }

    String getDescription() {
        return this.description.getValue();
    }

    DateTime getDueDate() {
        return this.dueDate.getValue();
    }

    @Override
    Command commandBuild() {
        return new AddTaskCommand(this);
    }
}
