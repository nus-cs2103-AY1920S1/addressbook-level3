package com.dukeacademy.testutil;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandFactory;
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
