package com.typee.logic.commands;

import com.typee.logic.commands.exceptions.CommandException;
import com.typee.model.Model;

/**
 * Changes the tab/menu of the application window.
 */
public class TabCommand extends Command {
    public static final String COMMAND_WORD = "tab";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("Testing tab command");
    }
}
