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
import seedu.address.model.Model;
import seedu.address.model.events.DateTime;

/**
 * Represents a CommandBuilder responsible for creating {@link AddEventCommand}.
 */
class UndoCommandBuilder extends CommandBuilder {

    private final Model model;

    UndoCommandBuilder(Model model) {
        this.model = model;
    }

    @Override
    OptionBuilder getCommandArguments() {
        return Option.newBuilder();
    }

    @Override
    Map<String, OptionBuilder> getCommandOptions() {
        return Map.of();
    }

    Model getModel() {
        return model;
    }

    @Override
    Command commandBuild() {
        return new UndoCommand(this);
    }
}
