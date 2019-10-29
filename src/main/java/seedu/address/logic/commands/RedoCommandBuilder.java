package seedu.address.logic.commands;

import java.util.Map;

import seedu.address.logic.UndoRedoManager;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;

/**
 * Represents a CommandBuilder responsible for creating {@link AddEventCommand}.
 */
class RedoCommandBuilder extends CommandBuilder {

    private final UndoRedoManager manager;

    RedoCommandBuilder(UndoRedoManager manager) {
        this.manager = manager;
    }

    @Override
    RequiredArgumentList defineCommandArguments() {
        return null;
    }

    @Override
    Map<String, OptionalArgumentList> defineCommandOptions() {
        return null;
    }

    UndoRedoManager getManager() {
        return manager;
    }

    @Override
    Command commandBuild() {
        return new RedoCommand(this);
    }
}
