package seedu.weme.logic.parser.contextparser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.weme.model.ModelContext.CONTEXT_MEMES;
import static seedu.weme.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.weme.logic.commands.generalcommand.HelpCommand;
import seedu.weme.logic.commands.generalcommand.RedoCommand;
import seedu.weme.logic.commands.generalcommand.TabCommand;
import seedu.weme.logic.commands.generalcommand.UndoCommand;
import seedu.weme.logic.parser.exceptions.ParseException;

public class ViewParserTest {

    private final WemeParser parser = new ViewParser();

    @Test
    public void parseCommand_undoRedo_throwsParseException() {
        // ensure undo and redo are overridden
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, (
            ) -> parser.parseCommand(UndoCommand.COMMAND_WORD));
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, (
            ) -> parser.parseCommand(RedoCommand.COMMAND_WORD));
    }

    @Test
    public void parseCommand_otherGeneralCommands() throws Exception {
        assertTrue(parser.parseCommand(TabCommand.COMMAND_WORD + " "
                + CONTEXT_MEMES.getContextName()) instanceof TabCommand);
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

}
