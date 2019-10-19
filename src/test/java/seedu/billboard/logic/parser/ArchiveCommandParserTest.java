package seedu.billboard.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.billboard.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_ARCHIVE_TAXES;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;

import org.junit.jupiter.api.Test;

import seedu.billboard.logic.commands.AddArchiveCommand;
import seedu.billboard.logic.commands.HelpCommand;
import seedu.billboard.logic.commands.ListArchiveCommand;
import seedu.billboard.logic.commands.ListArchiveNamesCommand;
import seedu.billboard.logic.parser.exceptions.ParseException;

public class ArchiveCommandParserTest {
    private final ArchiveCommandParser parser = new ArchiveCommandParser();

    @Test
    public void parseCommand_listArchiveNames_success() throws Exception {
        ListArchiveNamesCommand expectedCommand = new ListArchiveNamesCommand();
        assertEquals(expectedCommand, parser.parse(ListArchiveNamesCommand.COMMAND_WORD));
    }

    @Test
    public void parseCommand_listArchive_success() throws Exception {
        ListArchiveCommand expectedCommand = new ListArchiveCommand(VALID_ARCHIVE_TAXES);
        String actualInput = ListArchiveCommand.COMMAND_WORD + " " + VALID_ARCHIVE_TAXES;
        assertEquals(expectedCommand, parser.parse(actualInput));
    }

    @Test
    public void parseCommand_addArchive_success() throws Exception {
        AddArchiveCommand expectedCommand = new AddArchiveCommand(VALID_ARCHIVE_TAXES, INDEX_FIRST_EXPENSE);
        String actualInput = AddArchiveCommand.COMMAND_WORD + " 1 arc/" + VALID_ARCHIVE_TAXES;
        assertEquals(expectedCommand, parser.parse(actualInput));
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parse(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parse("unknownCommand"));
    }
}
