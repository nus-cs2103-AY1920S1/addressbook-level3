package com.dukeacademy.logic.commands;

import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;

/**
 * Encapsulates the creation of commands and its dependencies and exposes an interface that can be registered in
 * the command logic.
 */
public interface CommandFactory {
    /**
     * Gives the command word that will be used to match to this factory.
     *
     * @return the command word.
     */
    String getCommandWord();

    /**
     * Returns the corresponding command class instance.
     *
     * @param commandArguments the command text from the user's input.
     * @return the corresponding command class instance.
     * @throws InvalidCommandArgumentsException the invalid command arguments exception
     */
    Command getCommand(String commandArguments) throws InvalidCommandArgumentsException;
}
