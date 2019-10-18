package seedu.address.logic.commands;

import java.util.Map;

import seedu.address.logic.commands.options.Option;
import seedu.address.logic.commands.options.OptionBuilder;
import seedu.address.model.undo.UndoRedoManager;

/**
 * Represents a CommandBuilder responsible for creating {@link AddEventCommand}.
 */
class RedoCommandBuilder extends CommandBuilder {

    private final UndoRedoManager manager;

    RedoCommandBuilder(UndoRedoManager manager) {
        this.manager = manager;
    }

    @Override
    OptionBuilder getCommandArguments() {
        return Option.newBuilder();
    }

    @Override
    Map<String, OptionBuilder> getCommandOptions() {
        return Map.of();
    }

    UndoRedoManager getManager() {
        return manager;
    }

    @Override
    Command commandBuild() {
        return new RedoCommand(this);
    }
}
