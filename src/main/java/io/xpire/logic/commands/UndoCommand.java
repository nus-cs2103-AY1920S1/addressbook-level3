package io.xpire.logic.commands;

import io.xpire.model.Model;

/**
 * Undo the previous command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    @Override
    public CommandResult execute(Model model) {
        return null;
    }

    @Override
    public String toString() {
        return "Undo Command";
    }
}
