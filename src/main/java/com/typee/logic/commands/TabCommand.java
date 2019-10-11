package com.typee.logic.commands;

import com.typee.logic.commands.exceptions.CommandException;
import com.typee.model.Model;
import com.typee.ui.Tab;

/**
 * Changes the tab/menu of the application window.
 */
public class TabCommand extends Command {
    public static final String COMMAND_WORD = "tab";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " [tab name]";

    private Tab tab;

    public TabCommand(Tab tab) {
        this.tab = tab;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("Testing tab command", true, tab);
    }
}
