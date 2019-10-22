package com.dukeacademy.logic.commands;

import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.parser.exceptions.ParseException;

/**
 * Provides all the necessary methods needed to execute commands in the application.
 */
public interface CommandLogic {
    /**
     * Executes a command based on the command text that is provided.
     * @param commandText the command text that should contain the command keyword and the necessary arguments.
     * @return a command result instance.
     * @throws ParseException if the command text provided is invalid.
     * @throws CommandException if the execution of the command fails.
     */
    CommandResult executeCommand(String commandText) throws ParseException, CommandException;
}
