package seedu.weme.logic.parser.contextparser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.weme.logic.commands.generalcommand.HelpCommand;
import seedu.weme.logic.commands.templatecommand.TemplateArchiveCommand;
import seedu.weme.logic.commands.templatecommand.TemplateArchivesCommand;
import seedu.weme.logic.commands.templatecommand.TemplateUnarchiveCommand;
import seedu.weme.logic.parser.exceptions.ParseException;

public class TemplateParserTest {

    private final WemeParser parser = new TemplateParser();

    @Test
    public void parseCommand_archive() throws Exception {
        TemplateArchiveCommand command = (TemplateArchiveCommand) parser.parseCommand(
                TemplateArchiveCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new TemplateArchiveCommand(INDEX_FIRST), command);

    }
    @Test
    public void parseCommand_unarchive() throws Exception {
        TemplateUnarchiveCommand command = (TemplateUnarchiveCommand) parser.parseCommand(
                TemplateUnarchiveCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new TemplateUnarchiveCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_archives() throws Exception {
        assertTrue(parser.parseCommand(TemplateArchivesCommand.COMMAND_WORD) instanceof TemplateArchivesCommand);
        assertTrue(parser.parseCommand(TemplateArchivesCommand.COMMAND_WORD + " 3")
                instanceof TemplateArchivesCommand);
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
