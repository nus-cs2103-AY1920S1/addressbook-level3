package seedu.address.logic.commands;

import java.util.Map;

import seedu.address.logic.commands.arguments.StringArgument;
import seedu.address.logic.commands.arguments.list.ArgumentList;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
import seedu.address.model.ModelManager;

//@@author marcusteh1238
/**
 * Represents a CommandBuilder responsible for creating {@link ImportIcsCommand}.
 */
public class ImportIcsCommandBuilder extends CommandBuilder {
    private static final String ARGUMENT_FILEPATH = "FILEPATH";

    private final ModelManager model;

    private String filepath;

    //@@author marcusteh1238
    ImportIcsCommandBuilder(ModelManager model) {
        this.model = model;
    }

    //@@author marcusteh1238
    ModelManager getModel() {
        return model;
    }

    //@@author marcusteh1238
    String getFilepath() {
        return this.filepath;
    }

    //@@author marcusteh1238
    @Override
    protected RequiredArgumentList defineCommandArguments() {
        return ArgumentList.required()
            .addArgument(StringArgument.newBuilder(ARGUMENT_FILEPATH, o -> this.filepath = o));
    }

    //@@author marcusteh1238
    @Override
    protected Map<String, OptionalArgumentList> defineCommandOptions() {
        return null;
    }

    //@@author marcusteh1238
    @Override
    protected Command commandBuild() {
        return new ImportIcsCommand(this);
    }
}
