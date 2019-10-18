package seedu.address.logic.commands;

import java.util.Map;

import seedu.address.logic.commands.options.Option;
import seedu.address.logic.commands.options.OptionBuilder;
import seedu.address.model.undo.UndoRedoManager;

/**
 * Represents a CommandBuilder responsible for creating {@link AddEventCommand}.
 */
class UndoCommandBuilder extends CommandBuilder {

    private final UndoRedoManager manager;

    UndoCommandBuilder(UndoRedoManager manager) {
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
        return new UndoCommand(this);
    }
}
