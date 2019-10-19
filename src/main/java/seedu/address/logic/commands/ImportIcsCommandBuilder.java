package seedu.address.logic.commands;

import java.util.Map;

import seedu.address.logic.commands.arguments.StringArgument;
import seedu.address.logic.commands.arguments.StringArgumentBuilder;
import seedu.address.logic.commands.options.Option;
import seedu.address.logic.commands.options.OptionBuilder;
import seedu.address.model.ModelManager;

/**
 * Represents a CommandBuilder responsible for creating {@link ImportIcsCommand}.
 */
public class ImportIcsCommandBuilder extends CommandBuilder {
    private static final String ARGUMENT_FILEPATH = "FILEPATH";
    private final ModelManager model;
    private final StringArgumentBuilder filepath;

    ImportIcsCommandBuilder(ModelManager model) {
        this.model = model;
        this.filepath = StringArgument.newBuilder(ARGUMENT_FILEPATH);
    }

    ModelManager getModel() {
        return model;
    }

    String getFilepath() {
        return this.filepath.getValue();
    }

    @Override
    OptionBuilder getCommandArguments() {
        return Option.newBuilder()
                .addArgument(this.filepath);
    }

    @Override
    Map<String, OptionBuilder> getCommandOptions() {
        return Map.of();
    }

    @Override
    Command commandBuild() {
        return new ImportIcsCommand(this);
    }
}
