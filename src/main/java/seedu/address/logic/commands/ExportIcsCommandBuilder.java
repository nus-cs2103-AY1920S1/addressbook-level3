package seedu.address.logic.commands;

import java.util.Map;

import seedu.address.logic.commands.arguments.StringArgument;
import seedu.address.logic.commands.arguments.StringArgumentBuilder;
import seedu.address.logic.commands.options.Option;
import seedu.address.logic.commands.options.OptionBuilder;
import seedu.address.model.ModelManager;

/**
 * Represents a CommandBuilder responsible for creating {@link ExportIcsCommand}.
 */
public class ExportIcsCommandBuilder extends CommandBuilder {

    private static final String OPTION_DIRECTORY = "--directory";
    private static final String ARGUMENT_DIRECTORY = "DIRECTORY";

    private final ModelManager model;
    private final StringArgumentBuilder optionDirectory;

    ExportIcsCommandBuilder(ModelManager model) {
        this.model = model;
        this.optionDirectory = StringArgument.newBuilder(ARGUMENT_DIRECTORY);
    }

    ModelManager getModel() {
        return model;
    }

    String getOptionDirectory() {
        return this.optionDirectory.getValue();
    }

    @Override
    OptionBuilder getCommandArguments() {
        return Option.newBuilder();
    }

    @Override
    Map<String, OptionBuilder> getCommandOptions() {
        return Map.of(OPTION_DIRECTORY, Option.newBuilder()
                .addArgument(this.optionDirectory));
    }

    @Override
    Command commandBuild() {
        return new ExportIcsCommand(this);
    }
}
