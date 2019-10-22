package com.dukeacademy.logic.commands;

import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;

import java.util.Optional;

/**
 * Functional interface to represent a command supplier that accepts arguments for the command to act on.
 */
@FunctionalInterface
public interface CommandSupplier {
    public Command getCommand(String arguments) throws InvalidCommandArgumentsException;
}
