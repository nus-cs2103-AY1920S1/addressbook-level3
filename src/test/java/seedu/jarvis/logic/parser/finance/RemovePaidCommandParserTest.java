package seedu.jarvis.logic.parser.finance;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_PURCHASE;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.finance.RemovePaidCommand;

public class RemovePaidCommandParserTest {

    private RemovePaidCommandParser parser = new RemovePaidCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new RemovePaidCommand(INDEX_FIRST_PURCHASE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemovePaidCommand.MESSAGE_USAGE));
    }
}
