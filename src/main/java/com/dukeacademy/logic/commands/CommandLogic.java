package com.dukeacademy.logic.commands;

import com.dukeacademy.commons.exceptions.IllegalValueException;
import com.dukeacademy.commons.util.StringUtil;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.commands.factory.CommandFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandLogic {
    private Map<String, CommandFactory> commandFactoryMap;

    public CommandLogic() {
        this.commandFactoryMap = new HashMap<>();
    }

    public void registerCommandFactory(CommandFactory commandFactory) {
        this.commandFactoryMap.put(commandFactory.getCommandWord(), commandFactory);
    }

    public Command parseCommandText(String commandText) throws CommandException {
        try {
            String commandWord = StringUtil.getFirstWord(commandText);
            String arguments = StringUtil.removeFirstWord(commandText);

            return Optional.of(commandFactoryMap.get(commandWord))
                    .map(factory -> factory.getCommand(arguments))
                    .orElseThrow(() -> new CommandException("Please enter a valid command."));

        } catch (IllegalValueException e) {
            throw new CommandException("Please enter a valid command.");
        }
    }
}
