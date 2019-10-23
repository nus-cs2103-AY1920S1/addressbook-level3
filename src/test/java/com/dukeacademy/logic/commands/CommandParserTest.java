package com.dukeacademy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.dukeacademy.logic.parser.exceptions.ParseException;

class CommandParserTest {
    private final CommandParser commandParser = new CommandParser();
    @Test
    void registerAndParseCommand() throws ParseException {
        MockCommandFactory mockCommandFactory = new MockCommandFactory();
        commandParser.registerCommand(mockCommandFactory.getCommandWord(), mockCommandFactory::getCommand);

        Command command = commandParser.parseCommandText(mockCommandFactory.getCommandWord());
        assertTrue(command instanceof MockCommand);

        Command command1 = commandParser.parseCommandText(mockCommandFactory.getCommandWord() + "    ");
        assertTrue(command1 instanceof MockCommand);

        assertThrows(ParseException.class, () -> commandParser.parseCommandText("abcde"));
        assertThrows(ParseException.class, () -> commandParser.parseCommandText(mockCommandFactory.getCommandWord()
                + "   12345"));
    }
}
