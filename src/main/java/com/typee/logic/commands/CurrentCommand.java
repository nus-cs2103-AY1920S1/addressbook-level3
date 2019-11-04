package com.typee.logic.commands;

import com.typee.logic.commands.exceptions.CommandException;
import com.typee.model.Model;

public class CurrentCommand extends Command {

    private final String currentMessage;

    public CurrentCommand(String currentMessage) {
        this.currentMessage = currentMessage;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(currentMessage);
    }
}
