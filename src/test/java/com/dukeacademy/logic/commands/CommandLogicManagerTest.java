package com.dukeacademy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.parser.exceptions.ParseException;

class CommandLogicManagerTest {
    private CommandLogicManager commandLogicManager = new CommandLogicManager();

    @Test
    void registerAndExecuteCommand() throws ParseException, CommandException {
        MockCommandFactory mockCommandFactory = new MockCommandFactory();
        commandLogicManager.registerCommand(mockCommandFactory.getCommandWord(), mockCommandFactory::getCommand);

        CommandResult result = commandLogicManager.executeCommand(mockCommandFactory.getCommandWord());
        assertEquals(MockCommand.getExpectedCommandResult(), result);

        CommandResult result1 = commandLogicManager.executeCommand("   "
                + mockCommandFactory.getCommandWord() + "    ");
        assertEquals(MockCommand.getExpectedCommandResult(), result1);

        assertThrows(ParseException.class, () -> commandLogicManager.executeCommand("a@123"));
        assertThrows(ParseException.class, () -> commandLogicManager
                .executeCommand(mockCommandFactory.getCommandWord() + "      abc"));
    }
}
