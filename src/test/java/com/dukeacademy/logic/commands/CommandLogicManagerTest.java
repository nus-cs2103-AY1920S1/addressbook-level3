package com.dukeacademy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandKeywordException;
import com.dukeacademy.testutil.MockCommand;
import com.dukeacademy.testutil.MockCommandFactory;

class CommandLogicManagerTest {
    private CommandLogicManager commandLogicManager;

    @BeforeEach
    void initializeTest() {
        this.commandLogicManager = new CommandLogicManager();
    }

    @Test
    void registerAndExecuteCommand() throws CommandException, InvalidCommandKeywordException,
            InvalidCommandArgumentsException {
        // Register Command and its supplier
        MockCommandFactory mockCommandFactory = new MockCommandFactory();
        commandLogicManager.registerCommand(mockCommandFactory.getCommandWord(), mockCommandFactory::getCommand);

        // Check that result obtained is correct
        CommandResult result = commandLogicManager.executeCommand(mockCommandFactory.getCommandWord());
        assertEquals(MockCommand.getExpectedCommandResult(), result);

        // Check that command is still valid in spite of unexpected whitespace
        CommandResult result1 = commandLogicManager.executeCommand("   "
                + mockCommandFactory.getCommandWord() + "    ");
        assertEquals(MockCommand.getExpectedCommandResult(), result1);

        // Check that the correct exceptions are thrown
        assertThrows(InvalidCommandKeywordException.class, () -> commandLogicManager
                .executeCommand("a@123"));
        assertThrows(InvalidCommandArgumentsException.class, () -> commandLogicManager
                .executeCommand(mockCommandFactory.getCommandWord() + "      abc"));
    }

    @Test
    void registerFactoryAndExecuteCommand() throws CommandException, InvalidCommandKeywordException,
            InvalidCommandArgumentsException {
        // Register command factory
        MockCommandFactory mockCommandFactory = new MockCommandFactory();
        commandLogicManager.registerCommand(mockCommandFactory);

        // Check the the correct result is obtained after execution
        CommandResult result = commandLogicManager.executeCommand(mockCommandFactory.getCommandWord());
        assertEquals(MockCommand.getExpectedCommandResult(), result);

        // Check that the command is still valid in spite of unexpected whitespace
        CommandResult result1 = commandLogicManager.executeCommand("   "
                + mockCommandFactory.getCommandWord() + "    ");
        assertEquals(MockCommand.getExpectedCommandResult(), result1);

        // Check that the correct exceptions are thrown
        assertThrows(InvalidCommandKeywordException.class, () ->
                commandLogicManager.executeCommand("a@123"));
        assertThrows(InvalidCommandArgumentsException.class, () -> commandLogicManager
                .executeCommand(mockCommandFactory.getCommandWord() + "      abc"));
    }
}
