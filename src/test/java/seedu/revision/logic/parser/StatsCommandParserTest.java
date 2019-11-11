package seedu.revision.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.revision.logic.commands.main.StatsCommand;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.logic.parser.main.ParserManager;
import seedu.revision.logic.parser.main.StatsCommandParser;

public class StatsCommandParserTest {
    private StatsCommandParser parser = new StatsCommandParser();
    private final ParserManager parserManager = new ParserManager();

    @Test
    public void parse_validArgs_returnsStatsCommand() throws ParseException {
        assertTrue(parserManager.parseCommand(StatsCommand.COMMAND_WORD) instanceof StatsCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                StatsCommandParser.MESSAGE_ADDITIONAL_COMMAND_BEHIND));
    }
}
