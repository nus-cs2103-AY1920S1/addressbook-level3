package seedu.address.logic.commands;

import java.util.Map;

import seedu.address.logic.commands.arguments.StringArgument;
import seedu.address.logic.commands.arguments.list.ArgumentList;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
import seedu.address.model.ModelManager;

/**
 * Represents a CommandBuilder responsible for creating {@link ExportIcsCommand}.
 */
public class ExportIcsCommandBuilder extends CommandBuilder {

    private static final String OPTION_DIRECTORY = "--directory";
    private static final String ARGUMENT_DIRECTORY = "DIRECTORY";

    private final ModelManager model;

    private String optionDirectory;

    ExportIcsCommandBuilder(ModelManager model) {
        this.model = model;
    }

    ModelManager getModel() {
        return model;
    }

    String getOptionDirectory() {
        return this.optionDirectory;
    }

    @Override
    RequiredArgumentList defineCommandArguments() {
        return null;
    }

    @Override
    Map<String, OptionalArgumentList> defineCommandOptions() {
        return Map.of(OPTION_DIRECTORY, ArgumentList.optional()
            .addArgument(StringArgument.newBuilder(ARGUMENT_DIRECTORY, o -> this.optionDirectory = o)));
    }

    @Override
    Command commandBuild() {
        return new ExportIcsCommand(this);
    }
}
