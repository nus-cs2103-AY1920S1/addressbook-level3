package com.dukeacademy.logic.commands;

import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;

/**
 * Functional interface to represent a command supplier that accepts arguments for the command to act on.
 */
@FunctionalInterface interface CommandSupplier {
    /**
     * Gets command.
     *
     * @param arguments the arguments
     * @return the command
     * @throws InvalidCommandArgumentsException the invalid command arguments exception
     */
    Command getCommand(String arguments) throws InvalidCommandArgumentsException;
}
