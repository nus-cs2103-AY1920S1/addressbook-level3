package com.dukeacademy.logic.commands;

import java.util.HashMap;
import java.util.Map;

import com.dukeacademy.commons.exceptions.IllegalValueException;
import com.dukeacademy.commons.util.StringUtil;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.parser.exceptions.ParseException;

/**
 * Helper class used by CommandLogicManager to keep track and instantiate registered Command classes.
 */
public class CommandParser {
    private Map<String, CommandSupplier> commandFactoryMap;

    public CommandParser() {
        this.commandFactoryMap = new HashMap<>();
    }

    /**
     * Registers a command into the command parser for future access.
     * @param commandWord the keyword of the command.
     * @param commandSupplier the supplier of the command.
     */
    public void registerCommand(String commandWord, CommandSupplier commandSupplier) {
        this.commandFactoryMap.put(commandWord, commandSupplier);
    }

    /**
     * Instantiates and returns a Command class instance based on the command text provided.
     * @param commandText the command text used to invoke the command.
     * @return a command instance corresponding to the command text.
     * @throws ParseException if the command keyword or arguments are invalid.
     */
    public Command parseCommandText(String commandText) throws ParseException {
        try {
            String commandWord = StringUtil.getFirstWord(commandText);
            String commandArguments = StringUtil.removeFirstWord(commandText);

            CommandSupplier supplier = this.commandFactoryMap.get(commandWord);
            if (supplier == null) {
                throw new ParseException("Command word not recognized.");
            }

            return supplier.getCommand(commandArguments);
        } catch (InvalidCommandArgumentsException e) {
            throw new ParseException("Invalid command entered.");
        } catch (IllegalValueException e) {
            throw new ParseException(e.getMessage());
        }
    }
}
