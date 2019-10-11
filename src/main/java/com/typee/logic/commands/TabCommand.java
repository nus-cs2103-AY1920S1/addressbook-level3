package com.typee.logic.commands;

import java.util.logging.Logger;

import com.typee.commons.core.LogsCenter;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.model.Model;
import com.typee.ui.Tab;

/**
 * Changes the tab/menu of the application window.
 */
public class TabCommand extends Command {
    public static final String COMMAND_WORD = "tab";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " [tab name]";
    public static final String MESSAGE_SUCCESS = "Switched to window: ";

    private final Logger logger = new LogsCenter().getLogger(getClass());
    private Tab tabToSwitch;

    public TabCommand(Tab tabToSwitch) {
        this.tabToSwitch = tabToSwitch;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(MESSAGE_SUCCESS + tabToSwitch.getName(), true, tabToSwitch);
    }
}
