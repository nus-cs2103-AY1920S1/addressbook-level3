package seedu.address.logic.commands;

import java.util.Map;

import seedu.address.logic.commands.arguments.StringArgument;
import seedu.address.logic.commands.arguments.list.ArgumentList;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
import seedu.address.model.ModelManager;

//@@author marcusteh1238
/**
 * Represents a CommandBuilder responsible for creating {@link ExportIcsCommand}.
 */
public class ExportIcsCommandBuilder extends CommandBuilder {

    private static final String OPTION_DIRECTORY = "--directory";
    private static final String ARGUMENT_DIRECTORY = "DIRECTORY";

    private final ModelManager model;

    private String optionDirectory;

    //@@author marcusteh1238
    ExportIcsCommandBuilder(ModelManager model) {
        this.model = model;
    }

    //@@author marcusteh1238
    ModelManager getModel() {
        return model;
    }

    //@@author marcusteh1238
    String getOptionDirectory() {
        return this.optionDirectory;
    }

    //@@author marcusteh1238
    @Override
    protected RequiredArgumentList defineCommandArguments() {
        return null;
    }

    //@@author marcusteh1238
    @Override
    protected Map<String, OptionalArgumentList> defineCommandOptions() {
        return Map.of(OPTION_DIRECTORY, ArgumentList.optional()
            .addArgument(StringArgument.newBuilder(ARGUMENT_DIRECTORY, o -> this.optionDirectory = o)));
    }

    //@@author marcusteh1238
    @Override
    protected Command commandBuild() {
        return new ExportIcsCommand(this);
    }
}
