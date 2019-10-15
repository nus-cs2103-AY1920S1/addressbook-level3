package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.savenus.testutil.TypicalIndexes.INDEX_FIRST_FOOD;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.BuyCommand;

public class BuyCommandParserTest {
    private BuyCommandParser parser = new BuyCommandParser();

    @Test
    public void parse_validArgs_returnsBuyCommand() {
        assertParseSuccess(parser, "1", new BuyCommand(INDEX_FIRST_FOOD));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyIndex_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE));
    }
}
