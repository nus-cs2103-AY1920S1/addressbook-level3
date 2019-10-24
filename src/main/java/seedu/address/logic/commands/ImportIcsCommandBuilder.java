package seedu.address.logic.commands;

import java.util.Map;

import seedu.address.logic.commands.arguments.StringArgument;
import seedu.address.logic.commands.arguments.list.ArgumentList;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
import seedu.address.model.ModelManager;

/**
 * Represents a CommandBuilder responsible for creating {@link ImportIcsCommand}.
 */
public class ImportIcsCommandBuilder extends CommandBuilder {
    private static final String ARGUMENT_FILEPATH = "FILEPATH";

    private final ModelManager model;

    private String filepath;

    ImportIcsCommandBuilder(ModelManager model) {
        this.model = model;
    }

    ModelManager getModel() {
        return model;
    }

    String getFilepath() {
        return this.filepath;
    }

    @Override
    RequiredArgumentList defineCommandArguments() {
        return ArgumentList.required()
            .addArgument(StringArgument.newBuilder(ARGUMENT_FILEPATH, o -> this.filepath = o));
    }

    @Override
    Map<String, OptionalArgumentList> defineCommandOptions() {
        return null;
    }

    @Override
    Command commandBuild() {
        return new ImportIcsCommand(this);
    }
}
