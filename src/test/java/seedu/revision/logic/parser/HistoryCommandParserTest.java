package seedu.revision.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.revision.logic.commands.main.HistoryCommand;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.logic.parser.main.HistoryCommandParser;
import seedu.revision.logic.parser.main.ParserManager;

public class HistoryCommandParserTest {
    private HistoryCommandParser parser = new HistoryCommandParser();
    private final ParserManager parserManager = new ParserManager();

    @Test
    public void parse_validArgs_returnsHistoryCommand() throws ParseException {
        assertTrue(parserManager.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HistoryCommandParser.MESSAGE_ADDITIONAL_COMMAND_BEHIND));
    }
}
