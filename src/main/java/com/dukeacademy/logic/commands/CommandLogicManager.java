package com.dukeacademy.logic.commands;

import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.parser.exceptions.ParseException;

public class CommandLogicManager implements CommandLogic {
    private CommandParser commandParser;

    public CommandLogicManager() {
        this.commandParser = new CommandParser();
    }

    public void registerCommand(String commandWord, CommandSupplier commandSupplier) {
        this.commandParser.registerCommandFactory(commandWord, commandSupplier);
    }

    public CommandResult executeCommand(String commandText) throws ParseException, CommandException {
        Command command = this.commandParser.parseCommandText(commandText);
        return command.execute();
    }
}
