package seedu.address.logic.commands;

import java.util.Map;

import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
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
        return new UndoCommand(this);
    }
}
