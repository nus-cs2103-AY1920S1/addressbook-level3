package seedu.address.logic.commands;

import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.arguments.IndexVariableArguments;
import seedu.address.logic.commands.arguments.StringVariableArguments;
import seedu.address.logic.commands.arguments.list.ArgumentList;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
import seedu.address.model.ModelManager;

/**
 * Represents a CommandBuilder responsible for creating {@link DeleteEventCommand}.
 */
class DeleteEventCommandBuilder extends CommandBuilder {

    public static final String OPTION_TAGS = "--tag";

    private static final String ARGUMENT_INDEXES = "INDEXES";
    private static final String ARGUMENT_TAGS = "TAGS";

    private final ModelManager model;

    private List<Integer> indexes;
    private List<String> tags;

    DeleteEventCommandBuilder(ModelManager model) {
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

    List<String> getTags() {
        return this.tags;
    }

    @Override
    Command commandBuild() {
        return new DeleteEventCommand(this);
    }
}
