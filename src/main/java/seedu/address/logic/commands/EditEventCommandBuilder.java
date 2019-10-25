package seedu.address.logic.commands;

import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.arguments.DateTimeArgument;
import seedu.address.logic.commands.arguments.IndexVariableArguments;
import seedu.address.logic.commands.arguments.StringArgument;
import seedu.address.logic.commands.arguments.StringVariableArguments;
import seedu.address.logic.commands.arguments.list.ArgumentList;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
import seedu.address.model.DateTime;
import seedu.address.model.ModelManager;

/**
 * Represents a CommandBuilder responsible for creating {@link EditEventCommand}.
 */
class EditEventCommandBuilder extends CommandBuilder {

    public static final String OPTION_DESCRIPTION = "--description";
    public static final String OPTION_START_DATE_TIME = "--start";
    public static final String OPTION_END_DATE_TIME = "--end";
    public static final String OPTION_REMIND_DATE_TIME = "--remind";
    public static final String OPTION_TAGS = "--tag";

    private static final String ARGUMENT_INDEXES = "INDEXES";
    private static final String ARGUMENT_DESCRIPTION = "DESCRIPTION";
    private static final String ARGUMENT_START_DATE_TIME = "START_DATE_TIME";
    private static final String ARGUMENT_END_DATE_TIME = "END_DATE_TIME";
    private static final String ARGUMENT_REMIND_DATE_TIME = "REMIND_DATE_TIME";
    private static final String ARGUMENT_TAGS = "TAGS";

    private final ModelManager model;

    private List<Integer> indexes;
    private String description;
    private DateTime start;
    private DateTime end;
    private DateTime remind;
    private List<String> tags;

    EditEventCommandBuilder(ModelManager model) {
        this.model = model;
    }

    @Override
    RequiredArgumentList defineCommandArguments() {
        return ArgumentList.required()
            .setVariableArguments(IndexVariableArguments.newBuilder(ARGUMENT_INDEXES, o -> this.indexes = o));
    }

    @Override
    Map<String, OptionalArgumentList> defineCommandOptions() {
        return Map.of(
            OPTION_DESCRIPTION, ArgumentList.optional()
                .addArgument(StringArgument.newBuilder(ARGUMENT_DESCRIPTION, o -> this.description = o)),
            OPTION_START_DATE_TIME, ArgumentList.optional()
                .addArgument(DateTimeArgument.newBuilder(ARGUMENT_START_DATE_TIME, o -> this.start = o)),
            OPTION_END_DATE_TIME, ArgumentList.optional()
                .addArgument(DateTimeArgument.newBuilder(ARGUMENT_END_DATE_TIME, o -> this.end = o)),
            OPTION_REMIND_DATE_TIME, ArgumentList.optional()
                .addArgument(DateTimeArgument.newBuilder(ARGUMENT_REMIND_DATE_TIME, o -> this.remind = o)),
            OPTION_TAGS, ArgumentList.optional()
                .setVariableArguments(StringVariableArguments.newBuilder(ARGUMENT_TAGS, o -> this.tags = o))
        );
    }

    ModelManager getModel() {
        return model;
    }

    List<Integer> getIndexes() {
        return this.indexes;
    }

    String getDescription() {
        return this.description;
    }

    DateTime getStart() {
        return this.start;
    }

    DateTime getEnd() {
        return this.end;
    }

    DateTime getRemind() {
        return this.remind;
    }

    List<String> getTags() {
        return this.tags;
    }

    @Override
    Command commandBuild() {
        return new EditEventCommand(this);
    }
}
