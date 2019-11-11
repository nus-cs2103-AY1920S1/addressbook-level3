package seedu.address.logic.commands;

import java.util.Map;

import seedu.address.logic.UndoRedoManager;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;

//@@author bruceskellator

/**
 * Represents a CommandBuilder responsible for creating {@link UndoCommand}.
 */
class UndoCommandBuilder extends CommandBuilder {

    private final UndoRedoManager manager;

    UndoCommandBuilder(UndoRedoManager manager) {
        this.manager = manager;
    }

    @Override
    protected RequiredArgumentList defineCommandArguments() {
        return null;
    }

    @Override
    protected Map<String, OptionalArgumentList> defineCommandOptions() {
        return null;
    }

    UndoRedoManager getManager() {
        return manager;
    }

    @Override
    protected Command commandBuild() {
        return new UndoCommand(this);
    }
}
