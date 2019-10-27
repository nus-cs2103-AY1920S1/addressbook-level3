package com.dukeacademy.logic.commands;

import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandKeywordException;

/**
 * Provides all the necessary methods needed to execute commands in the application.
 */
public interface CommandLogic {
    /**
     * Executes a command based on the command text that is provided.
     *
     * @param commandText the command text that should contain the command keyword and the necessary arguments
     * @return a command result instance
     * @throws CommandException                 if the execution of the command fails
     * @throws InvalidCommandArgumentsException if the arguments in the command text is invalid
     * @throws InvalidCommandKeywordException   if the command text provided does not have a valid command word
     */
    CommandResult executeCommand(String commandText) throws CommandException, InvalidCommandArgumentsException,
            InvalidCommandKeywordException;
}
