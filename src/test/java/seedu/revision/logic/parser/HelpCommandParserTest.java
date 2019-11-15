package seedu.revision.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.revision.logic.commands.main.HelpCommand;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.logic.parser.main.HelpCommandParser;
import seedu.revision.logic.parser.main.ParserManager;

public class HelpCommandParserTest {
    private HelpCommandParser parser = new HelpCommandParser();
    private final ParserManager parserManager = new ParserManager();

    @Test
    public void parse_validArgs_returnsClearCommand() throws ParseException {
        assertTrue(parserManager.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommandParser.MESSAGE_ADDITIONAL_COMMAND_BEHIND));
    }
}
