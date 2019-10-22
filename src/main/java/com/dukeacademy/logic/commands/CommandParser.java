package com.dukeacademy.logic.commands;

import com.dukeacademy.commons.exceptions.IllegalValueException;
import com.dukeacademy.commons.util.StringUtil;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.parser.exceptions.ParseException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandParser {
    private Map<String, CommandSupplier> commandFactoryMap;

    public CommandParser() {
        this.commandFactoryMap = new HashMap<>();
    }

    public void registerCommandFactory(String commandWord, CommandSupplier commandSupplier) {
        this.commandFactoryMap.put(commandWord, commandSupplier);
    }

    public Command parseCommandText(String commandText) throws ParseException {
        try {
            String commandWord = StringUtil.getFirstWord(commandText);
            String commandArguments = StringUtil.removeFirstWord(commandText);

            return Optional.ofNullable(this.commandFactoryMap.get(commandWord))
                    .map(supplier -> {
                        try {
                            return supplier.getCommand(commandArguments);
                        } catch (InvalidCommandArgumentsException e) {
                            return null;
                        }
                    })
                    .orElseThrow(() -> new ParseException("Invalid command entered."));

        } catch (IllegalValueException e) {
            throw new ParseException("Invalid command entered.");
        }
    }
}
