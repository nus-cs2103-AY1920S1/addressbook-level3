package com.dukeacademy.logic.commands;

import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;

/**
 * Mock class for testing purposes.
 */
public class MockCommandFactory implements CommandFactory {

    @Override
    public String getCommandWord() {
        return "test";
    }

    @Override
    public Command getCommand(String commandArguments) throws InvalidCommandArgumentsException {
        return new MockCommand(commandArguments);
    }
}
