package com.dukeacademy.testutil;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;

/**
 * Mock class for testing purposes.
 */
public class MockCommand implements Command {
    public MockCommand(String arguments) throws InvalidCommandArgumentsException {
        if (arguments != "") {
            throw new InvalidCommandArgumentsException("Invalid arguments");
        }
    }

    public static CommandResult getExpectedCommandResult() {
        return new CommandResult("AbCdEfG1315r!", false);
    }

    @Override
    public CommandResult execute() throws CommandException {
        return getExpectedCommandResult();
    }
}
