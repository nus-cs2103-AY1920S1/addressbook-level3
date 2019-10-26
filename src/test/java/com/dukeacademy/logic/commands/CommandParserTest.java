package com.dukeacademy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandKeywordException;
import com.dukeacademy.testutil.MockCommand;
import com.dukeacademy.testutil.MockCommandFactory;

class CommandParserTest {
    private final CommandParser commandParser = new CommandParser();

    @Test
    void registerAndParseCommand() throws InvalidCommandArgumentsException, InvalidCommandKeywordException {
        // Creates and registers the MockCommandFactory instance
        MockCommandFactory mockCommandFactory = new MockCommandFactory();
        commandParser.registerCommand(mockCommandFactory.getCommandWord(), mockCommandFactory::getCommand);

        // Attempts to parse a MockCommand and check if command received is correct
        Command command = commandParser.parseCommandText(mockCommandFactory.getCommandWord());
        assertTrue(command instanceof MockCommand);

        // Check to see that parsing still works in spite of unexpected whitespace.
        Command command1 = commandParser.parseCommandText(mockCommandFactory.getCommandWord() + "    ");
        assertTrue(command1 instanceof MockCommand);

        // Check that the correct exceptions are thrown
        assertThrows(InvalidCommandKeywordException.class, () -> commandParser.parseCommandText("abcde"));
        assertThrows(InvalidCommandArgumentsException.class, () -> commandParser
                .parseCommandText(mockCommandFactory.getCommandWord() + "   12345"));
    }
}
