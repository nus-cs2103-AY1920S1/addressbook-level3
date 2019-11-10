package seedu.weme.logic.parser.contextparser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.weme.model.ModelContext.CONTEXT_MEMES;
import static seedu.weme.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.weme.logic.commands.generalcommand.ExitCommand;
import seedu.weme.logic.commands.generalcommand.HelpCommand;
import seedu.weme.logic.commands.generalcommand.RedoCommand;
import seedu.weme.logic.commands.generalcommand.TabCommand;
import seedu.weme.logic.commands.generalcommand.UndoCommand;
import seedu.weme.logic.parser.exceptions.ParseException;

public class WemeParserTest {

    private final WemeParser parser = new WemeParserStub();

    @Test
    public void parseCommand_undo() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD + " 2") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_redo() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD + " 2") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_tab() throws Exception {
        assertTrue(parser.parseCommand(TabCommand.COMMAND_WORD + " "
                + CONTEXT_MEMES.getContextName()) instanceof TabCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    class WemeParserStub extends WemeParser {};
}
