package io.xpire.logic.commands;

import io.xpire.model.Model;

/**
 * Redo the previous Undo Command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    @Override
    public CommandResult execute(Model model) {
        return null;
    }

    @Override
    public String toString() {
        return "Redo Command";
    }
}
